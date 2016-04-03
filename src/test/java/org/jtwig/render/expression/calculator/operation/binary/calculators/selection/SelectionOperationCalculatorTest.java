package org.jtwig.render.expression.calculator.operation.binary.calculators.selection;

import com.google.common.base.Optional;
import org.jtwig.exceptions.CalculationException;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.position.Position;
import org.jtwig.reflection.model.Value;
import org.jtwig.render.expression.calculator.operation.binary.calculators.BinaryOperationCalculator;
import org.jtwig.value.Undefined;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class SelectionOperationCalculatorTest {
    private final PropertyAndArgumentsCalculator propertyAndArgumentsCalculator = mock(PropertyAndArgumentsCalculator.class);
    private final ResolveSelectionPropertyCalculator resolveSelectionPropertyCalculator = mock(ResolveSelectionPropertyCalculator.class);
    private SelectionOperationCalculator underTest = new SelectionOperationCalculator(propertyAndArgumentsCalculator, resolveSelectionPropertyCalculator);

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void calculateIfNotDefinedNotStrictMode() throws Exception {
        String propertyName = "propertyName";
        BinaryOperationCalculator.Request request = mock(BinaryOperationCalculator.Request.class, RETURNS_DEEP_STUBS);
        PropertyAndArgumentsCalculator.Response response = mock(PropertyAndArgumentsCalculator.Response.class);
        Expression leftExpression = mock(Expression.class);
        Object leftValue = new Object();
        Position position = mock(Position.class);
        Expression rightExpression = mock(Expression.class);
        List<Object> arguments = mock(List.class);

        when(request.getLeftOperand()).thenReturn(leftExpression);
        when(request.getRightOperand()).thenReturn(rightExpression);
        when(request.getPosition()).thenReturn(position);
        when(request.getEnvironment().getRenderEnvironment().getCalculateExpressionService().calculate(request, leftExpression)).thenReturn(leftValue);
        when(response.getPropertyName()).thenReturn(propertyName);
        when(response.getArguments()).thenReturn(arguments);
        when(propertyAndArgumentsCalculator.calculate(request, position, rightExpression)).thenReturn(response);
        when(resolveSelectionPropertyCalculator.calculate(request, position, propertyName, arguments, leftValue)).thenReturn(Optional.<Value>absent());
        when(request.getEnvironment().getRenderEnvironment().getStrictMode()).thenReturn(false);

        Object result = underTest.calculate(request);

        assertSame(Undefined.UNDEFINED, result);
    }

    @Test
    public void calculateIfNotDefinedStrictMode() throws Exception {
        String propertyName = "propertyName";
        BinaryOperationCalculator.Request request = mock(BinaryOperationCalculator.Request.class, RETURNS_DEEP_STUBS);
        PropertyAndArgumentsCalculator.Response response = mock(PropertyAndArgumentsCalculator.Response.class);
        Expression leftExpression = mock(Expression.class);
        Object leftValue = new Object();
        Position position = mock(Position.class);
        Expression rightExpression = mock(Expression.class);
        List<Object> arguments = mock(List.class);

        when(request.getLeftOperand()).thenReturn(leftExpression);
        when(request.getRightOperand()).thenReturn(rightExpression);
        when(request.getPosition()).thenReturn(position);
        when(request.getEnvironment().getRenderEnvironment().getCalculateExpressionService().calculate(request, leftExpression)).thenReturn(leftValue);
        when(response.getPropertyName()).thenReturn(propertyName);
        when(response.getArguments()).thenReturn(arguments);
        when(propertyAndArgumentsCalculator.calculate(request, position, rightExpression)).thenReturn(response);
        when(resolveSelectionPropertyCalculator.calculate(request, position, propertyName, arguments, leftValue)).thenReturn(Optional.<Value>absent());
        when(request.getEnvironment().getRenderEnvironment().getStrictMode()).thenReturn(true);

        expectedException.expect(CalculationException.class);
        expectedException.expectMessage(containsString(String.format("Impossible to access an attribute '%s' on '%s'", propertyName, leftValue)));

        underTest.calculate(request);
    }

    @Test
    public void calculateIfDefined() throws Exception {
        String propertyName = "propertyName";
        BinaryOperationCalculator.Request request = mock(BinaryOperationCalculator.Request.class, RETURNS_DEEP_STUBS);
        PropertyAndArgumentsCalculator.Response response = mock(PropertyAndArgumentsCalculator.Response.class);
        Expression leftExpression = mock(Expression.class);
        Object leftValue = new Object();
        Position position = mock(Position.class);
        Expression rightExpression = mock(Expression.class);
        List<Object> arguments = mock(List.class);
        Value expectedWrapped = mock(Value.class);
        Object expected = new Object();

        when(request.getLeftOperand()).thenReturn(leftExpression);
        when(request.getRightOperand()).thenReturn(rightExpression);
        when(request.getPosition()).thenReturn(position);
        when(request.getEnvironment().getRenderEnvironment().getCalculateExpressionService().calculate(request, leftExpression)).thenReturn(leftValue);
        when(response.getPropertyName()).thenReturn(propertyName);
        when(response.getArguments()).thenReturn(arguments);
        when(propertyAndArgumentsCalculator.calculate(request, position, rightExpression)).thenReturn(response);
        when(resolveSelectionPropertyCalculator.calculate(request, position, propertyName, arguments, leftValue)).thenReturn(Optional.of(expectedWrapped));
        when(expectedWrapped.getValue()).thenReturn(expected);

        Object result = underTest.calculate(request);

        assertSame(expected, result);
    }
}