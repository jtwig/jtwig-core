package org.jtwig.render.impl;

import org.jtwig.render.RenderResult;
import org.jtwig.render.Renderable;

public class FlushRenderable implements Renderable {
    public static FlushRenderable instance() {
        return new FlushRenderable();
    }

    private FlushRenderable () {}

    @Override
    public RenderResult appendTo(RenderResult result) {
        result.flush();
        return result;
    }
}
