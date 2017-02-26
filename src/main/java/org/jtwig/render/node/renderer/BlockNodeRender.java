package org.jtwig.render.node.renderer;

import org.jtwig.model.tree.BlockNode;
import org.jtwig.render.RenderRequest;
import org.jtwig.render.context.model.BlockContext;
import org.jtwig.render.context.model.BlockDefinition;
import org.jtwig.render.node.RenderNodeService;
import org.jtwig.renderable.Renderable;
import org.jtwig.resource.reference.ResourceReference;

public class BlockNodeRender implements NodeRender<BlockNode> {
    @Override
    public Renderable render(RenderRequest renderRequest, BlockNode node) {
        String identifier = node.getIdentifier();
        ResourceReference current = renderRequest.getRenderContext().getCurrent(ResourceReference.class);
        renderRequest.getRenderContext().getCurrent(BlockContext.class).add(node, current);
        BlockDefinition definition = renderRequest.getRenderContext().getCurrent(BlockContext.class).get(identifier).get();
        RenderNodeService renderNodeService = renderRequest.getEnvironment().getRenderEnvironment().getRenderNodeService();

        renderRequest.getRenderContext().start(ResourceReference.class, definition.getSource());
        Renderable render = renderNodeService.render(renderRequest, definition.getNode());
        renderRequest.getRenderContext().end(ResourceReference.class);
        return render;
    }
}
