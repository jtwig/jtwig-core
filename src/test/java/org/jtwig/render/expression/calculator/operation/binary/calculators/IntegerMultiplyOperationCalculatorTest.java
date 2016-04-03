package org.jtwig.render.expression.calculator.operation.binary.calculators;

import org.jtwig.render.RenderRequest;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class IntegerMultiplyOperationCalculatorTest {
    private IntegerMultiplyOperationCalculator underTest = new IntegerMultiplyOperationCalculator();


    @Test
    public void calculate() throws Exception {
        RoundingMode roundingMode = RoundingMode.CEILING;
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        BigDecimal left = mock(BigDecimal.class);
        BigDecimal right = mock(BigDecimal.class);
        BigDecimal leftRounded = mock(BigDecimal.class);
        BigDecimal rightRounded = mock(BigDecimal.class);
        BigDecimal expected = mock(BigDecimal.class);

        when(request.getEnvironment().getValueEnvironment().getRoundingMode()).thenReturn(roundingMode);
        when(left.setScale(0, roundingMode)).thenReturn(leftRounded);
        when(right.setScale(0, roundingMode)).thenReturn(rightRounded);
        when(leftRounded.multiply(rightRounded)).thenReturn(expected);

        BigDecimal result = underTest.calculate(request, left, right);

        assertEquals(expected, result);
    }
}