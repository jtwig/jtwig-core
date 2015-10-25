package org.jtwig.model.expression.operation.calculators.unary;

import org.jtwig.context.RenderContext;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.operation.unary.calculators.NotOperationCalculator;
import org.jtwig.model.position.Position;
import org.jtwig.value.JtwigValue;
import org.jtwig.value.JtwigValueFactory;
import org.jtwig.value.configuration.DefaultValueConfiguration;
import org.jtwig.value.environment.ValueEnvironment;
import org.jtwig.value.environment.ValueEnvironmentFactory;
import org.junit.Before;
import org.junit.Test;

import java.math.MathContext;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class NotOperationCalculatorTest {
    public static final ValueEnvironment VALUE_ENVIRONMENT = new ValueEnvironmentFactory().crete(new DefaultValueConfiguration());
    private final RenderContext renderContext = mock(RenderContext.class, RETURNS_DEEP_STUBS);
    private final Position position = mock(Position.class);
    private NotOperationCalculator underTest = new NotOperationCalculator();

    @Before
    public void setUp() throws Exception {
        when(renderContext.environment().value().getMathContext()).thenReturn(MathContext.DECIMAL32);
        when(renderContext.environment().value()).thenReturn(VALUE_ENVIRONMENT);
    }

    @Test
    public void notWhenTrue() throws Exception {
        Expression operand = mock(Expression.class);
        when(operand.calculate(renderContext)).thenReturn(JtwigValueFactory.value(false, VALUE_ENVIRONMENT));

        JtwigValue result = underTest.calculate(renderContext, position, operand);

        assertThat(result.asBoolean(), is(true));
    }

    @Test
    public void notWhenFalse() throws Exception {
        Expression operand = mock(Expression.class);
        when(operand.calculate(renderContext)).thenReturn(JtwigValueFactory.value(true, VALUE_ENVIRONMENT));

        JtwigValue result = underTest.calculate(renderContext, position, operand);

        assertThat(result.asBoolean(), is(false));
    }

}