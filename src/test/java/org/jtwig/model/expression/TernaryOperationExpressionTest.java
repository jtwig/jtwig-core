package org.jtwig.model.expression;

import org.jtwig.context.RenderContext;
import org.jtwig.model.position.Position;
import org.jtwig.util.JtwigValue;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TernaryOperationExpressionTest {
    private final Position position = mock(Position.class);
    private final Expression condition = mock(Expression.class);
    private final Expression ifTrueExpression = mock(Expression.class);
    private final Expression ifFalseExpression = mock(Expression.class);
    private final RenderContext context = mock(RenderContext.class);
    private TernaryOperationExpression underTest = new TernaryOperationExpression(position, condition, ifTrueExpression, ifFalseExpression);

    @Test
    public void calculateWhenConditionTrue() throws Exception {
        JtwigValue value = new JtwigValue("one");
        when(condition.calculate(context)).thenReturn(new JtwigValue(true));
        when(ifTrueExpression.calculate(context)).thenReturn(value);

        JtwigValue result = underTest.calculate(context);

        assertThat(result, is(value));
        verify(ifFalseExpression, never()).calculate(context);
    }

    @Test
    public void calculateWhenConditionFalse() throws Exception {
        JtwigValue value = new JtwigValue("one");
        when(condition.calculate(context)).thenReturn(new JtwigValue(false));
        when(ifFalseExpression.calculate(context)).thenReturn(value);

        JtwigValue result = underTest.calculate(context);

        assertThat(result, is(value));
        verify(ifTrueExpression, never()).calculate(context);
    }
}