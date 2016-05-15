package org.jtwig.render.expression.test.calculator;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.VariableExpression;
import org.jtwig.model.expression.test.DefinedTestExpression;
import org.jtwig.model.position.Position;
import org.jtwig.render.RenderRequest;
import org.jtwig.value.Undefined;
import org.jtwig.value.context.ValueContext;

public class DefinedTestExpressionCalculator implements TestExpressionCalculator<DefinedTestExpression> {
    @Override
    public Object calculate(RenderRequest request, Position position, DefinedTestExpression test, Expression argument) {
        if (argument instanceof VariableExpression) {
            ValueContext current = request.getRenderContext().getValueContext().getCurrent();
            Object result = current.resolve(((VariableExpression) argument).getIdentifier());
            if (result == null || result == Undefined.UNDEFINED) {
                return false;
            } {
                return true;
            }
        } else {
            return true;
        }
    }
}
