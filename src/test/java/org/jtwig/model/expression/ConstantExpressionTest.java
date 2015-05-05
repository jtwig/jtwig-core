package org.jtwig.model.expression;

import org.jtwig.context.RenderContext;
import org.jtwig.model.position.Position;
import org.jtwig.value.JtwigValue;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class ConstantExpressionTest {
    private final RenderContext context = mock(RenderContext.class);
    private ConstantExpression underTest;

    @Test
    public void justReturnsGivenValue() throws Exception {
        Object input = new Object();
        underTest = new ConstantExpression(mock(Position.class), input);

        JtwigValue result = underTest.calculate(context);

        assertThat(result.asObject(), is(input));
    }
}