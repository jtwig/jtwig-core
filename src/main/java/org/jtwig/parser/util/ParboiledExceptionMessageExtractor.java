package org.jtwig.parser.util;

import org.parboiled.errors.ParserRuntimeException;

public class ParboiledExceptionMessageExtractor {
    public String extract (ParserRuntimeException exception) {
        String[] split = exception.getMessage().split("\n");
        return split[1] + "\n" + split[2];
    }
}
