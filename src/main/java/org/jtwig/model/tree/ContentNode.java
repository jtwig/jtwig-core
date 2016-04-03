package org.jtwig.model.tree;

import org.jtwig.model.position.Position;

public class ContentNode extends Node {
    private final Node content;

    protected ContentNode(Position position, Node content) {
        super(position);
        this.content = content;
    }

    public Node getContent() {
        return content;
    }
}
