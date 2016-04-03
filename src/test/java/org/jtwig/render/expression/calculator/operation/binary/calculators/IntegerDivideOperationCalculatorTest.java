package org.jtwig.render.expression.calculator.operation.binary.calculators;

import org.jtwig.render.RenderRequest;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class IntegerDivideOperationCalculatorTest {
    private IntegerDivideOperationCalculator underTest = new IntegerDivideOperationCalculator();

    @Test
    public void calculate() throws Exception {
        MathContext mathContext = MathContext.DECIMAL128;
        RoundingMode roundingMode = RoundingMode.CEILING;
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        BigDecimal left = mock(BigDecimal.class);
        BigDecimal right = mock(BigDecimal.class);
        BigDecimal leftRounded = mock(BigDecimal.class);
        BigDecimal rightRounded = mock(BigDecimal.class);
        BigDecimal divisionResult = mock(BigDecimal.class);
        BigDecimal expected = mock(BigDecimal.class);

        when(request.getEnvironment().getValueEnvironment().getMathContext()).thenReturn(mathContext);
        when(request.getEnvironment().getValueEnvironment().getRoundingMode()).thenReturn(roundingMode);
        when(left.setScale(0, roundingMode)).thenReturn(leftRounded);
        when(right.setScale(0, roundingMode)).thenReturn(rightRounded);
        when(leftRounded.divide(rightRounded, mathContext)).thenReturn(divisionResult);
        when(divisionResult.setScale(0, roundingMode)).thenReturn(expected);

        BigDecimal result = underTest.calculate(request, left, right);

        assertEquals(expected, result);
    }
}