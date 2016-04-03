package org.jtwig.render.node.renderer;

import org.jtwig.model.tree.FlushNode;
import org.jtwig.render.RenderRequest;
import org.jtwig.renderable.Renderable;
import org.jtwig.renderable.impl.FlushRenderable;

public class FlushNodeRender implements NodeRender<FlushNode> {
    @Override
    public Renderable render(RenderRequest renderRequest, FlushNode node) {
        return FlushRenderable.instance();
    }
}
