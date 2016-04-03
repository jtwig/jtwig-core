package org.jtwig.renderable.impl;

import org.jtwig.render.context.model.EscapeMode;
import org.jtwig.renderable.RenderResult;
import org.jtwig.renderable.Renderable;

public class StringRenderable implements Renderable {
    private final String content;
    private final EscapeMode mode;

    public StringRenderable(String content, EscapeMode mode) {
        this.content = content;
        this.mode = mode;
    }

    @Override
    public RenderResult appendTo(RenderResult result) {
        result.append(mode.escape(content));
        return result;
    }
}
