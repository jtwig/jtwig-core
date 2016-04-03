package org.jtwig.model.expression;

import org.jtwig.model.position.Position;
import org.jtwig.model.position.Traceable;

public abstract class Expression implements Traceable {
    private final Position position;

    protected Expression(Position position) {
        this.position = position;
    }

    @Override
    public Position getPosition() {
        return position;
    }
}
