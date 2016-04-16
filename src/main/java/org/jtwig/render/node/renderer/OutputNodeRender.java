package org.jtwig.render.node.renderer;

import org.jtwig.model.tree.OutputNode;
import org.jtwig.render.RenderRequest;
import org.jtwig.render.escape.EscapeEngine;
import org.jtwig.render.expression.CalculateExpressionService;
import org.jtwig.renderable.Renderable;
import org.jtwig.renderable.impl.StringRenderable;

public class OutputNodeRender implements NodeRender<OutputNode> {
    @Override
    public Renderable render(RenderRequest request, OutputNode node) {
        CalculateExpressionService calculateExpressionService = request.getEnvironment().getRenderEnvironment().getCalculateExpressionService();

        Object calculate = calculateExpressionService.calculate(request, node.getExpression());
        EscapeEngine escapeEngine = request.getRenderContext().getEscapeEngineContext().getCurrent();
        return new StringRenderable(getString(request, calculate), escapeEngine);
    }

    private String getString(RenderRequest request, Object input) {
        return request.getEnvironment().getValueEnvironment().getStringConverter().convert(input);
    }
}
