package org.jtwig.render.node.renderer;

import org.jtwig.model.expression.ConstantExpression;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.InjectableExpression;
import org.jtwig.model.tree.FilterNode;
import org.jtwig.render.RenderRequest;
import org.jtwig.render.expression.CalculateExpressionService;
import org.jtwig.render.node.RenderNodeService;
import org.jtwig.renderable.Renderable;
import org.jtwig.renderable.StringBuilderRenderResult;
import org.jtwig.renderable.impl.StringRenderable;

public class FilterNodeRender implements NodeRender<FilterNode> {
    @Override
    public Renderable render(RenderRequest renderRequest, FilterNode node) {
        RenderNodeService renderNodeService = renderRequest.getEnvironment().getRenderEnvironment().getRenderNodeService();
        CalculateExpressionService calculateExpressionService = renderRequest.getEnvironment().getRenderEnvironment().getCalculateExpressionService();

        InjectableExpression filterExpression = node.getFilterExpression();
        Renderable renderable = renderNodeService.render(renderRequest, node.getContent());
        String content = renderable.appendTo(new StringBuilderRenderResult()).content();
        ConstantExpression expression = new ConstantExpression(filterExpression.getPosition(), content);
        Expression injectedExpression = filterExpression.inject(expression);

        Object calculate = calculateExpressionService.calculate(renderRequest, injectedExpression);
        return new StringRenderable(getString(renderRequest, calculate));
    }

    private String getString(RenderRequest request, Object input) {
        return request.getEnvironment().getValueEnvironment().getStringConverter().convert(input);
    }
}
