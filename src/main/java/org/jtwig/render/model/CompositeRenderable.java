package org.jtwig.render.model;

import org.jtwig.render.Renderable;

import java.io.OutputStream;
import java.util.Collection;

public class CompositeRenderable implements Renderable {
    private final Collection<Renderable> renderableCollection;

    public CompositeRenderable(Collection<Renderable> renderableCollection) {
        this.renderableCollection = renderableCollection;
    }

    @Override
    public void accept(OutputStream outputStream) {
        for (Renderable renderable : renderableCollection) {
            renderable.accept(outputStream);
        }
    }
}
