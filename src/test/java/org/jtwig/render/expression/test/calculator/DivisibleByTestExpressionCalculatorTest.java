package org.jtwig.render.expression.test.calculator;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.test.DivisibleByTestExpression;
import org.jtwig.model.position.Position;
import org.jtwig.render.RenderRequest;
import org.jtwig.value.convert.Converter;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;
import java.math.MathContext;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class DivisibleByTestExpressionCalculatorTest {
    private DivisibleByTestExpressionCalculator underTest = new DivisibleByTestExpressionCalculator();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void calculateArgumentNotANumber() throws Exception {
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        DivisibleByTestExpression test = mock(DivisibleByTestExpression.class);
        Expression argument = mock(Expression.class);
        Object argumentValue = new Object();

        when(request.getEnvironment().getRenderEnvironment().getCalculateExpressionService().calculate(request, argument)).thenReturn(argumentValue);
        when(request.getEnvironment().getValueEnvironment().getNumberConverter().convert(argumentValue)).thenReturn(Converter.Result.<BigDecimal>undefined());

        expectedException.expectMessage(containsString(String.format("Cannot convert '%s' to number", argumentValue)));

        underTest.calculate(request, mock(Position.class), test, argument);
    }

    @Test
    public void calculateTestValueNotANumber() throws Exception {
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        DivisibleByTestExpression test = mock(DivisibleByTestExpression.class);
        Expression argument = mock(Expression.class);
        Object argumentValue = new Object();
        Expression expression = mock(Expression.class);
        Object expressionValue = new Object();

        when(test.getExpression()).thenReturn(expression);
        when(request.getEnvironment().getRenderEnvironment().getCalculateExpressionService().calculate(request, argument)).thenReturn(argumentValue);
        when(request.getEnvironment().getRenderEnvironment().getCalculateExpressionService().calculate(request, expression)).thenReturn(expressionValue);
        when(request.getEnvironment().getValueEnvironment().getNumberConverter().convert(argumentValue)).thenReturn(Converter.Result.defined(mock(BigDecimal.class)));
        when(request.getEnvironment().getValueEnvironment().getNumberConverter().convert(expressionValue)).thenReturn(Converter.Result.<BigDecimal>undefined());


        expectedException.expectMessage(containsString(String.format("Cannot convert '%s' to number", expressionValue)));

        underTest.calculate(request, mock(Position.class), test, argument);
    }

    @Test
    public void calculateFalse() throws Exception {
        MathContext mathContext = MathContext.DECIMAL128;
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        DivisibleByTestExpression test = mock(DivisibleByTestExpression.class);
        Expression argument = mock(Expression.class);
        Object argumentValue = new Object();
        Expression expression = mock(Expression.class);
        Object expressionValue = new Object();
        BigDecimal argumentNumber = mock(BigDecimal.class);
        BigDecimal expressionNumber = mock(BigDecimal.class);
        BigDecimal expected = BigDecimal.TEN;

        when(test.getExpression()).thenReturn(expression);
        when(request.getEnvironment().getRenderEnvironment().getCalculateExpressionService().calculate(request, argument)).thenReturn(argumentValue);
        when(request.getEnvironment().getRenderEnvironment().getCalculateExpressionService().calculate(request, expression)).thenReturn(expressionValue);
        when(request.getEnvironment().getValueEnvironment().getNumberConverter().convert(argumentValue)).thenReturn(Converter.Result.defined(argumentNumber));
        when(request.getEnvironment().getValueEnvironment().getNumberConverter().convert(expressionValue)).thenReturn(Converter.Result.defined(expressionNumber));
        when(request.getEnvironment().getValueEnvironment().getMathContext()).thenReturn(mathContext);
        when(argumentNumber.remainder(expressionNumber, mathContext)).thenReturn(expected);

        Object result = underTest.calculate(request, mock(Position.class), test, argument);

        assertSame(false, result);
    }

    @Test
    public void calculateTrue() throws Exception {
        MathContext mathContext = MathContext.DECIMAL128;
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        DivisibleByTestExpression test = mock(DivisibleByTestExpression.class);
        Expression argument = mock(Expression.class);
        Object argumentValue = new Object();
        Expression expression = mock(Expression.class);
        Object expressionValue = new Object();
        BigDecimal argumentNumber = mock(BigDecimal.class);
        BigDecimal expressionNumber = mock(BigDecimal.class);
        BigDecimal expected = BigDecimal.ZERO;

        when(test.getExpression()).thenReturn(expression);
        when(request.getEnvironment().getRenderEnvironment().getCalculateExpressionService().calculate(request, argument)).thenReturn(argumentValue);
        when(request.getEnvironment().getRenderEnvironment().getCalculateExpressionService().calculate(request, expression)).thenReturn(expressionValue);
        when(request.getEnvironment().getValueEnvironment().getNumberConverter().convert(argumentValue)).thenReturn(Converter.Result.defined(argumentNumber));
        when(request.getEnvironment().getValueEnvironment().getNumberConverter().convert(expressionValue)).thenReturn(Converter.Result.defined(expressionNumber));
        when(request.getEnvironment().getValueEnvironment().getMathContext()).thenReturn(mathContext);
        when(argumentNumber.remainder(expressionNumber, mathContext)).thenReturn(expected);

        Object result = underTest.calculate(request, mock(Position.class), test, argument);

        assertSame(true, result);
    }
}