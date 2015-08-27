package org.jtwig.parser.parboiled.base;

import org.jtwig.model.position.Position;
import org.jtwig.parser.parboiled.ParserContext;

public class PositionTrackerParser extends BasicParser<Position> {
    public PositionTrackerParser(ParserContext context) {
        super(PositionTrackerParser.class, context);
    }

    public boolean PushPosition() {
        push(new Position(parserContext().resource(), getContext().getPosition().line, getContext().getPosition().column));
        return true;
    }

    public Position currentPosition() {
        return new Position(parserContext().resource(), super.getContext().getPosition().line, super.getContext().getPosition().column);
    }
}
