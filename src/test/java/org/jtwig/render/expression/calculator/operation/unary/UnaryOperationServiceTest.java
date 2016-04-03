package org.jtwig.render.expression.calculator.operation.unary;

import com.google.common.base.Optional;
import org.jtwig.exceptions.CalculationException;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.UnaryOperationExpression;
import org.jtwig.model.position.Position;
import org.jtwig.render.RenderRequest;
import org.jtwig.render.expression.calculator.operation.unary.calculators.UnaryOperationCalculator;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class UnaryOperationServiceTest {
    private final UnaryOperationCalculatorSelector unaryOperationCalculatorSelector = mock(UnaryOperationCalculatorSelector.class);
    private UnaryOperationService underTest = new UnaryOperationService(unaryOperationCalculatorSelector);

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void calculateNotPresent() throws Exception {
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        UnaryOperationExpression unaryOperationExpression = mock(UnaryOperationExpression.class);
        UnaryOperator unaryOperator = mock(UnaryOperator.class);

        when(unaryOperationExpression.getOperator()).thenReturn(unaryOperator);
        when(unaryOperationCalculatorSelector.calculatorFor(unaryOperator)).thenReturn(Optional.<UnaryOperationCalculator>absent());

        expectedException.expect(CalculationException.class);
        expectedException.expectMessage(containsString(String.format("No calculator for operator %s found", unaryOperator.getClass())));

        underTest.calculate(request, unaryOperationExpression);
    }

    @Test
    public void calculatePresent() throws Exception {
        Object expected = new Object();
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        UnaryOperationExpression unaryOperationExpression = mock(UnaryOperationExpression.class);
        UnaryOperator unaryOperator = mock(UnaryOperator.class);
        UnaryOperationCalculator unaryOperationCalculator = mock(UnaryOperationCalculator.class);
        Position position = mock(Position.class);
        Expression operand = mock(Expression.class);

        when(unaryOperationExpression.getOperator()).thenReturn(unaryOperator);
        when(unaryOperationExpression.getPosition()).thenReturn(position);
        when(unaryOperationExpression.getOperand()).thenReturn(operand);
        when(unaryOperationCalculatorSelector.calculatorFor(unaryOperator)).thenReturn(Optional.of(unaryOperationCalculator));
        when(unaryOperationCalculator.calculate(request, position, operand)).thenReturn(expected);

        Object result = underTest.calculate(request, unaryOperationExpression);

        assertSame(expected, result);
    }
}