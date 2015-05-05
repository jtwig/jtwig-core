package org.jtwig.model.expression.operation.calculators.binary;

import org.jtwig.context.RenderContext;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.position.Position;
import org.jtwig.value.JtwigValue;
import org.jtwig.value.JtwigValueFactory;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EquivalentOperationCalculatorTest {
    private final Position position = mock(Position.class);
    private final RenderContext context = mock(RenderContext.class);
    private final Expression leftOperand = mock(Expression.class);
    private final Expression rightOperand = mock(Expression.class);

    private EquivalentOperationCalculator underTest = new EquivalentOperationCalculator();

    @Test
    public void calculateWhenEqual() throws Exception {
        when(leftOperand.calculate(context)).thenReturn(JtwigValueFactory.create(""));
        when(rightOperand.calculate(context)).thenReturn(JtwigValueFactory.create(""));

        JtwigValue result = underTest.calculate(context, position, leftOperand, rightOperand);

        assertThat(result.asBoolean(), is(true));
    }

    @Test
    public void calculateWhenNotEqual() throws Exception {
        when(leftOperand.calculate(context)).thenReturn(JtwigValueFactory.create("a"));
        when(rightOperand.calculate(context)).thenReturn(JtwigValueFactory.create(""));

        JtwigValue result = underTest.calculate(context, position, leftOperand, rightOperand);

        assertThat(result.asBoolean(), is(false));
    }
}