package com.gtf.library.controller;

import com.gtf.library.exceptions.BookNotAvailableException;
import com.gtf.library.exceptions.NotFoundException;
import com.gtf.library.exceptions.RentalLimitExceededException;
import com.gtf.library.model.error.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

@RestControllerAdvice
@Slf4j
public class ControllerAdvice {

    private static final String ENTRY_MESSAGE_WITH_INFO = "Entering exception handling method for '{}' and error message: '{}'";

    @ExceptionHandler({BookNotAvailableException.class})
    public ErrorResponse bookNotAvailable(BookNotAvailableException exception, HttpServletResponse response) {
        log.error(ENTRY_MESSAGE_WITH_INFO, exception.getClass().getName(), exception.getMessage());
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.name(),
                "Either unable to find the books with the information provided or book not available");
    }

    @ExceptionHandler({NotFoundException.class})
    public ErrorResponse notFoundException(NotFoundException exception, HttpServletResponse response) {
        log.error(ENTRY_MESSAGE_WITH_INFO, exception.getClass().getName(), exception.getMessage());
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.name(),
                "Resource not found");
    }

    @ExceptionHandler({RentalLimitExceededException.class})
    public ErrorResponse rentalProcessException(RentalLimitExceededException exception, HttpServletResponse response) {
        log.error(ENTRY_MESSAGE_WITH_INFO, exception.getClass().getName(), exception.getMessage());
        response.setStatus(HttpStatus.CONFLICT.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        return new ErrorResponse(HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.name(),
                "User reached maximum limit for book rentals");
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ErrorResponse httpRequestMethodNotSupportedHandler(HttpRequestMethodNotSupportedException exception, HttpServletResponse response) {
        log.error(ENTRY_MESSAGE_WITH_INFO, exception.getClass().getName(), exception.getMessage());
        response.setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        return new ErrorResponse(HttpStatus.METHOD_NOT_ALLOWED.value(),
                HttpStatus.METHOD_NOT_ALLOWED.name(),
                "Method not supported");
    }

    @ExceptionHandler({IllegalArgumentException.class,
            HttpMessageConversionException.class,
            HttpMediaTypeException.class,
            MissingServletRequestParameterException.class,
            ConstraintViolationException.class,
            MethodArgumentNotValidException.class,
            MissingRequestHeaderException.class})
    public ErrorResponse badRequestException(Exception exception, HttpServletResponse response) {
        log.error(ENTRY_MESSAGE_WITH_INFO, exception.getClass().getName(), exception.getMessage());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.name(),
                "Request is not valid");
    }

    @ExceptionHandler({Exception.class})
    public ErrorResponse genericException(Exception exception, HttpServletResponse response) {
        log.error(ENTRY_MESSAGE_WITH_INFO, exception.getClass().getName(), exception.getMessage());
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.name(),
                "Internal server exception");
    }
}
