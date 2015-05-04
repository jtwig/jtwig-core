package org.jtwig.render.impl;

import org.jtwig.context.model.EscapeMode;
import org.jtwig.render.RenderResult;
import org.jtwig.render.Renderable;

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
