package org.jtwig.render.expression.calculator;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.MapExpression;
import org.jtwig.render.RenderRequest;
import org.jtwig.render.expression.CalculateExpressionService;

import java.util.LinkedHashMap;
import java.util.Map;

public class MapExpressionCalculator implements ExpressionCalculator<MapExpression> {
    @Override
    public Object calculate(RenderRequest request, MapExpression expression) {
        CalculateExpressionService calculateExpressionService = request.getEnvironment().getRenderEnvironment().getCalculateExpressionService();

        Map<String, Object> result = new LinkedHashMap<>();
        for (Map.Entry<String, Expression> entry : expression.getExpressions().entrySet()) {
            result.put(entry.getKey(), calculateExpressionService.calculate(request, entry.getValue()));
        }
        return result;
    }
}
