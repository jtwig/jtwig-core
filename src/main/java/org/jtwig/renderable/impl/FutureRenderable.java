package org.jtwig.renderable.impl;

import org.jtwig.renderable.RenderResult;
import org.jtwig.renderable.Renderable;

public class FutureRenderable implements Renderable {
    private Renderable renderable;

    public FutureRenderable complete(Renderable renderable) {
        if (this.renderable != null) throw new IllegalStateException("Future Renderable already completed");
        this.renderable = renderable;
        return this;
    }

    @Override
    public RenderResult appendTo(RenderResult result) {
        if (renderable != null) {
            renderable.appendTo(result);
            return result;
        } else {
            throw new IllegalStateException("Current renderable is in invalid state, it must be completed first");
        }
    }
}
