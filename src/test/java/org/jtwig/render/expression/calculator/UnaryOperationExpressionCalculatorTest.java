package org.jtwig.render.expression.calculator;

import org.jtwig.model.expression.UnaryOperationExpression;
import org.jtwig.render.RenderRequest;
import org.junit.Test;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class UnaryOperationExpressionCalculatorTest {
    private UnaryOperationExpressionCalculator underTest = new UnaryOperationExpressionCalculator();

    @Test
    public void calculate() throws Exception {
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        UnaryOperationExpression unaryOperationExpression = mock(UnaryOperationExpression.class);
        Object value = new Object();

        when(request.getEnvironment().getRenderEnvironment().getUnaryOperationService().calculate(request, unaryOperationExpression)).thenReturn(value);

        Object result = underTest.calculate(request, unaryOperationExpression);

        assertSame(value, result);
    }
}