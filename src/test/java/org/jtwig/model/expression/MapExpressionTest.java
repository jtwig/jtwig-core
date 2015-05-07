package org.jtwig.model.expression;

import org.jtwig.context.RenderContext;
import org.jtwig.model.position.Position;
import org.jtwig.value.JtwigValue;
import org.jtwig.value.JtwigValueFactory;
import org.jtwig.value.configuration.NamedValueConfiguration;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MapExpressionTest {
    private final Position position = mock(Position.class);
    private final HashMap<String, Expression> map = new HashMap<>();
    private final RenderContext context = mock(RenderContext.class, RETURNS_DEEP_STUBS);
    private MapExpression underTest = new MapExpression(position, map);

    @Before
    public void setUp() throws Exception {
        map.clear();
        when(context.configuration().valueConfiguration()).thenReturn(NamedValueConfiguration.COMPATIBLE_MODE);
    }

    @Test
    public void calculateWhenEmpty() throws Exception {
        JtwigValue result = underTest.calculate(context);

        assertThat(result.asMap().isEmpty(), is(true));
    }

    @Test
    public void calculateWhenNonEmpty() throws Exception {
        String key = "one";
        Expression value = mock(Expression.class);
        when(value.calculate(context)).thenReturn(JtwigValueFactory.value("two", NamedValueConfiguration.COMPATIBLE_MODE));
        map.put(key, value);

        JtwigValue result = underTest.calculate(context);

        assertThat(result.asMap().isEmpty(), is(false));
        assertThat(result.asMap().get("one"), is((Object) "two"));
    }
}