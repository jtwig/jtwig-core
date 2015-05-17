package org.jtwig.resource.exceptions;

import org.jtwig.exceptions.JtwigException;

public class ResourceNotFoundException extends JtwigException {
    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(Throwable e) {
        super(e);
    }
}
