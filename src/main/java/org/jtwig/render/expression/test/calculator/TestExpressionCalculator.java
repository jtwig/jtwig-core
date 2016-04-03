package org.jtwig.render.expression.test.calculator;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.test.TestExpression;
import org.jtwig.model.position.Position;
import org.jtwig.render.RenderRequest;

public interface TestExpressionCalculator<T extends TestExpression> {
    Object calculate (RenderRequest request, Position position, T test, Expression argument);
}
