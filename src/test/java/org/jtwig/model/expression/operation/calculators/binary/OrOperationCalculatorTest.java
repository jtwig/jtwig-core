package org.jtwig.model.expression.operation.calculators.binary;

import org.jtwig.context.RenderContext;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.position.Position;
import org.jtwig.util.JtwigValue;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class OrOperationCalculatorTest {
    private final Position position = mock(Position.class);
    private final RenderContext context = mock(RenderContext.class);
    private final Expression leftOperand = mock(Expression.class);
    private final Expression rightOperand = mock(Expression.class);
    private OrOperationCalculator underTest = new OrOperationCalculator();

    @Test
    public void calculateWhenBothFalse() throws Exception {
        when(leftOperand.calculate(context)).thenReturn(new JtwigValue(false));
        when(rightOperand.calculate(context)).thenReturn(new JtwigValue(false));

        JtwigValue result = underTest.calculate(context, position, leftOperand, rightOperand);

        assertThat(result.asBoolean(), is(false));
    }

    @Test
    public void calculateWhenFirstTrue() throws Exception {
        when(leftOperand.calculate(context)).thenReturn(new JtwigValue(true));

        JtwigValue result = underTest.calculate(context, position, leftOperand, rightOperand);

        verify(rightOperand, never()).calculate(context);
        assertThat(result.asBoolean(), is(true));
    }

    @Test
    public void calculateWhenFirstFalseSecondTrue() throws Exception {
        when(leftOperand.calculate(context)).thenReturn(new JtwigValue(false));
        when(rightOperand.calculate(context)).thenReturn(new JtwigValue(true));

        JtwigValue result = underTest.calculate(context, position, leftOperand, rightOperand);

        assertThat(result.asBoolean(), is(true));
    }
}