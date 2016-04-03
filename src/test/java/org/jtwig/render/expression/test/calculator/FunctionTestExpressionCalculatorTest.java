package org.jtwig.render.expression.test.calculator;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.InjectableExpression;
import org.jtwig.model.expression.test.FunctionTestExpression;
import org.jtwig.model.position.Position;
import org.jtwig.render.RenderRequest;
import org.junit.Test;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class FunctionTestExpressionCalculatorTest {
    private FunctionTestExpressionCalculator underTest = new FunctionTestExpressionCalculator();

    @Test
    public void calculate() throws Exception {
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        FunctionTestExpression functionTestExpression = mock(FunctionTestExpression.class);
        Expression expression = mock(Expression.class);
        InjectableExpression injectableExpression = mock(InjectableExpression.class);
        Expression exp = mock(Expression.class);
        Object value = new Object();

        when(functionTestExpression.getInjectableExpression()).thenReturn(injectableExpression);
        when(injectableExpression.inject(expression)).thenReturn(exp);
        when(request.getEnvironment().getRenderEnvironment().getCalculateExpressionService().calculate(request, exp)).thenReturn(value);

        Object result = underTest.calculate(request, mock(Position.class), functionTestExpression, expression);

        assertSame(value, result);
    }
}