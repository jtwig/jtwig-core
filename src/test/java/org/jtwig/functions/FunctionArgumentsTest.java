package org.jtwig.functions;

import com.google.common.base.Function;
import org.jtwig.model.expression.Expression;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FunctionArgumentsTest {
    private final Function<Expression, Object> expressionResolver = mock(Function.class);

    @Test
    public void getExpression() throws Exception {
        Expression expression = mock(Expression.class);
        FunctionArguments underTest = new FunctionArguments(expressionResolver, Collections.singletonList(expression));

        List<Expression> result = underTest.getExpressions();

        assertThat(result, contains(expression));
    }

    @Test
    public void getExpressions() throws Exception {
        Expression expression = mock(Expression.class);
        FunctionArguments underTest = new FunctionArguments(expressionResolver, Collections.singletonList(expression));

        Expression result = underTest.getExpression(0);

        assertSame(expression, result);
    }

    @Test
    public void getRemainingArguments() throws Exception {
        Expression expression1 = mock(Expression.class);
        Expression expression2 = mock(Expression.class);
        Expression expression3 = mock(Expression.class);
        Object value1 = new Object();
        Object value2 = new Object();
        Object value3 = new Object();
        FunctionArguments underTest = new FunctionArguments(expressionResolver, asList(expression1, expression2, expression3));

        when(expressionResolver.apply(expression1)).thenReturn(value1);
        when(expressionResolver.apply(expression2)).thenReturn(value2);
        when(expressionResolver.apply(expression3)).thenReturn(value3);

        Object[] result = underTest.getRemainingArguments(1);

        assertArrayEquals(new Object[]{
                value2, value3
        }, result);
    }

    @Test
    public void getRemainingArgumentsEmpty() throws Exception {
        FunctionArguments underTest = new FunctionArguments(expressionResolver, Collections.<Expression>emptyList());

        Object[] result = underTest.getRemainingArguments(1);

        assertArrayEquals(new Object[]{}, result);
    }
}