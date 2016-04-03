package org.jtwig.render.expression.calculator.operation.binary.calculators;

import org.jtwig.render.RenderRequest;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.MathContext;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class ModOperationCalculatorTest {
    private ModOperationCalculator underTest = new ModOperationCalculator();

    @Test
    public void calculate() throws Exception {
        MathContext mathContext = MathContext.DECIMAL128;
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        BigDecimal left = mock(BigDecimal.class);
        BigDecimal right = mock(BigDecimal.class);
        BigDecimal expected = mock(BigDecimal.class);

        when(request.getEnvironment().getValueEnvironment().getMathContext()).thenReturn(mathContext);
        when(left.remainder(right, mathContext)).thenReturn(expected);

        BigDecimal result = underTest.calculate(request, left, right);

        assertSame(expected, result);
    }
}