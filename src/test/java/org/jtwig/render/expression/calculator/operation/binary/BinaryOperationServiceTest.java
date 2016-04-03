package org.jtwig.render.expression.calculator.operation.binary;

import com.google.common.base.Optional;
import org.jtwig.model.expression.BinaryOperationExpression;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.position.Position;
import org.jtwig.render.RenderRequest;
import org.jtwig.render.expression.calculator.operation.binary.calculators.BinaryOperationCalculator;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.Matchers.containsString;
import static org.jtwig.support.MatcherUtils.theSame;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BinaryOperationServiceTest {
    private final BinaryOperationCalculatorSelector binaryOperationCalculatorSelector = mock(BinaryOperationCalculatorSelector.class);
    private BinaryOperationService underTest = new BinaryOperationService(binaryOperationCalculatorSelector);

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void calculateWhenNoCalculator() throws Exception {
        RenderRequest request = mock(RenderRequest.class);
        BinaryOperationExpression binaryOperationExpression = mock(BinaryOperationExpression.class);
        BinaryOperator binaryOperator = mock(BinaryOperator.class);

        when(binaryOperationExpression.getBinaryOperator()).thenReturn(binaryOperator);
        when(binaryOperationCalculatorSelector.calculatorFor(binaryOperator)).thenReturn(Optional.<BinaryOperationCalculator>absent());

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(containsString(String.format("No calculator implementation for operation '%s'", binaryOperator.getClass())));

        underTest.calculate(request, binaryOperationExpression);
    }

    @Test
    public void calculateWhenCalculator() throws Exception {
        Object expected = new Object();
        RenderRequest request = mock(RenderRequest.class);
        BinaryOperationExpression binaryOperationExpression = mock(BinaryOperationExpression.class);
        BinaryOperationCalculator binaryOperationCalculator = mock(BinaryOperationCalculator.class);
        BinaryOperator binaryOperator = mock(BinaryOperator.class);
        Expression leftOperand = mock(Expression.class);
        Expression rightOperand = mock(Expression.class);
        Position position = mock(Position.class);

        when(binaryOperationExpression.getBinaryOperator()).thenReturn(binaryOperator);
        when(binaryOperationExpression.getPosition()).thenReturn(position);
        when(binaryOperationExpression.getLeftOperand()).thenReturn(leftOperand);
        when(binaryOperationExpression.getRightOperand()).thenReturn(rightOperand);
        when(binaryOperationCalculatorSelector.calculatorFor(binaryOperator)).thenReturn(Optional.of(binaryOperationCalculator));
        when(binaryOperationCalculator.calculate(argThat(theSame(new BinaryOperationCalculator.Request(request, position, leftOperand, rightOperand))))).thenReturn(expected);

        Object result = underTest.calculate(request, binaryOperationExpression);

        assertSame(expected, result);
    }
}