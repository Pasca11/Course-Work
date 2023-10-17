package ru.amir.library.utils;

public class NotEnoughBooksException extends RuntimeException {
    public NotEnoughBooksException(String message) {
        super(message);
    }
}
