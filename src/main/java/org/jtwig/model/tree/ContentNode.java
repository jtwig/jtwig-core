package org.jtwig.model.tree;

import org.jtwig.context.RenderContext;
import org.jtwig.model.position.Position;
import org.jtwig.model.tree.visitor.NodeVisitor;
import org.jtwig.render.Renderable;

public class ContentNode extends Node {
    private final Node content;

    protected ContentNode(Position position, Node content) {
        super(position);
        this.content = content;
    }

    @Override
    public Renderable render(RenderContext context) {
        return context.nodeRenderer()
                .render(content);
    }

    public Node getContent() {
        return content;
    }

    @Override
    public void visit(NodeVisitor visitor) {
        super.visit(visitor);
        content.visit(visitor);
    }
}
