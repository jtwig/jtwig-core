package org.jtwig.model.tree;

import org.jtwig.model.position.Position;
import org.jtwig.model.position.Traceable;
import org.jtwig.model.tree.visitor.NodeVisitor;

public abstract class Node implements Traceable {
    private final Position position;

    protected Node(Position position) {
        this.position = position;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    public void visit (NodeVisitor nodeConsumer) {
        nodeConsumer.consume(this);
    }
}
