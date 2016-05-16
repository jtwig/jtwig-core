package org.jtwig.render.expression.test.calculator;

import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.VariableExpression;
import org.jtwig.model.expression.test.IsFunctionTestExpression;
import org.jtwig.model.position.Position;
import org.jtwig.render.RenderRequest;

import java.util.Collections;

public class IsFunctionTestExpressionCalculator implements TestExpressionCalculator<IsFunctionTestExpression> {
    @Override
    public Object calculate(RenderRequest request, Position position, IsFunctionTestExpression test, Expression argument) {
        if (argument instanceof VariableExpression) {
            Optional<Supplier> function = request.getEnvironment().getFunctionResolver().resolve(request, position, ((VariableExpression) argument).getIdentifier(), Collections.emptyList());
            return function.isPresent();
        } else {
            return false;
        }
    }
}
