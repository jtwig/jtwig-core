package org.jtwig.render.node.renderer;

import org.jtwig.model.tree.BlockNode;
import org.jtwig.render.RenderRequest;
import org.jtwig.render.context.ContextItem;
import org.jtwig.render.context.model.BlockDefinition;
import org.jtwig.render.node.RenderNodeService;
import org.jtwig.renderable.Renderable;
import org.jtwig.resource.reference.ResourceReference;

public class BlockNodeRender implements NodeRender<BlockNode> {
    @Override
    public Renderable render(RenderRequest renderRequest, BlockNode node) {
        String identifier = node.getIdentifier();
        ResourceReference current = renderRequest.getRenderContext().getResourceContext().getCurrent().getItem();
        renderRequest.getRenderContext().getBlockContext().getCurrent().add(node, current);
        BlockDefinition definition = renderRequest.getRenderContext().getBlockContext().getCurrent().get(identifier).get();
        RenderNodeService renderNodeService = renderRequest.getEnvironment().getRenderEnvironment().getRenderNodeService();

        renderRequest.getRenderContext().getResourceContext().start(new ContextItem<>(definition.getSource()));
        Renderable render = renderNodeService.render(renderRequest, definition.getNode());
        renderRequest.getRenderContext().getResourceContext().end();
        return render;
    }
}
