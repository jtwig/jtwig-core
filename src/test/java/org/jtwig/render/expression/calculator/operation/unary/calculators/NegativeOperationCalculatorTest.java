package org.jtwig.render.expression.calculator.operation.unary.calculators;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.position.Position;
import org.jtwig.render.RenderRequest;
import org.jtwig.value.convert.Converter;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class NegativeOperationCalculatorTest {
    private NegativeOperationCalculator underTest = new NegativeOperationCalculator();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void calculate() throws Exception {
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        Position position = mock(Position.class);
        Expression operand = mock(Expression.class);
        Object operandValue = new Object();

        when(request.getEnvironment().getRenderEnvironment().getCalculateExpressionService().calculate(request, operand)).thenReturn(operandValue);
        when(request.getEnvironment().getValueEnvironment().getNumberConverter().convert(operandValue)).thenReturn(Converter.Result.defined(BigDecimal.ONE));

        Object result = underTest.calculate(request, position, operand);

        assertEquals(new BigDecimal("-1"), result);
    }

    @Test
    public void calculateNotANumber() throws Exception {
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        Position position = mock(Position.class);
        Expression operand = mock(Expression.class);
        Object operandValue = new Object();

        when(request.getEnvironment().getRenderEnvironment().getCalculateExpressionService().calculate(request, operand)).thenReturn(operandValue);
        when(request.getEnvironment().getValueEnvironment().getNumberConverter().convert(operandValue)).thenReturn(Converter.Result.<BigDecimal>undefined());

        expectedException.expectMessage(containsString(String.format("Unable to convert '%s' to a number", operandValue)));

        underTest.calculate(request, position, operand);
    }
}