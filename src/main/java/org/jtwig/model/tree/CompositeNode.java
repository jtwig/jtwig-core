package org.jtwig.model.tree;

import org.jtwig.model.position.Position;

import java.util.Collection;

public class CompositeNode extends Node {
    private final Collection<Node> nodes;

    public CompositeNode(Position position, Collection<Node> nodes) {
        super(position);
        this.nodes = nodes;
    }

    public Collection<Node> getNodes() {
        return nodes;
    }
}
