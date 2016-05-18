package org.jtwig.functions;

import org.jtwig.model.expression.Expression;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class EmptyExpressionResolverTest {

    @Test
    public void apply() throws Exception {
        Object result = EmptyExpressionResolver.instance().apply(mock(Expression.class));

        assertThat(result, is(nullValue()));
    }
}