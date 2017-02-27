package org.jtwig.render.node;

import com.google.common.base.Optional;
import org.jtwig.escape.EscapeEngine;
import org.jtwig.model.tree.Node;
import org.jtwig.render.RenderRequest;
import org.jtwig.render.listeners.RenderStage;
import org.jtwig.render.node.renderer.NodeRender;
import org.jtwig.renderable.Renderable;
import org.jtwig.util.ErrorMessageFormatter;

public class RenderNodeService {
    private final NodeRenderSelector nodeRenderSelector;

    public RenderNodeService(NodeRenderSelector nodeRenderSelector) {
        this.nodeRenderSelector = nodeRenderSelector;
    }

    public Renderable render(RenderRequest request, Node node) {
        Optional<NodeRender> nodeRenderOptional = nodeRenderSelector.renderFor(node);
        if (nodeRenderOptional.isPresent()) {
            request.getRenderContext().start(EscapeEngine.class, request.getRenderContext().getCurrent(EscapeEngine.class));
            request.getRenderContext().start(Node.class, node);

            request.getEnvironment().getRenderEnvironment().getRenderListeners().trigger(RenderStage.PRE_NODE_RENDER, request);
            Renderable renderable = nodeRenderOptional.get().render(request, node);
            request.getEnvironment().getRenderEnvironment().getRenderListeners().trigger(RenderStage.POST_NODE_RENDER, request);

            request.getRenderContext().end(EscapeEngine.class);
            request.getRenderContext().end(Node.class);
            return renderable;
        } else {
            throw new IllegalArgumentException(ErrorMessageFormatter.errorMessage(node.getPosition(), String.format("No render found for %s", node.getClass())));
        }
    }
}
