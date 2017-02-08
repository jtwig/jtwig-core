package org.jtwig.property.selection.cache;

import com.google.common.base.Optional;
import org.jtwig.model.expression.Expression;
import org.jtwig.property.resolver.PropertyResolver;

import java.util.concurrent.ConcurrentHashMap;

public class SelectionPropertyResolverPersistentCache implements SelectionPropertyResolverCache {
    private final ConcurrentHashMap<Expression, PropertyResolver> hashMap;

    public SelectionPropertyResolverPersistentCache(ConcurrentHashMap<Expression, PropertyResolver> hashMap) {
        this.hashMap = hashMap;
    }

    @Override
    public Optional<PropertyResolver> getCachedResolver(Expression expression) {
        return Optional.fromNullable(hashMap.get(expression));
    }

    @Override
    public void cacheResolver(Expression expression, PropertyResolver propertyResolver) {
        hashMap.put(expression, propertyResolver);
    }
}
