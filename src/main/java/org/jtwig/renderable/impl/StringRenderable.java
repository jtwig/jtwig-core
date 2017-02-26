package org.jtwig.renderable.impl;

import org.jtwig.escape.EscapeEngine;
import org.jtwig.escape.NoneEscapeEngine;
import org.jtwig.renderable.RenderResult;
import org.jtwig.renderable.Renderable;

public class StringRenderable implements Renderable {
    private final String content;
    private final EscapeEngine escapeEngine;

    public StringRenderable(String content) {
        this(content, NoneEscapeEngine.instance());
    }

    public StringRenderable(String content, EscapeEngine escapeEngine) {
        this.content = content;
        this.escapeEngine = escapeEngine;
    }

    @Override
    public RenderResult appendTo(RenderResult result) {
        result.append(escapeEngine.escape(content));
        return result;
    }
}
