package org.jtwig.render.expression.test.calculator;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.test.NotTestExpression;
import org.jtwig.model.position.Position;
import org.jtwig.render.RenderRequest;
import org.jtwig.value.convert.Converter;
import org.junit.Test;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class NotTestExpressionCalculatorTest {
    private NotTestExpressionCalculator underTest = new NotTestExpressionCalculator();

    @Test
    public void calculateIfNotBoolean() throws Exception {
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        Position position = mock(Position.class);
        Expression argument = mock(Expression.class);
        NotTestExpression notTestExpression = mock(NotTestExpression.class);
        Object testResult = new Object();

        when(request.getEnvironment().getRenderEnvironment().getCalculateTestExpressionService().calculate(request, position, notTestExpression, argument)).thenReturn(testResult);
        when(request.getEnvironment().getValueEnvironment().getBooleanConverter().convert(testResult)).thenReturn(Converter.Result.<Boolean>undefined());

        Object result = underTest.calculate(request, position, notTestExpression, argument);

        assertSame(true, result);
    }

    @Test
    public void calculateIfBoolean() throws Exception {
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        Position position = mock(Position.class);
        Expression argument = mock(Expression.class);
        NotTestExpression notTestExpression = mock(NotTestExpression.class);
        Object testResult = new Object();
        boolean expected = false;

        when(request.getEnvironment().getRenderEnvironment().getCalculateTestExpressionService().calculate(request, position, notTestExpression, argument)).thenReturn(testResult);
        when(request.getEnvironment().getValueEnvironment().getBooleanConverter().convert(testResult)).thenReturn(Converter.Result.defined(expected));

        Object result = underTest.calculate(request, position, notTestExpression, argument);

        assertSame(!expected, result);
    }
}