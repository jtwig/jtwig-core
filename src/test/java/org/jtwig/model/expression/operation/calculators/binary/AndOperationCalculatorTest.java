package org.jtwig.model.expression.operation.calculators.binary;

import org.jtwig.context.RenderContext;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.operation.binary.calculators.AndOperationCalculator;
import org.jtwig.model.position.Position;
import org.jtwig.value.JtwigValue;
import org.jtwig.value.JtwigValueFactory;
import org.jtwig.value.configuration.DefaultValueConfiguration;
import org.jtwig.value.environment.ValueEnvironment;
import org.jtwig.value.environment.ValueEnvironmentFactory;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class AndOperationCalculatorTest {
    public static final ValueEnvironment VALUE_ENVIRONMENT = new ValueEnvironmentFactory().crete(new DefaultValueConfiguration());
    private final Position position = mock(Position.class);
    private final RenderContext context = mock(RenderContext.class, RETURNS_DEEP_STUBS);
    private final Expression leftOperand = mock(Expression.class);
    private final Expression rightOperand = mock(Expression.class);
    private AndOperationCalculator underTest = new AndOperationCalculator();


    @Before
    public void setUp() throws Exception {
        when(context.environment().value()).thenReturn(VALUE_ENVIRONMENT);
    }

    @Test
    public void calculateWhenBothTrue() throws Exception {
        when(leftOperand.calculate(context)).thenReturn(JtwigValueFactory.value(true, VALUE_ENVIRONMENT));
        when(rightOperand.calculate(context)).thenReturn(JtwigValueFactory.value(true, VALUE_ENVIRONMENT));

        JtwigValue result = underTest.calculate(context, position, leftOperand, rightOperand);

        assertThat(result.asBoolean(), is(true));
    }

    @Test
    public void calculateWhenFirstFalse() throws Exception {
        when(leftOperand.calculate(context)).thenReturn(JtwigValueFactory.value(false, VALUE_ENVIRONMENT));

        JtwigValue result = underTest.calculate(context, position, leftOperand, rightOperand);

        verify(rightOperand, never()).calculate(context);
        assertThat(result.asBoolean(), is(false));
    }

    @Test
    public void calculateWhenFirstTrueSecondFalse() throws Exception {
        when(leftOperand.calculate(context)).thenReturn(JtwigValueFactory.value(true, VALUE_ENVIRONMENT));
        when(rightOperand.calculate(context)).thenReturn(JtwigValueFactory.value(false, VALUE_ENVIRONMENT));

        JtwigValue result = underTest.calculate(context, position, leftOperand, rightOperand);

        assertThat(result.asBoolean(), is(false));
    }
}