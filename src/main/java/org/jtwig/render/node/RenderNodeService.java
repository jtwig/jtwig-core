package org.jtwig.render.node;

import com.google.common.base.Optional;
import org.jtwig.model.tree.Node;
import org.jtwig.render.RenderRequest;
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
            request.getRenderContext().getEscapeModeContext().start(request.getRenderContext().getEscapeModeContext().getCurrent());
            Renderable renderable = nodeRenderOptional.get().render(request, node);
            request.getRenderContext().getEscapeModeContext().end();
            return renderable;
        } else {
            throw new IllegalArgumentException(ErrorMessageFormatter.errorMessage(node.getPosition(), String.format("No render found for %s", node.getClass())));
        }
    }
}
