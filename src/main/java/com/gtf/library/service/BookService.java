package com.gtf.library.service;

import com.gtf.library.entity.Book;
import com.gtf.library.exceptions.NotFoundException;
import com.gtf.library.model.BookDto;
import com.gtf.library.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    public long addNewBook(final BookDto bookDto) {
        return bookRepository.save(convertToBookEntity(bookDto)).getId();
    }

    public List<Book> searchBooks(final String keyword) {
        return bookRepository.search(keyword).stream()
                .collect(Collectors.collectingAndThen(Collectors.toList(), result -> {
                    if(result.isEmpty()) throw new NotFoundException("Unable to find book with search keyword: " + keyword);
                    return result;
                }));
    }

    public Book findBookById(final Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Unable to find book with id: "+ id));
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    private Book convertToBookEntity(final BookDto bookDto) {
        return modelMapper.map(bookDto, Book.class);
    }
}
