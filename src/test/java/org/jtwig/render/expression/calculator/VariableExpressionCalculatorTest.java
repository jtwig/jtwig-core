package org.jtwig.render.expression.calculator;

import org.jtwig.exceptions.ResolveValueException;
import org.jtwig.model.expression.VariableExpression;
import org.jtwig.render.RenderRequest;
import org.jtwig.value.Undefined;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class VariableExpressionCalculatorTest {
    private VariableExpressionCalculator underTest = new VariableExpressionCalculator();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void calculateWhenUndefinedAndStrictMode() throws Exception {
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        VariableExpression variableExpression = mock(VariableExpression.class);

        String identifier = "identifier";
        when(variableExpression.getIdentifier()).thenReturn(identifier);
        when(request.getRenderContext().getValueContext().getCurrent().resolve(identifier)).thenReturn(Undefined.UNDEFINED);
        when(request.getEnvironment().getRenderEnvironment().getStrictMode()).thenReturn(true);

        expectedException.expect(ResolveValueException.class);
        expectedException.expectMessage(containsString(String.format("Variable '%s' undefined", identifier)));

        underTest.calculate(request, variableExpression);
    }

    @Test
    public void calculateWhenUndefinedAndNotStrictMode() throws Exception {
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        VariableExpression variableExpression = mock(VariableExpression.class);

        String identifier = "identifier";
        when(variableExpression.getIdentifier()).thenReturn(identifier);
        when(request.getRenderContext().getValueContext().getCurrent().resolve(identifier)).thenReturn(Undefined.UNDEFINED);
        when(request.getEnvironment().getRenderEnvironment().getStrictMode()).thenReturn(false);

        Object result = underTest.calculate(request, variableExpression);

        assertSame(Undefined.UNDEFINED, result);
    }

    @Test
    public void calculateWhenDefined() throws Exception {
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        VariableExpression variableExpression = mock(VariableExpression.class);
        Object value = new Object();
        String identifier = "identifier";

        when(variableExpression.getIdentifier()).thenReturn(identifier);
        when(request.getRenderContext().getValueContext().getCurrent().resolve(identifier)).thenReturn(value);
        when(request.getEnvironment().getRenderEnvironment().getStrictMode()).thenReturn(false);

        Object result = underTest.calculate(request, variableExpression);

        assertSame(value, result);
    }
}