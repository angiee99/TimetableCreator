package org.example.timetable.model.exception;

public class NoSolutionFoundException extends RuntimeException{
    public NoSolutionFoundException(String message) {
        super(message);
    }

    public NoSolutionFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
