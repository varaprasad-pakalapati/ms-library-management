package com.gtf.library.service;

import com.gtf.library.entity.Book;
import com.gtf.library.entity.BookRent;
import com.gtf.library.entity.Member;
import com.gtf.library.exceptions.BookNotAvailableException;
import com.gtf.library.exceptions.NotFoundException;
import com.gtf.library.exceptions.RentalLimitExceededException;
import com.gtf.library.model.RentDto;
import com.gtf.library.repository.BookRentRepository;
import com.gtf.library.repository.BookRepository;
import com.gtf.library.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
public class RentalService {

    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final BookRentRepository bookRentRepository;

    @Transactional
    public long rentBook(final RentDto rentDto) {
        final Book book = bookRepository.findById(rentDto.getBookId())
                .orElseThrow(() -> new NotFoundException("Unable to find the book with id: " + rentDto.getBookId()));
        if (book.getNoOfCopies() == 0) {
            throw new BookNotAvailableException("Book not available to rent");
        }

        final Member member = memberRepository.findById(rentDto.getMemberId())
                .orElseThrow(() -> new NotFoundException("Unable to find Member with id: " + rentDto.getMemberId()));
        if(member.getTotalBooksCheckout() >= 3) {
            throw new RentalLimitExceededException("Member rental limit is exceeded. Member allowed to rent only 3 books");
        }

        final long id = bookRentRepository.save(createBookRent(rentDto)).getId();
        bookRepository.updateBookCopies(book.getNoOfCopies() - 1, book.getId());
        memberRepository.updateBooksCheckedOut(member.getTotalBooksCheckout() + 1, member.getId());
        log.debug("Book rental created successfully");
        return id;
    }

    private BookRent createBookRent(final RentDto rentDto) {
        BookRent bookRent = new BookRent();
        bookRent.setBookId(rentDto.getBookId());
        bookRent.setMemberId(rentDto.getMemberId());
        return bookRent;
    }
}
