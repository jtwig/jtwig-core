package org.jtwig.resource.exceptions;

import org.jtwig.exceptions.JtwigException;

public class ResourceException extends JtwigException {

    public ResourceException(String message, Throwable cause) {
        super(message, cause);
    }
}
