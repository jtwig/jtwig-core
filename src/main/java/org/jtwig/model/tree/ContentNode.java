package org.jtwig.model.tree;

import org.jtwig.context.RenderContext;
import org.jtwig.model.position.Position;
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
}
