package org.jtwig.model.tree;

import org.jtwig.context.RenderContext;
import org.jtwig.context.model.EscapeMode;
import org.jtwig.model.position.Position;
import org.jtwig.render.Renderable;
import org.jtwig.render.impl.StringRenderable;

public class VerbatimNode extends Node {
    private final String content;

    public VerbatimNode(Position position, String content) {
        super(position);
        this.content = content;
    }

    @Override
    public Renderable render(RenderContext context) {
        return new StringRenderable(content, EscapeMode.NONE);
    }
}
