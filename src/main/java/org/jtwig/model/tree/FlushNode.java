package org.jtwig.model.tree;

import org.jtwig.context.RenderContext;
import org.jtwig.model.position.Position;
import org.jtwig.render.Renderable;
import org.jtwig.render.impl.FlushRenderable;

public class FlushNode extends Node {
    public FlushNode(Position position) {
        super(position);
    }

    @Override
    public Renderable render(RenderContext context) {
        return FlushRenderable.instance();
    }
}
