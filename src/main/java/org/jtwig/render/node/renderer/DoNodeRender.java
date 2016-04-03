package org.jtwig.render.node.renderer;

import org.jtwig.model.tree.DoNode;
import org.jtwig.render.RenderRequest;
import org.jtwig.renderable.Renderable;
import org.jtwig.renderable.impl.EmptyRenderable;

public class DoNodeRender implements NodeRender<DoNode> {
    @Override
    public Renderable render(RenderRequest renderRequest, DoNode node) {
        renderRequest.getEnvironment().getRenderEnvironment().getCalculateExpressionService().calculate(renderRequest, node.getExpression());
        return EmptyRenderable.instance();
    }
}
