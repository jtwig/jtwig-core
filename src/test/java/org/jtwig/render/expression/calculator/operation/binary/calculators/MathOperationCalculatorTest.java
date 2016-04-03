package org.jtwig.render.expression.calculator.operation.binary.calculators;

import org.jtwig.exceptions.CalculationException;
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

public class MathOperationCalculatorTest {
    private final SimpleBinaryMathCalculator simpleBinaryMathCalculator = mock(SimpleBinaryMathCalculator.class);
    private MathOperationCalculator underTest = new MathOperationCalculator(simpleBinaryMathCalculator);

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void calculateLeftNotNumber() throws Exception {
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        Position position = mock(Position.class);
        Object left = new Object();
        Object right = new Object();

        when(request.getEnvironment().getValueEnvironment().getNumberConverter().convert(left)).thenReturn(Converter.Result.<BigDecimal>undefined());

        expectedException.expect(CalculationException.class);
        expectedException.expectMessage(containsString("Left argument expected to be a number"));

        underTest.calculate(request, position, left, right);
    }

    @Test
    public void calculateRightNotNumber() throws Exception {
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        Position position = mock(Position.class);
        Object left = new Object();
        Object right = new Object();

        when(request.getEnvironment().getValueEnvironment().getNumberConverter().convert(left)).thenReturn(Converter.Result.defined(BigDecimal.ONE));
        when(request.getEnvironment().getValueEnvironment().getNumberConverter().convert(right)).thenReturn(Converter.Result.<BigDecimal>undefined());

        expectedException.expect(CalculationException.class);
        expectedException.expectMessage(containsString("Right argument expected to be a number"));

        underTest.calculate(request, position, left, right);
    }

    @Test
    public void calculate() throws Exception {
        BigDecimal expected = BigDecimal.TEN;
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        Position position = mock(Position.class);
        Object left = new Object();
        Object right = new Object();
        BigDecimal leftNumber = BigDecimal.ONE;
        BigDecimal rightNumber = BigDecimal.ZERO;

        when(request.getEnvironment().getValueEnvironment().getNumberConverter().convert(left)).thenReturn(Converter.Result.defined(leftNumber));
        when(request.getEnvironment().getValueEnvironment().getNumberConverter().convert(right)).thenReturn(Converter.Result.defined(rightNumber));
        when(simpleBinaryMathCalculator.calculate(request, leftNumber, rightNumber)).thenReturn(expected);

        Object result = underTest.calculate(request, position, left, right);

        assertEquals(expected, result);
    }
}