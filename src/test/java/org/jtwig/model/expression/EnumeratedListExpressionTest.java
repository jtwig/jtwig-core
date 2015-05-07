package org.jtwig.model.expression;

import org.jtwig.context.RenderContext;
import org.jtwig.model.position.Position;
import org.jtwig.value.JtwigValue;
import org.jtwig.value.JtwigValueFactory;
import org.jtwig.value.configuration.NamedValueConfiguration;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EnumeratedListExpressionTest {
    private final Position position = mock(Position.class);
    private final ArrayList<Expression> expressions = new ArrayList<>();
    private final EnumeratedListExpression underTest = new EnumeratedListExpression(position, expressions);
    private final RenderContext context = mock(RenderContext.class, RETURNS_DEEP_STUBS);

    @Before
    public void setUp() throws Exception {
        expressions.clear();
        when(context.configuration().valueConfiguration()).thenReturn(NamedValueConfiguration.COMPATIBLE_MODE);
    }

    @Test
    public void calculateWhenEmpty() throws Exception {
        JtwigValue result = underTest.calculate(context);

        assertThat(result.asCollection().isEmpty(), is(true));
    }

    @Test
    public void calculateWhenNonEmpty() throws Exception {
        Expression expression = mock(Expression.class);
        when(expression.calculate(context)).thenReturn(JtwigValueFactory.value("asd", NamedValueConfiguration.COMPATIBLE_MODE));
        expressions.add(expression);

        JtwigValue result = underTest.calculate(context);

        assertThat(result.asCollection(), hasItem("asd"));
    }
}