package org.jtwig.exceptions;

public class CalculationException extends JtwigException {
    public CalculationException(String message) {
        super(message);
    }

    public CalculationException(String message, Throwable cause) {
        super(message, cause);
    }
}
