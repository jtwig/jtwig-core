package org.jtwig.model.expression;

import com.google.common.base.Optional;
import org.jtwig.context.RenderContext;
import org.jtwig.exceptions.CalculationException;
import org.jtwig.model.position.Position;
import org.jtwig.reflection.model.Value;
import org.jtwig.value.JtwigValue;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class VariableExpressionTest {
    private final Position position = mock(Position.class);
    private final String identifier = "variable";
    private final RenderContext context = mock(RenderContext.class, RETURNS_DEEP_STUBS);
    private VariableExpression underTest = new VariableExpression(position, identifier);

    @Test
    public void calculateWhenVariableDefined() throws Exception {
        Value value = new Value("one");
        when(context.valueContext().value(identifier)).thenReturn(Optional.of(value));

        JtwigValue result = underTest.calculate(context);

        assertThat(result.asObject(), is(value.getValue()));
    }

    @Test
    public void calculateWhenVariableUndefinedAndStrictModeDisabled() throws Exception {
        when(context.environment().renderConfiguration().strictMode()).thenReturn(false);
        when(context.valueContext().value(identifier)).thenReturn(Optional.<Value>absent());

        JtwigValue result = underTest.calculate(context);

        assertThat(result.isUndefined(), is(true));
    }

    @Test(expected = CalculationException.class)
    public void calculateWhenVariableUndefinedAndStrictModeEnabled() throws Exception {
        when(context.environment().renderConfiguration().strictMode()).thenReturn(true);
        when(context.valueContext().value(identifier)).thenReturn(Optional.<Value>absent());

        underTest.calculate(context);
    }
}