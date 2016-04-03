package org.jtwig.render.expression.test.calculator;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.test.NullTestExpression;
import org.jtwig.model.position.Position;
import org.jtwig.render.RenderRequest;
import org.jtwig.value.Undefined;
import org.junit.Test;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class NullTestExpressionCalculatorTest {
    private NullTestExpressionCalculator underTest = new NullTestExpressionCalculator();

    @Test
    public void calculateIfStrictModeNull() throws Exception {
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        Position position = mock(Position.class);
        Expression argument = mock(Expression.class);

        when(request.getEnvironment().getRenderEnvironment().getCalculateExpressionService().calculate(request, argument)).thenReturn(null);
        when(request.getEnvironment().getRenderEnvironment().getStrictMode()).thenReturn(true);

        Object result = underTest.calculate(request, position, mock(NullTestExpression.class), argument);

        assertSame(true, result);
    }

    @Test
    public void calculateIfStrictModeUndefined() throws Exception {
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        Position position = mock(Position.class);
        Expression argument = mock(Expression.class);

        when(request.getEnvironment().getRenderEnvironment().getCalculateExpressionService().calculate(request, argument)).thenReturn(Undefined.UNDEFINED);
        when(request.getEnvironment().getRenderEnvironment().getStrictMode()).thenReturn(true);

        Object result = underTest.calculate(request, position, mock(NullTestExpression.class), argument);

        assertSame(false, result);
    }

    @Test
    public void calculateIfNotStrictModeUndefined() throws Exception {
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        Position position = mock(Position.class);
        Expression argument = mock(Expression.class);

        when(request.getEnvironment().getRenderEnvironment().getCalculateExpressionService().calculate(request, argument)).thenReturn(Undefined.UNDEFINED);
        when(request.getEnvironment().getRenderEnvironment().getStrictMode()).thenReturn(false);

        Object result = underTest.calculate(request, position, mock(NullTestExpression.class), argument);

        assertSame(true, result);
    }

    @Test
    public void calculateIfNull() throws Exception {
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        Position position = mock(Position.class);
        Expression argument = mock(Expression.class);

        when(request.getEnvironment().getRenderEnvironment().getCalculateExpressionService().calculate(request, argument)).thenReturn(null);
        when(request.getEnvironment().getRenderEnvironment().getStrictMode()).thenReturn(false);

        Object result = underTest.calculate(request, position, mock(NullTestExpression.class), argument);

        assertSame(true, result);
    }
}