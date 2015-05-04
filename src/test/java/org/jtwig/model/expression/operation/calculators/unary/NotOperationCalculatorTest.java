package org.jtwig.model.expression.operation.calculators.unary;

import org.jtwig.context.RenderContext;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.position.Position;
import org.jtwig.util.JtwigValue;
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
        when(renderContext.configuration().mathContext()).thenReturn(MathContext.DECIMAL32);
    }

    @Test
    public void notWhenTrue() throws Exception {
        Expression operand = mock(Expression.class);
        when(operand.calculate(renderContext)).thenReturn(new JtwigValue(false));

        JtwigValue result = underTest.calculate(renderContext, position, operand);

        assertThat(result.asBoolean(), is(true));
    }

    @Test
    public void notWhenFalse() throws Exception {
        Expression operand = mock(Expression.class);
        when(operand.calculate(renderContext)).thenReturn(new JtwigValue(true));

        JtwigValue result = underTest.calculate(renderContext, position, operand);

        assertThat(result.asBoolean(), is(false));
    }

}