package org.jtwig.render.model;

import com.google.common.base.Optional;

import org.jtwig.render.Renderable;

import java.io.OutputStream;

public class OverrideRenderable implements Renderable {
    private Optional<Renderable> override = Optional.absent();
    private final Renderable defaultContent;

    public OverrideRenderable(Renderable defaultContent) {
        this.defaultContent = defaultContent;
    }

    public OverrideRenderable overrideWith(Renderable override) {
        this.override = Optional.of(override);
        return this;
    }

    @Override
    public void accept(OutputStream outputStream) {
        override.or(defaultContent).accept(outputStream);
    }

    public Renderable getDefault() {
        return defaultContent;
    }

}
