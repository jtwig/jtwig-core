package org.jtwig.render.node.renderer;

import org.jtwig.model.tree.BlockNode;
import org.jtwig.model.tree.Node;
import org.jtwig.render.RenderRequest;
import org.jtwig.render.node.RenderNodeService;
import org.jtwig.renderable.Renderable;

public class BlockNodeRender implements NodeRender<BlockNode> {
    @Override
    public Renderable render(RenderRequest renderRequest, BlockNode node) {
        String identifier = node.getIdentifier();
        renderRequest.getRenderContext().getBlockContext().getCurrent().add(node);
        Node content = renderRequest.getRenderContext().getBlockContext().getCurrent().get(identifier).get();
        RenderNodeService renderNodeService = renderRequest.getEnvironment().getRenderEnvironment().getRenderNodeService();
        return renderNodeService.render(renderRequest, content);
    }
}
