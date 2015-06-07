package org.jtwig.model.expression.operation.calculators.binary;

import org.jtwig.context.RenderContext;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.operation.binary.calculators.AndOperationCalculator;
import org.jtwig.model.position.Position;
import org.jtwig.value.JtwigValue;
import org.jtwig.value.JtwigValueFactory;
import org.jtwig.value.configuration.CompatibleModeValueConfiguration;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class AndOperationCalculatorTest {
    private final Position position = mock(Position.class);
    private final RenderContext context = mock(RenderContext.class, RETURNS_DEEP_STUBS);
    private final Expression leftOperand = mock(Expression.class);
    private final Expression rightOperand = mock(Expression.class);
    private AndOperationCalculator underTest = new AndOperationCalculator();


    @Before
    public void setUp() throws Exception {
        when(context.environment().valueConfiguration()).thenReturn(new CompatibleModeValueConfiguration());
    }

    @Test
    public void calculateWhenBothTrue() throws Exception {
        when(leftOperand.calculate(context)).thenReturn(JtwigValueFactory.value(true, new CompatibleModeValueConfiguration()));
        when(rightOperand.calculate(context)).thenReturn(JtwigValueFactory.value(true, new CompatibleModeValueConfiguration()));

        JtwigValue result = underTest.calculate(context, position, leftOperand, rightOperand);

        assertThat(result.asBoolean(), is(true));
    }

    @Test
    public void calculateWhenFirstFalse() throws Exception {
        when(leftOperand.calculate(context)).thenReturn(JtwigValueFactory.value(false, new CompatibleModeValueConfiguration()));

        JtwigValue result = underTest.calculate(context, position, leftOperand, rightOperand);

        verify(rightOperand, never()).calculate(context);
        assertThat(result.asBoolean(), is(false));
    }

    @Test
    public void calculateWhenFirstTrueSecondFalse() throws Exception {
        when(leftOperand.calculate(context)).thenReturn(JtwigValueFactory.value(true, new CompatibleModeValueConfiguration()));
        when(rightOperand.calculate(context)).thenReturn(JtwigValueFactory.value(false, new CompatibleModeValueConfiguration()));

        JtwigValue result = underTest.calculate(context, position, leftOperand, rightOperand);

        assertThat(result.asBoolean(), is(false));
    }
}