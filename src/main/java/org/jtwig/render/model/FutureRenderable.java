package org.jtwig.render.model;

import org.jtwig.render.Renderable;

import java.io.OutputStream;

public class FutureRenderable implements Renderable {
    private Renderable renderable;

    public FutureRenderable complete(Renderable renderable) {
        if (this.renderable != null) throw new IllegalStateException("Future Renderable already completed");
        this.renderable = renderable;
        return this;
    }

    @Override
    public void accept(OutputStream outputStream) {
        if (renderable != null) {
            renderable.accept(outputStream);
        } else {
            throw new IllegalStateException("Current renderable is in invalid state, it must be completed first");
        }
    }
}
