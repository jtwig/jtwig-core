package org.jtwig.render.expression.calculator.operation.binary.calculators;

import org.jtwig.model.position.Position;
import org.jtwig.render.RenderRequest;
import org.jtwig.value.convert.Converter;
import org.junit.Test;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class BooleanOperationCalculatorTest {
    private final SimpleBinaryBooleanCalculator binaryBooleanCalculator = mock(SimpleBinaryBooleanCalculator.class);
    private BooleanOperationCalculator underTest = new BooleanOperationCalculator(binaryBooleanCalculator);

    @Test
    public void calculateUndefined() throws Exception {
        boolean expected = true;
        Object left = new Object();
        Object right = new Object();
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        Position position = mock(Position.class);

        when(request.getEnvironment().getValueEnvironment().getBooleanConverter().convert(left)).thenReturn(Converter.Result.<Boolean>undefined());
        when(request.getEnvironment().getValueEnvironment().getBooleanConverter().convert(right)).thenReturn(Converter.Result.<Boolean>undefined());
        when(binaryBooleanCalculator.calculate(true, true)).thenReturn(expected);

        Object result = underTest.calculate(request, position, left, right);

        assertSame(expected, result);
    }

    @Test
    public void calculateDefined() throws Exception {
        boolean expected = true;
        Object left = new Object();
        Object right = new Object();
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        Position position = mock(Position.class);

        when(request.getEnvironment().getValueEnvironment().getBooleanConverter().convert(left)).thenReturn(Converter.Result.defined(false));
        when(request.getEnvironment().getValueEnvironment().getBooleanConverter().convert(right)).thenReturn(Converter.Result.defined(false));
        when(binaryBooleanCalculator.calculate(false, false)).thenReturn(expected);

        Object result = underTest.calculate(request, position, left, right);

        assertSame(expected, result);
    }
}