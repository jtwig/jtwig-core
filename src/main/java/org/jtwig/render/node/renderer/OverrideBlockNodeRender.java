package org.jtwig.render.node.renderer;

import org.jtwig.model.tree.OverrideBlockNode;
import org.jtwig.render.RenderRequest;
import org.jtwig.renderable.Renderable;
import org.jtwig.renderable.impl.EmptyRenderable;

public class OverrideBlockNodeRender implements NodeRender<OverrideBlockNode> {
    @Override
    public Renderable render(RenderRequest renderRequest, OverrideBlockNode node) {
        renderRequest.getRenderContext().getBlockContext().getCurrent().add(node);
        return EmptyRenderable.instance();
    }
}
