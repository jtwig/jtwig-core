package org.jtwig.property.selection.cache;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.VariableExpression;
import org.jtwig.model.position.Position;
import org.jtwig.resource.reference.ResourceReference;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class SelectionPropertyResolverCacheKeyTest {

    private Expression expression;

    @Before
    public void prepareTestExpression() {
        ResourceReference resourceReference = ResourceReference.inline("{{ item }}");
        Position position = new Position(resourceReference, 15, 28);
        expression = new VariableExpression(position, "item");
    }

    @Test
    public void createForInt() {
        SelectionPropertyResolverCacheKey cacheKey = SelectionPropertyResolverCacheKey.createFor(42, expression);
        assertThat(cacheKey.hashCode(), not(is(0)));
    }

    @Test
    public void createForClass() {
        SelectionPropertyResolverCacheKey cacheKey = SelectionPropertyResolverCacheKey.createFor(SomeClass.class, expression);
        assertThat(cacheKey.hashCode(), not(is(0)));
    }

    @Test
    public void createForObject() {
        SelectionPropertyResolverCacheKey cacheKey = SelectionPropertyResolverCacheKey.createFor(new SomeClass(), expression);
        assertThat(cacheKey.hashCode(), not(is(0)));
    }

    @Test
    public void createForNullObject() {
        SelectionPropertyResolverCacheKey cacheKey = SelectionPropertyResolverCacheKey.createFor((Object) null, expression);
        assertThat(cacheKey.hashCode(), not(is(0)));
    }

    @Test
    public void createForNullClass() {
        SelectionPropertyResolverCacheKey cacheKey = SelectionPropertyResolverCacheKey.createFor((Class<?>) null, expression);
        assertThat(cacheKey.hashCode(), not(is(0)));
    }

    private class SomeClass {
    }
}