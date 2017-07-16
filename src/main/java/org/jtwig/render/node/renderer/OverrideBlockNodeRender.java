package org.jtwig.render.node.renderer;

import org.jtwig.model.tree.OverrideBlockNode;
import org.jtwig.render.RenderRequest;
import org.jtwig.render.context.model.BlockContext;
import org.jtwig.renderable.Renderable;
import org.jtwig.renderable.impl.EmptyRenderable;
import org.jtwig.resource.reference.ResourceReference;

public class OverrideBlockNodeRender implements NodeRender<OverrideBlockNode> {
    @Override
    public Renderable render(RenderRequest renderRequest, OverrideBlockNode node) {
        ResourceReference current = renderRequest.getRenderContext().getCurrent(ResourceReference.class);
        renderRequest.getRenderContext().getCurrent(BlockContext.class).addLast(node, current);
        return EmptyRenderable.instance();
    }
}
