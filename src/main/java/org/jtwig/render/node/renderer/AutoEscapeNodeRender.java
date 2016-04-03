package org.jtwig.render.node.renderer;

import org.jtwig.model.tree.AutoEscapeNode;
import org.jtwig.render.RenderRequest;
import org.jtwig.render.context.model.EscapeMode;
import org.jtwig.renderable.Renderable;

public class AutoEscapeNodeRender implements NodeRender<AutoEscapeNode> {
    @Override
    public Renderable render(RenderRequest request, AutoEscapeNode node) {
        request.getRenderContext().getEscapeModeContext()
                .set(node.getEscapeMode().or(EscapeMode.HTML));
        return request.getEnvironment().getRenderEnvironment().getRenderNodeService()
                .render(request, node.getContent());
    }
}
