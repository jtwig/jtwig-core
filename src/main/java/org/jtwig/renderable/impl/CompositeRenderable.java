package org.jtwig.renderable.impl;

import org.jtwig.renderable.RenderResult;
import org.jtwig.renderable.Renderable;

import java.util.Collection;

public class CompositeRenderable implements Renderable {
    private final Collection<Renderable> renderableCollection;

    public CompositeRenderable(Collection<Renderable> renderableCollection) {
        this.renderableCollection = renderableCollection;
    }

    @Override
    public RenderResult appendTo(RenderResult result) {
        for (Renderable renderable : renderableCollection) {
            renderable.appendTo(result);
        }
        return result;
    }
}
