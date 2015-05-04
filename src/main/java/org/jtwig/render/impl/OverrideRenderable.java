package org.jtwig.render.impl;

import com.google.common.base.Optional;
import org.jtwig.render.RenderResult;
import org.jtwig.render.Renderable;

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
    public RenderResult appendTo(RenderResult result) {
        override.or(defaultContent).appendTo(result);
        return result;
    }

    public Renderable getDefault() {
        return defaultContent;
    }

}
