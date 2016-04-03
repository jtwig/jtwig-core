package org.jtwig.render.expression.calculator.operation.binary.calculators;

import org.jtwig.render.RenderRequest;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.MathContext;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class DivideOperationCalculatorTest {
    private DivideOperationCalculator underTest = new DivideOperationCalculator();

    @Test
    public void calculate() throws Exception {
        BigDecimal expected = BigDecimal.ONE;
        MathContext mathContext = MathContext.DECIMAL64;
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        BigDecimal left = mock(BigDecimal.class);
        BigDecimal right = mock(BigDecimal.class);

        when(request.getEnvironment().getValueEnvironment().getMathContext()).thenReturn(mathContext);
        when(left.divide(right, mathContext)).thenReturn(expected);

        BigDecimal result = underTest.calculate(request, left, right);

        assertEquals(expected, result);
    }
}