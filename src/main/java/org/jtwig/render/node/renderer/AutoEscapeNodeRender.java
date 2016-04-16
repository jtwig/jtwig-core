package org.jtwig.render.node.renderer;

import com.google.common.base.Optional;
import org.jtwig.model.tree.AutoEscapeNode;
import org.jtwig.render.RenderRequest;
import org.jtwig.render.escape.EscapeEngine;
import org.jtwig.renderable.RenderException;
import org.jtwig.renderable.Renderable;
import org.jtwig.util.ErrorMessageFormatter;

public class AutoEscapeNodeRender implements NodeRender<AutoEscapeNode> {
    @Override
    public Renderable render(RenderRequest request, AutoEscapeNode node) {
        String escapeModeName = node.getEscapeEngineName().or(request.getEnvironment().getRenderEnvironment().getDefaultEscapeEngineName());
        Optional<EscapeEngine> escapeEngineOptional = request.getEnvironment().getRenderEnvironment().getEscapeEngineSelector().escapeEngineFor(escapeModeName);
        if (escapeEngineOptional.isPresent()) {
            request.getRenderContext().getEscapeEngineContext()
                    .set(escapeEngineOptional.get());
            return request.getEnvironment().getRenderEnvironment().getRenderNodeService()
                    .render(request, node.getContent());
        } else {
            throw new RenderException(ErrorMessageFormatter.errorMessage(node.getPosition(), String.format("Invalid escape engine requested '%s'. Only supporting [%s]", escapeModeName, request.getEnvironment().getRenderEnvironment().getEscapeEngineSelector().availableEscapeEngines())));
        }
    }
}
