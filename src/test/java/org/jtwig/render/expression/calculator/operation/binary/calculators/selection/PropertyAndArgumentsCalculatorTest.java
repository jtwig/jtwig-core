package org.jtwig.render.expression.calculator.operation.binary.calculators.selection;

import org.jtwig.exceptions.CalculationException;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.FunctionExpression;
import org.jtwig.model.expression.VariableExpression;
import org.jtwig.model.position.Position;
import org.jtwig.render.RenderRequest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Collections;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class PropertyAndArgumentsCalculatorTest {
    private PropertyAndArgumentsCalculator underTest = new PropertyAndArgumentsCalculator();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void calculateNotVariableNeitherFunction() throws Exception {
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        Position position = mock(Position.class);
        Expression expression = mock(Expression.class);

        expectedException.expect(CalculationException.class);
        expectedException.expectMessage(containsString(String.format("Expecting variable or function, but got %s", expression.getClass().getSimpleName())));

        underTest.calculate(request, position, expression);
    }

    @Test
    public void calculateVariable() throws Exception {
        String identifier = "identifier";
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        Position position = mock(Position.class);
        VariableExpression expression = mock(VariableExpression.class);

        when(expression.getIdentifier()).thenReturn(identifier);

        PropertyAndArgumentsCalculator.Response result = underTest.calculate(request, position, expression);

        assertThat(result.getPropertyName(), is(identifier));
        assertThat(result.getArguments(), is(Collections.emptyList()));
    }

    @Test
    public void calculateFunction() throws Exception {
        String identifier = "identifier";
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        Position position = mock(Position.class);
        FunctionExpression expression = mock(FunctionExpression.class);
        Expression argument1 = mock(Expression.class);
        Expression argument2 = mock(Expression.class);
        Object argument1Value = new Object();
        Object argument2Value = new Object();

        when(expression.getFunctionIdentifier()).thenReturn(identifier);
        when(expression.getArguments()).thenReturn(asList(argument1, argument2));
        when(request.getEnvironment().getRenderEnvironment().getCalculateExpressionService().calculate(request, argument1)).thenReturn(argument1Value);
        when(request.getEnvironment().getRenderEnvironment().getCalculateExpressionService().calculate(request, argument2)).thenReturn(argument2Value);

        PropertyAndArgumentsCalculator.Response result = underTest.calculate(request, position, expression);

        assertThat(result.getPropertyName(), is(identifier));
        assertThat(result.getArguments(), is(asList(argument1Value, argument2Value)));
    }
}