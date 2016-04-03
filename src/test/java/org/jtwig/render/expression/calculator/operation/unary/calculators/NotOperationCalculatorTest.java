package org.jtwig.render.expression.calculator.operation.unary.calculators;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.position.Position;
import org.jtwig.render.RenderRequest;
import org.jtwig.value.convert.Converter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class NotOperationCalculatorTest {
    private NotOperationCalculator underTest = new NotOperationCalculator();

    @Test
    public void calculateWhenNotConverted() throws Exception {
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        Expression operand = mock(Expression.class);
        Object operandValue = new Object();

        when(request.getEnvironment().getRenderEnvironment().getCalculateExpressionService().calculate(request, operand)).thenReturn(operandValue);
        when(request.getEnvironment().getValueEnvironment().getBooleanConverter().convert(operandValue)).thenReturn(Converter.Result.<Boolean>undefined());

        Object result = underTest.calculate(request, mock(Position.class), operand);

        assertEquals(false, result);
    }

    @Test
    public void calculateWhenConverted() throws Exception {
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        Expression operand = mock(Expression.class);
        Object operandValue = new Object();

        when(request.getEnvironment().getRenderEnvironment().getCalculateExpressionService().calculate(request, operand)).thenReturn(operandValue);
        when(request.getEnvironment().getValueEnvironment().getBooleanConverter().convert(operandValue)).thenReturn(Converter.Result.defined(false));

        Object result = underTest.calculate(request, mock(Position.class), operand);

        assertEquals(true, result);
    }
}