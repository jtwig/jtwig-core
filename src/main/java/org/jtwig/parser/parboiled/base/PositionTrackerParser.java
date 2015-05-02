package org.jtwig.parser.parboiled.base;

import org.jtwig.model.position.Position;
import org.jtwig.parser.parboiled.ParserContext;
import org.parboiled.BaseParser;

import static org.parboiled.Parboiled.createParser;

public class PositionTrackerParser extends BasicParser<Position> {
    public PositionTrackerParser(ParserContext context) {
        super(PositionTrackerParser.class, context);
    }

    public boolean PushPosition() {
        push(new Position(getContext().getPosition().line, getContext().getPosition().column));
        return true;
    }

    public Position currentPosition() {
        return new Position(super.getContext().getPosition().line, super.getContext().getPosition().column);
    }
}
