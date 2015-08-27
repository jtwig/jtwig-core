package org.jtwig.exceptions;

public class JtwigException extends RuntimeException {
    public JtwigException(String message) {
        super(message);
    }

    public JtwigException(Throwable cause) {
        super(cause);
    }

    public JtwigException(String message, Throwable cause) {
        super(message, cause);
    }
}
