package org.jtwig.property.selection.cache;

import org.jtwig.model.expression.Expression;

public class SelectionPropertyResolverCacheKey {
    private final int leftValueClassHashcode;
    private final Expression expression;

    private SelectionPropertyResolverCacheKey(int leftValueClassHashcode, Expression expression) {
        this.leftValueClassHashcode = leftValueClassHashcode;
        this.expression = expression;
    }

    public static SelectionPropertyResolverCacheKey createFor(int leftValueClassHashcode, Expression expression) {
        return new SelectionPropertyResolverCacheKey(leftValueClassHashcode, expression);
    }

    public static SelectionPropertyResolverCacheKey createFor(Class<?> leftValueClass, Expression expression) {
        return SelectionPropertyResolverCacheKey.createFor(leftValueClass == null ? 0 : leftValueClass.hashCode(), expression);
    }

    public static SelectionPropertyResolverCacheKey createFor(Object leftValue, Expression expression) {
        return SelectionPropertyResolverCacheKey.createFor(leftValue == null ? null : leftValue.getClass(), expression);
    }

    @Override
    public int hashCode() {
        return 89 * expression.hashCode() ^ 43 * leftValueClassHashcode;
    }
}
