package org.jtwig.model.expression.function;

import org.jtwig.context.RenderContext;
import org.jtwig.model.expression.Expression;
import org.jtwig.value.JtwigValue;
import org.jtwig.value.JtwigValueFactory;
import org.jtwig.value.configuration.DefaultValueConfiguration;
import org.jtwig.value.environment.ValueEnvironment;
import org.jtwig.value.environment.ValueEnvironmentFactory;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ArgumentTest {
    public static final ValueEnvironment VALUE_ENVIRONMENT = new ValueEnvironmentFactory().crete(new DefaultValueConfiguration());
    private final Expression expression = mock(Expression.class);
    private final RenderContext context = mock(RenderContext.class);
    private Argument underTest = new Argument(expression);

    @Test
    public void calculate() throws Exception {
        Object value = "";
        JtwigValue jtwigValue = JtwigValueFactory.value(value, VALUE_ENVIRONMENT);
        when(expression.calculate(context)).thenReturn(jtwigValue);
        JtwigValue result = underTest.calculate(context);

        assertThat(result.asObject(), is(value));
    }
}