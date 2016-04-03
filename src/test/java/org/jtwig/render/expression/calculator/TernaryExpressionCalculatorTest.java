package org.jtwig.render.expression.calculator;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.TernaryOperationExpression;
import org.jtwig.render.RenderRequest;
import org.jtwig.value.convert.Converter;
import org.junit.Test;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class TernaryExpressionCalculatorTest {
    private TernaryExpressionCalculator underTest = new TernaryExpressionCalculator();

    @Test
    public void calculateIfUndefined() throws Exception {
        TernaryOperationExpression ternaryOperationExpression = mock(TernaryOperationExpression.class);
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        Expression firstExpression = mock(Expression.class);
        Expression secondExpression = mock(Expression.class);
        Object condition = new Object();
        Object whenTrueValue = new Object();

        when(ternaryOperationExpression.getFirstExpression()).thenReturn(firstExpression);
        when(ternaryOperationExpression.getSecondExpression()).thenReturn(secondExpression);
        when(request.getEnvironment().getRenderEnvironment().getCalculateExpressionService().calculate(request, firstExpression)).thenReturn(condition);
        when(request.getEnvironment().getValueEnvironment().getBooleanConverter().convert(condition)).thenReturn(Converter.Result.<Boolean>undefined());
        when(request.getEnvironment().getRenderEnvironment().getCalculateExpressionService().calculate(request, secondExpression)).thenReturn(whenTrueValue);

        Object result = underTest.calculate(request, ternaryOperationExpression);

        assertSame(whenTrueValue, result);
    }

    @Test
    public void calculateIfTrue() throws Exception {
        TernaryOperationExpression ternaryOperationExpression = mock(TernaryOperationExpression.class);
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        Expression firstExpression = mock(Expression.class);
        Expression secondExpression = mock(Expression.class);
        Object condition = new Object();
        Object whenTrueValue = new Object();

        when(ternaryOperationExpression.getFirstExpression()).thenReturn(firstExpression);
        when(ternaryOperationExpression.getSecondExpression()).thenReturn(secondExpression);
        when(request.getEnvironment().getRenderEnvironment().getCalculateExpressionService().calculate(request, firstExpression)).thenReturn(condition);
        when(request.getEnvironment().getValueEnvironment().getBooleanConverter().convert(condition)).thenReturn(Converter.Result.defined(true));
        when(request.getEnvironment().getRenderEnvironment().getCalculateExpressionService().calculate(request, secondExpression)).thenReturn(whenTrueValue);

        Object result = underTest.calculate(request, ternaryOperationExpression);

        assertSame(whenTrueValue, result);
    }

    @Test
    public void calculateIfFalse() throws Exception {
        TernaryOperationExpression ternaryOperationExpression = mock(TernaryOperationExpression.class);
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        Expression firstExpression = mock(Expression.class);
        Expression secondExpression = mock(Expression.class);
        Expression thirdExpression = mock(Expression.class);
        Object condition = new Object();
        Object whenTrueValue = new Object();
        Object whenFalseValue = new Object();

        when(ternaryOperationExpression.getFirstExpression()).thenReturn(firstExpression);
        when(ternaryOperationExpression.getSecondExpression()).thenReturn(secondExpression);
        when(ternaryOperationExpression.getThirdExpression()).thenReturn(thirdExpression);
        when(request.getEnvironment().getRenderEnvironment().getCalculateExpressionService().calculate(request, firstExpression)).thenReturn(condition);
        when(request.getEnvironment().getRenderEnvironment().getCalculateExpressionService().calculate(request, secondExpression)).thenReturn(whenTrueValue);
        when(request.getEnvironment().getRenderEnvironment().getCalculateExpressionService().calculate(request, thirdExpression)).thenReturn(whenFalseValue);
        when(request.getEnvironment().getValueEnvironment().getBooleanConverter().convert(condition)).thenReturn(Converter.Result.defined(false));

        Object result = underTest.calculate(request, ternaryOperationExpression);

        assertSame(whenFalseValue, result);
    }
}