package org.jtwig.renderable.impl;

import org.jtwig.renderable.RenderResult;
import org.jtwig.renderable.Renderable;

public class FlushRenderable implements Renderable {
    private static final FlushRenderable INSTANCE = new FlushRenderable();

    public static FlushRenderable instance() {
        return INSTANCE;
    }

    private FlushRenderable () {}

    @Override
    public RenderResult appendTo(RenderResult result) {
        result.flush();
        return result;
    }
}
