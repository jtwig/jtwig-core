package org.jtwig.model.tree;

import org.jtwig.context.RenderContext;
import org.jtwig.context.values.ScopeType;
import org.jtwig.model.position.Position;
import org.jtwig.model.position.Traceable;
import org.jtwig.render.Renderable;

public abstract class Node implements Traceable {
    private final Position position;

    protected Node(Position position) {
        this.position = position;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    public abstract Renderable render (RenderContext context);

    public ScopeType scopeType () {
        return ScopeType.SHARE;
    }
}
