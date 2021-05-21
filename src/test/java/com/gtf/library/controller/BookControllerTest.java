package com.gtf.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gtf.library.entity.Book;
import com.gtf.library.exceptions.NotFoundException;
import com.gtf.library.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static com.gtf.library.utils.Constants.BOOK_DTO;
import static com.gtf.library.utils.Constants.HTTP_CODE;
import static com.gtf.library.utils.Constants.MORE_INFORMATION;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@Slf4j
class BookControllerTest {
    private static final ResultHandler logResultHandler = result -> log.info(result.getResponse().getContentAsString());

    @Mock
    private BookService bookService;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        BookController bookController = new BookController(bookService);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController)
                .setControllerAdvice(new ControllerAdvice())
                .build();
    }

    //region Tests

    @Test
    void controllerAddBookSuccessfully() throws Exception {
        when(bookService.addNewBook(any())).thenReturn(3L);
        mockMvc.perform(post("/book")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsBytes(BOOK_DTO)))
                .andDo(logResultHandler)
                .andExpect(status().isCreated());
    }

    @Test
    void controllerAddBookFailure() throws Exception {
        when(bookService.addNewBook(any())).thenThrow(new IllegalArgumentException("Entity is empty"));
        mockMvc.perform(post("/book")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsBytes(BOOK_DTO)))
                .andDo(logResultHandler)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(HTTP_CODE, is(400)))
                .andExpect(jsonPath(MORE_INFORMATION, is("Request is not valid")));
    }

    @Test
    void controllerAddBookMethodNotSupported() throws Exception {
        mockMvc.perform(get("/book")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsBytes(BOOK_DTO)))
                .andDo(logResultHandler)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(HTTP_CODE, is(400)))
                .andExpect(jsonPath(MORE_INFORMATION, is("Request is not valid")));
    }

    @Test
    void controllerSearchBookSuccessfully() throws Exception {
        when(bookService.searchBooks(anyString())).thenReturn(Collections.singletonList(createBook()));
        mockMvc.perform(get("/book?keyword=Sample"))
                .andDo(logResultHandler)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void controllerSearchBookNoBooks() throws Exception {
        when(bookService.searchBooks(any())).thenThrow(new NotFoundException("Entity is empty"));
        mockMvc.perform(get("/book?keyword=Sample"))
                .andDo(logResultHandler)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath(HTTP_CODE, is(404)))
                .andExpect(jsonPath(MORE_INFORMATION, is("Resource not found")));
    }

    @Test
    void controllerGetAllBooksSuccessfully() throws Exception {
        when(bookService.getAllBooks()).thenReturn(Collections.singletonList(createBook()));
        mockMvc.perform(get("/books"))
                .andDo(logResultHandler)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void controllerGetAllBooksNoBooks() throws Exception {
        when(bookService.getAllBooks()).thenThrow(new NotFoundException("Entity is empty"));
        mockMvc.perform(get("/books"))
                .andDo(logResultHandler)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath(HTTP_CODE, is(404)))
                .andExpect(jsonPath(MORE_INFORMATION, is("Resource not found")));
    }

    @Test
    void controllerGetAllBooksMethodNotAllowed() throws Exception {
        mockMvc.perform(post("/books"))
                .andDo(logResultHandler)
                .andExpect(status().isMethodNotAllowed())
                .andExpect(jsonPath(HTTP_CODE, is(405)))
                .andExpect(jsonPath(MORE_INFORMATION, is("Method not supported")));
    }

    @Test
    void controllerFindBookByIdSuccessfully() throws Exception {
        when(bookService.findBookById(3L)).thenReturn(createBook());
        mockMvc.perform(get("/book/3"))
                .andDo(logResultHandler)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void controllerFindBookByIdNoBooks() throws Exception {
        when(bookService.findBookById(3L)).thenThrow(new NotFoundException("Entity is empty"));
        mockMvc.perform(get("/book/3"))
                .andDo(logResultHandler)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath(HTTP_CODE, is(404)))
                .andExpect(jsonPath(MORE_INFORMATION, is("Resource not found")));
    }

    //endregion

    //region Private methods

    private Book createBook() {
        final Book book = new Book();
        book.setId(3L);
        book.setIsbn("900-9-0990909-9-787");
        book.setTitle("Sample Title");
        return book;
    }

    //endregion
}