package org.jtwig.model.expression.operation.calculators.binary;

import org.jtwig.context.RenderContext;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.position.Position;
import org.jtwig.util.JtwigValue;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LessOperationCalculatorTest {

    private final Position position = mock(Position.class);
    private final RenderContext context = mock(RenderContext.class);
    private final Expression leftOperand = mock(Expression.class);
    private final Expression rightOperand = mock(Expression.class);

    private LessOperationCalculator underTest = new LessOperationCalculator();

    @Test
    public void calculateWhenLeftGreaterThanRight() throws Exception {
        when(leftOperand.calculate(context)).thenReturn(new JtwigValue("2.0"));
        when(rightOperand.calculate(context)).thenReturn(new JtwigValue("1.0"));

        JtwigValue result = underTest.calculate(context, position, leftOperand, rightOperand);

        assertThat(result.asBoolean(), is(false));
    }

    @Test
    public void calculateWhenLeftEqualToRight() throws Exception {
        when(leftOperand.calculate(context)).thenReturn(new JtwigValue("1.0"));
        when(rightOperand.calculate(context)).thenReturn(new JtwigValue("1.0"));

        JtwigValue result = underTest.calculate(context, position, leftOperand, rightOperand);

        assertThat(result.asBoolean(), is(false));
    }

    @Test
    public void calculateWhenLeftLessThanRight() throws Exception {
        when(leftOperand.calculate(context)).thenReturn(new JtwigValue("1.0"));
        when(rightOperand.calculate(context)).thenReturn(new JtwigValue("2.0"));

        JtwigValue result = underTest.calculate(context, position, leftOperand, rightOperand);

        assertThat(result.asBoolean(), is(true));
    }
}