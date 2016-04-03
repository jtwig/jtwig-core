package org.jtwig.render.expression.calculator;

import org.jtwig.model.expression.ConstantExpression;
import org.jtwig.render.RenderRequest;
import org.junit.Test;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ConstantExpressionCalculatorTest {
    private ConstantExpressionCalculator underTest = new ConstantExpressionCalculator();

    @Test
    public void calculate() throws Exception {
        Object expected = new Object();
        ConstantExpression expression = mock(ConstantExpression.class);

        when(expression.getConstantValue()).thenReturn(expected);

        Object result = underTest.calculate(mock(RenderRequest.class), expression);

        assertSame(expected, result);
    }
}