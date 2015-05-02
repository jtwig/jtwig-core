package org.jtwig.parser.parboiled.base;

import org.jtwig.parser.parboiled.ParserContext;

public class BooleanParser extends BasicParser<Boolean> {
    public BooleanParser(ParserContext context) {
        super(BooleanParser.class, context);
    }
}
