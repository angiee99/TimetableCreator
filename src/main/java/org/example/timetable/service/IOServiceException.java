package org.example.timetable.service;

public class IOServiceException extends Exception {
    public IOServiceException(String message) {
        super(message);
    }

    public IOServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
