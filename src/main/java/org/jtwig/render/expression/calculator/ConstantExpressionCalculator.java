package org.jtwig.render.expression.calculator;

import org.jtwig.model.expression.ConstantExpression;
import org.jtwig.render.RenderRequest;

public class ConstantExpressionCalculator implements ExpressionCalculator<ConstantExpression> {
    @Override
    public Object calculate(RenderRequest request, ConstantExpression expression) {
        return expression.getConstantValue();
    }
}
