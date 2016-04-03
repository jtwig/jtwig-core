package org.jtwig.render.node.renderer;

import org.jtwig.model.tree.SetNode;
import org.jtwig.render.RenderRequest;
import org.jtwig.render.expression.CalculateExpressionService;
import org.jtwig.renderable.Renderable;
import org.jtwig.renderable.impl.EmptyRenderable;
import org.jtwig.value.context.ValueContext;

public class SetNodeRender implements NodeRender<SetNode> {
    @Override
    public Renderable render(RenderRequest request, SetNode node) {
        CalculateExpressionService calculateExpressionService = request.getEnvironment().getRenderEnvironment().getCalculateExpressionService();

        Object result = calculateExpressionService.calculate(request, node.getExpression());

        ValueContext valueContext = request.getRenderContext().getValueContext().getCurrent();
        valueContext.with(node.getVariableExpression().getIdentifier(), result);
        return EmptyRenderable.instance();
    }
}
