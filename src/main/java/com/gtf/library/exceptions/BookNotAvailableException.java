package com.gtf.library.exceptions;

public class BookNotAvailableException extends  RuntimeException {
    public BookNotAvailableException(String message) {
        super(message);
    }
}
