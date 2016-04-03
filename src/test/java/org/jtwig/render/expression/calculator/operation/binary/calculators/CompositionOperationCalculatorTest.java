package org.jtwig.render.expression.calculator.operation.binary.calculators;

import org.jtwig.exceptions.CalculationException;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.InjectableExpression;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class CompositionOperationCalculatorTest {
    private CompositionOperationCalculator underTest = new CompositionOperationCalculator();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void calculateInvalidRightExpression() throws Exception {
        BinaryOperationCalculator.Request request = mock(BinaryOperationCalculator.Request.class, RETURNS_DEEP_STUBS);
        Expression expression = mock(Expression.class);

        when(request.getRightOperand()).thenReturn(expression);

        expectedException.expect(CalculationException.class);
        expectedException.expectMessage(containsString("Invalid composition expression"));

        underTest.calculate(request);
    }

    @Test
    public void calculate() throws Exception {
        Object expected = new Object();
        BinaryOperationCalculator.Request request = mock(BinaryOperationCalculator.Request.class, RETURNS_DEEP_STUBS);
        InjectableExpression rightOperand = mock(InjectableExpression.class);
        Expression leftOperand = mock(Expression.class);
        Expression expression = mock(Expression.class);

        when(request.getRightOperand()).thenReturn(rightOperand);
        when(request.getLeftOperand()).thenReturn(leftOperand);
        when(rightOperand.inject(leftOperand)).thenReturn(expression);
        when(request.getEnvironment().getRenderEnvironment().getCalculateExpressionService().calculate(request, expression)).thenReturn(expected);

        Object result = underTest.calculate(request);

        assertSame(expected, result);
    }
}