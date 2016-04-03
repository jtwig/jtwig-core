package org.jtwig.render.expression.calculator.operation.binary.calculators;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.position.Position;
import org.junit.Test;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class SimpleOperationCalculatorTest {
    private final SimpleBinaryOperationCalculator simpleBinaryOperationCalculator = mock(SimpleBinaryOperationCalculator.class);
    private SimpleOperationCalculator underTest = new SimpleOperationCalculator(simpleBinaryOperationCalculator);

    @Test
    public void calculate() throws Exception {
        BinaryOperationCalculator.Request request = mock(BinaryOperationCalculator.Request.class, RETURNS_DEEP_STUBS);
        Expression leftExpression = mock(Expression.class);
        Expression rightExpression = mock(Expression.class);
        Position position = mock(Position.class);
        Object leftValue = new Object();
        Object rightValue = new Object();
        Object expected = new Object();

        when(request.getLeftOperand()).thenReturn(leftExpression);
        when(request.getRightOperand()).thenReturn(rightExpression);
        when(request.getPosition()).thenReturn(position);
        when(request.getEnvironment().getRenderEnvironment().getCalculateExpressionService().calculate(request, leftExpression)).thenReturn(leftValue);
        when(request.getEnvironment().getRenderEnvironment().getCalculateExpressionService().calculate(request, rightExpression)).thenReturn(rightValue);
        when(simpleBinaryOperationCalculator.calculate(request, position, leftValue, rightValue)).thenReturn(expected);

        Object result = underTest.calculate(request);

        assertSame(expected, result);
    }
}