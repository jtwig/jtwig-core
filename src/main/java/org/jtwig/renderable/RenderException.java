package org.jtwig.renderable;

import org.jtwig.exceptions.JtwigException;

public class RenderException extends JtwigException {
    public RenderException(Throwable cause) {
        super(cause);
    }

    public RenderException(String message) {
        super(message);
    }
}
