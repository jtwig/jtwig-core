package org.jtwig.util;

import org.jtwig.model.position.Position;

public class ErrorMessageFormatter {
    public static String errorMessage (Position position, String message) {
        return String.format("%s -> %s", position, message);
    }
}
