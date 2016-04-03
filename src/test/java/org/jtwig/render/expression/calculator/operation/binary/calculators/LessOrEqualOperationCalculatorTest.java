package org.jtwig.render.expression.calculator.operation.binary.calculators;

import org.jtwig.model.position.Position;
import org.jtwig.render.RenderRequest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class LessOrEqualOperationCalculatorTest {
    private LessOrEqualOperationCalculator underTest = new LessOrEqualOperationCalculator();


    @Test
    public void calculateGreater() throws Exception {
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        Object left = new Object();
        Object right = new Object();

        when(request.getEnvironment().getValueEnvironment().getValueComparator().compare(request, left, right)).thenReturn(1);

        Object result = underTest.calculate(request, mock(Position.class), left, right);

        assertEquals(false, result);
    }

    @Test
    public void calculateLower() throws Exception {
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        Object left = new Object();
        Object right = new Object();

        when(request.getEnvironment().getValueEnvironment().getValueComparator().compare(request, left, right)).thenReturn(-1);

        Object result = underTest.calculate(request, mock(Position.class), left, right);

        assertEquals(true, result);
    }

    @Test
    public void calculateEqual() throws Exception {
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        Object left = new Object();
        Object right = new Object();

        when(request.getEnvironment().getValueEnvironment().getValueComparator().compare(request, left, right)).thenReturn(0);

        Object result = underTest.calculate(request, mock(Position.class), left, right);

        assertEquals(true, result);
    }
}