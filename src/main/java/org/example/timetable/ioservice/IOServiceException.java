package org.example.timetable.ioservice;

public class IOServiceException extends Exception {
    public IOServiceException(String message) {
        super(message);
    }

    public IOServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
