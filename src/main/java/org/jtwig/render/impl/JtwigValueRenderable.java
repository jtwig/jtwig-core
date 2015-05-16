package org.jtwig.render.impl;

import org.jtwig.context.model.EscapeMode;
import org.jtwig.render.RenderResult;
import org.jtwig.render.Renderable;
import org.jtwig.value.JtwigValue;

public class JtwigValueRenderable implements Renderable {
    private final JtwigValue value;
    private final EscapeMode escapeMode;

    public JtwigValueRenderable(JtwigValue value, EscapeMode escapeMode) {
        this.value = value;
        this.escapeMode = escapeMode;
    }

    @Override
    public RenderResult appendTo(RenderResult result) {
        return result.append(escapeMode.escape(value.asString()));
    }
}
