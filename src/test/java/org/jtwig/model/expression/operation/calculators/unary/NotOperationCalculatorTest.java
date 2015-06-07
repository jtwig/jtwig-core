package org.jtwig.model.expression.operation.calculators.unary;

import org.jtwig.context.RenderContext;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.operation.unary.calculators.NotOperationCalculator;
import org.jtwig.model.position.Position;
import org.jtwig.value.JtwigValue;
import org.jtwig.value.JtwigValueFactory;
import org.jtwig.value.configuration.CompatibleModeValueConfiguration;
import org.junit.Before;
import org.junit.Test;

import java.math.MathContext;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class NotOperationCalculatorTest {
    private final RenderContext renderContext = mock(RenderContext.class, RETURNS_DEEP_STUBS);
    private final Position position = mock(Position.class);
    private NotOperationCalculator underTest = new NotOperationCalculator();

    @Before
    public void setUp() throws Exception {
        when(renderContext.environment().renderConfiguration().mathContext()).thenReturn(MathContext.DECIMAL32);
        when(renderContext.environment().valueConfiguration()).thenReturn(new CompatibleModeValueConfiguration());
    }

    @Test
    public void notWhenTrue() throws Exception {
        Expression operand = mock(Expression.class);
        when(operand.calculate(renderContext)).thenReturn(JtwigValueFactory.value(false, new CompatibleModeValueConfiguration()));

        JtwigValue result = underTest.calculate(renderContext, position, operand);

        assertThat(result.asBoolean(), is(true));
    }

    @Test
    public void notWhenFalse() throws Exception {
        Expression operand = mock(Expression.class);
        when(operand.calculate(renderContext)).thenReturn(JtwigValueFactory.value(true, new CompatibleModeValueConfiguration()));

        JtwigValue result = underTest.calculate(renderContext, position, operand);

        assertThat(result.asBoolean(), is(false));
    }

}