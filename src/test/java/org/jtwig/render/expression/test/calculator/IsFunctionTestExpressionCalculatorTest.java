package org.jtwig.render.expression.test.calculator;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.test.IsFunctionTestExpression;
import org.jtwig.model.position.Position;
import org.jtwig.render.RenderRequest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class IsFunctionTestExpressionCalculatorTest {
    private IsFunctionTestExpressionCalculator underTest = new IsFunctionTestExpressionCalculator();

    @Test
    public void calculate() throws Exception {
        IsFunctionTestExpression testExpression = mock(IsFunctionTestExpression.class);
        Position position = mock(Position.class);
        RenderRequest request = mock(RenderRequest.class);
        Expression expression = mock(Expression.class);

        Object result = underTest.calculate(request, position, testExpression, expression);

        assertEquals(false, result);
    }
}