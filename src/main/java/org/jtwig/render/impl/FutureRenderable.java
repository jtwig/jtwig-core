package org.jtwig.render.impl;

import org.jtwig.render.RenderResult;
import org.jtwig.render.Renderable;

public class FutureRenderable implements Renderable {
    private Renderable renderable;

    public FutureRenderable complete(Renderable renderable) {
        if (this.renderable != null) throw new IllegalStateException("Future Renderable already completed");
        this.renderable = renderable;
        return this;
    }

    @Override
    public void appendTo(RenderResult result) {
        if (renderable != null) {
            renderable.appendTo(result);
        } else {
            throw new IllegalStateException("Current renderable is in invalid state, it must be completed first");
        }
    }
}
