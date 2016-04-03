package org.jtwig.render.expression.calculator;

import org.jtwig.model.expression.BinaryOperationExpression;
import org.jtwig.render.RenderRequest;
import org.junit.Test;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class BinaryOperationExpressionCalculatorTest {
    private final BinaryOperationExpressionCalculator underTest = new BinaryOperationExpressionCalculator();

    @Test
    public void calculate() throws Exception {
        Object value = new Object();
        BinaryOperationExpression binaryOperationExpression = mock(BinaryOperationExpression.class);
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);

        when(request.getEnvironment().getRenderEnvironment().getBinaryOperationService().calculate(request, binaryOperationExpression))
                .thenReturn(value);

        Object result = underTest.calculate(request, binaryOperationExpression);

        assertSame(value, result);
    }
}