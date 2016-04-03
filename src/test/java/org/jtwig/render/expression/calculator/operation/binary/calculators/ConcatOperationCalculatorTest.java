package org.jtwig.render.expression.calculator.operation.binary.calculators;

import org.jtwig.model.position.Position;
import org.jtwig.render.RenderRequest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ConcatOperationCalculatorTest {
    private ConcatOperationCalculator underTest = new ConcatOperationCalculator();

    @Test
    public void calculate() throws Exception {
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        Position position = mock(Position.class);
        Object left = new Object();
        Object right = new Object();

        when(request.getEnvironment().getValueEnvironment().getStringConverter().convert(left)).thenReturn("hello");
        when(request.getEnvironment().getValueEnvironment().getStringConverter().convert(right)).thenReturn(" world");

        Object result = underTest.calculate(request, position, left, right);

        assertEquals("hello world", result);
    }
}