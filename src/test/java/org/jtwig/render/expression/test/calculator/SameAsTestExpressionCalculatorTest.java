package org.jtwig.render.expression.test.calculator;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.test.SameAsTestExpression;
import org.jtwig.model.position.Position;
import org.jtwig.render.RenderRequest;
import org.junit.Test;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class SameAsTestExpressionCalculatorTest {
    private SameAsTestExpressionCalculator underTest = new SameAsTestExpressionCalculator();

    @Test
    public void calculateNotSame() throws Exception {
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        Position position = mock(Position.class);
        Expression argument = mock(Expression.class);
        SameAsTestExpression sameAsTestExpression = mock(SameAsTestExpression.class);
        Expression expression = mock(Expression.class);
        Object argumentValue = new Object();
        Object expressionValue = new Object();

        when(sameAsTestExpression.getExpression()).thenReturn(expression);
        when(request.getEnvironment().getRenderEnvironment().getCalculateExpressionService().calculate(request, argument)).thenReturn(argumentValue);
        when(request.getEnvironment().getRenderEnvironment().getCalculateExpressionService().calculate(request, expression)).thenReturn(expressionValue);

        Object result = underTest.calculate(request, position, sameAsTestExpression, argument);

        assertSame(false, result);
    }

    @Test
    public void calculateSame() throws Exception {
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        Position position = mock(Position.class);
        Expression argument = mock(Expression.class);
        SameAsTestExpression sameAsTestExpression = mock(SameAsTestExpression.class);
        Expression expression = mock(Expression.class);
        Object argumentValue = new Object();

        when(sameAsTestExpression.getExpression()).thenReturn(expression);
        when(request.getEnvironment().getRenderEnvironment().getCalculateExpressionService().calculate(request, argument)).thenReturn(argumentValue);
        when(request.getEnvironment().getRenderEnvironment().getCalculateExpressionService().calculate(request, expression)).thenReturn(argumentValue);

        Object result = underTest.calculate(request, position, sameAsTestExpression, argument);

        assertSame(true, result);
    }
}