package org.jtwig.render.node.renderer;

import com.google.common.base.Optional;
import org.jtwig.escape.EscapeEngine;
import org.jtwig.model.tree.ContentEscapeNode;
import org.jtwig.render.RenderRequest;
import org.jtwig.renderable.RenderException;
import org.jtwig.renderable.Renderable;
import org.jtwig.renderable.StringBuilderRenderResult;
import org.jtwig.renderable.impl.StringRenderable;
import org.jtwig.util.ErrorMessageFormatter;

public class ContentEscapeNodeRender implements NodeRender<ContentEscapeNode> {
    @Override
    public Renderable render(RenderRequest request, ContentEscapeNode node) {
        String escapeModeName = node.getEscapeEngineName().or(request.getEnvironment().getEscapeEnvironment().getDefaultEscapeEngine());
        Optional<EscapeEngine> escapeEngineOptional = request.getEnvironment().getEscapeEnvironment().getEscapeEngineSelector().escapeEngineFor(escapeModeName);
        if (escapeEngineOptional.isPresent()) {
            Renderable renderable = request.getEnvironment().getRenderEnvironment().getRenderNodeService()
                    .render(request, node.getContent());
            return new StringRenderable(
                    renderable.appendTo(new StringBuilderRenderResult()).content(),
                    escapeEngineOptional.get()
            );
        } else {
            throw new RenderException(ErrorMessageFormatter.errorMessage(node.getPosition(), String.format("Invalid escape engine requested '%s'. Only supporting [%s]", escapeModeName, request.getEnvironment().getEscapeEnvironment().getEscapeEngineSelector().availableEscapeEngines())));
        }
    }
}
