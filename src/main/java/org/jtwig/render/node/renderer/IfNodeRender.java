package org.jtwig.render.node.renderer;

import org.jtwig.model.tree.IfNode;
import org.jtwig.render.RenderRequest;
import org.jtwig.render.expression.CalculateExpressionService;
import org.jtwig.render.node.RenderNodeService;
import org.jtwig.renderable.Renderable;
import org.jtwig.renderable.impl.EmptyRenderable;
import org.jtwig.value.convert.Converter;

import java.util.Collection;

public class IfNodeRender implements NodeRender<IfNode> {
    @Override
    public Renderable render(RenderRequest request, IfNode node) {
        CalculateExpressionService calculateExpressionService = request.getEnvironment().getRenderEnvironment().getCalculateExpressionService();
        RenderNodeService renderNodeService = request.getEnvironment().getRenderEnvironment().getRenderNodeService();
        Converter<Boolean> booleanConverter = request.getEnvironment().getValueEnvironment().getBooleanConverter();
        Collection<IfNode.IfConditionNode> conditionNodes = node.getConditionNodes();

        for (IfNode.IfConditionNode conditionNode : conditionNodes) {
            Object result = calculateExpressionService.calculate(request, conditionNode.getCondition());
            Converter.Result<Boolean> booleanValue = booleanConverter.convert(result);

            if (booleanValue.or(true)) {
                return renderNodeService.render(request, conditionNode.getContent());
            }
        }

        return EmptyRenderable.instance();
    }
}
