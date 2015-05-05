package org.jtwig.model.expression.function;

import com.google.common.base.Optional;
import org.jtwig.context.RenderContext;
import org.jtwig.functions.FunctionArgument;
import org.jtwig.model.expression.Expression;
import org.jtwig.value.JtwigValue;
import org.jtwig.value.JtwigValueFactory;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ArgumentTest {
    private final Expression expression = mock(Expression.class);
    private final Optional<String> name = Optional.<String>absent();
    private final RenderContext context = mock(RenderContext.class);
    private Argument underTest = new Argument(name, expression);

    @Test
    public void calculate() throws Exception {
        JtwigValue value = JtwigValueFactory.create("");
        when(expression.calculate(context)).thenReturn(value);
        FunctionArgument result = underTest.calculate(context);

        assertThat(result.getName(), is(name));
        assertThat(result.getValue(), is(value));
    }
}