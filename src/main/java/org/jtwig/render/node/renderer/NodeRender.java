package org.jtwig.render.node.renderer;

import org.jtwig.model.tree.Node;
import org.jtwig.render.RenderRequest;
import org.jtwig.renderable.Renderable;

public interface NodeRender<T extends Node> {
    Renderable render (RenderRequest renderRequest, T node);
}
