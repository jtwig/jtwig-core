package org.jtwig.model.tree;

import org.jtwig.model.position.Position;
import org.jtwig.model.tree.visitor.NodeVisitor;

public class ContentNode extends Node {
    private final Node content;

    protected ContentNode(Position position, Node content) {
        super(position);
        this.content = content;
    }

    public Node getContent() {
        return content;
    }

    @Override
    public void visit(NodeVisitor nodeConsumer) {
        super.visit(nodeConsumer);
        content.visit(nodeConsumer);
    }
}
