package org.jtwig.render.impl;

import org.jtwig.render.RenderResult;
import org.jtwig.render.Renderable;

import java.util.Collection;

public class CompositeRenderable implements Renderable {
    private final Collection<Renderable> renderableCollection;

    public CompositeRenderable(Collection<Renderable> renderableCollection) {
        this.renderableCollection = renderableCollection;
    }

    @Override
    public void appendTo(RenderResult result) {
        for (Renderable renderable : renderableCollection) {
            renderable.appendTo(result);
        }
    }
}
