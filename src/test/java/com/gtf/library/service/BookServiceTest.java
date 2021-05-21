package com.gtf.library.service;

import com.gtf.library.entity.Book;
import com.gtf.library.exceptions.NotFoundException;
import com.gtf.library.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.gtf.library.utils.Assertions.assertThatThrows;
import static com.gtf.library.utils.Constants.BOOK_DTO;
import static org.hamcrest.core.Is.isA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private ModelMapper modelMapper;

    @Test
    void bookServiceAddBookSuccessfully() {
        given(bookRepository.save(any())).willReturn(createBook());

        assertEquals(3, bookService.addNewBook(BOOK_DTO));

        verify(bookRepository, times(1)).save(any());
        verify(modelMapper, times(1)).map(BOOK_DTO, Book.class);
    }

    @Test
    void bookServiceAddBookThrowsException() {
        given(bookRepository.save(any())).willThrow(new IllegalArgumentException("Entity is null"));

        assertThatThrows(() -> bookService.addNewBook(BOOK_DTO),
                isA(IllegalArgumentException.class),
                "Entity is null");

        verify(bookRepository, times(1)).save(any());
        verify(modelMapper, times(1)).map(BOOK_DTO, Book.class);
    }

    @Test
    void bookServiceSearchBookSuccessfully() {
        given(bookRepository.search(anyString())).willReturn(Collections.singletonList(createBook()));
        final List<Book> books = bookService.searchBooks("Sample");
        assertEquals(1, books.size());
        verify(bookRepository, times(1)).search("Sample");
    }

    @Test
    void bookServiceSearchBookWithNoResponse() {
        given(bookRepository.search(anyString())).willReturn(Collections.emptyList());
        assertThatThrows(() -> bookService.searchBooks("Sample"),
                isA(NotFoundException.class),
                "Unable to find book with search keyword: Sample");
        verify(bookRepository, times(1)).search("Sample");
    }

    @Test
    void bookServiceFindBookByIdNoBooks() {
        given(bookRepository.findById(any())).willReturn(Optional.empty());
        assertThatThrows(() -> bookService.findBookById(3L),
                isA(NotFoundException.class),
                "Unable to find book with id: 3");
        verify(bookRepository, times(1)).findById(3L);
    }

    @Test
    void bookServiceFindBookByIdSuccessful() {
        given(bookRepository.findById(any())).willReturn(Optional.of(createBook()));
        final Book book = bookService.findBookById(3L);
        assertEquals(3, book.getId());
        assertEquals("Sample Title", book.getTitle());
        assertEquals("900-9-0990909-9-787", book.getIsbn());
        verify(bookRepository, times(1)).findById(3L);
    }

    @Test
    void bookServiceGetAllBooksSuccessful() {
        given(bookRepository.findAll()).willReturn(Collections.singletonList(createBook()));
        final List<Book> books = bookService.getAllBooks();
        assertEquals(1, books.size());
        verify(bookRepository, times(1)).findAll();
    }

    private Book createBook() {
        final Book book = new Book();
        book.setId(3L);
        book.setIsbn("900-9-0990909-9-787");
        book.setTitle("Sample Title");
        return book;
    }
}
