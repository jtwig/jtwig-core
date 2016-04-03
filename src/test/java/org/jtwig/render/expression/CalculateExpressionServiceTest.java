package org.jtwig.render.expression;

import org.jtwig.model.expression.Expression;
import org.jtwig.render.RenderRequest;
import org.jtwig.render.expression.calculator.ExpressionCalculator;
import org.junit.Test;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CalculateExpressionServiceTest {
    private final ExpressionCalculatorSelector calculatorSelector = mock(ExpressionCalculatorSelector.class);
    private CalculateExpressionService underTest = new CalculateExpressionService(calculatorSelector);

    @Test
    public void calculate() throws Exception {
        Object value = new Object();
        RenderRequest request = mock(RenderRequest.class);
        Expression expression = mock(Expression.class);
        ExpressionCalculator calculator = mock(ExpressionCalculator.class);

        when(calculatorSelector.calculatorFor(expression)).thenReturn(calculator);
        when(calculator.calculate(request, expression)).thenReturn(value);

        Object result = underTest.calculate(request, expression);

        assertSame(value, result);
    }
}