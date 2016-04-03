package org.jtwig.model.tree;

import org.jtwig.model.position.Position;
import org.jtwig.model.position.Traceable;

public abstract class Node implements Traceable {
    private final Position position;

    protected Node(Position position) {
        this.position = position;
    }

    @Override
    public Position getPosition() {
        return position;
    }
}
