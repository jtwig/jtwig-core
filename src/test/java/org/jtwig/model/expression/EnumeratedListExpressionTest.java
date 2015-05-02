package org.jtwig.model.expression;

import org.jtwig.context.RenderContext;
import org.jtwig.model.position.Position;
import org.jtwig.util.JtwigValue;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EnumeratedListExpressionTest {
    private final Position position = mock(Position.class);
    private final ArrayList<Expression> expressions = new ArrayList<>();
    private final EnumeratedListExpression underTest = new EnumeratedListExpression(position, expressions);
    private final RenderContext context = mock(RenderContext.class);

    @Before
    public void setUp() throws Exception {
        expressions.clear();
    }

    @Test
    public void calculateWhenEmpty() throws Exception {
        JtwigValue result = underTest.calculate(context);

        assertThat(result.asCollection(), empty());
    }

    @Test
    public void calculateWhenNonEmpty() throws Exception {
        Expression expression = mock(Expression.class);
        when(expression.calculate(context)).thenReturn(new JtwigValue("asd"));
        expressions.add(expression);

        JtwigValue result = underTest.calculate(context);

        assertThat(result.asCollection(), hasItem("asd"));
    }
}