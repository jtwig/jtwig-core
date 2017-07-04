package org.jtwig.property.selection.cache;

import com.google.common.base.Optional;
import org.jtwig.model.expression.Expression;
import org.jtwig.property.resolver.PropertyResolver;

import java.util.concurrent.ConcurrentHashMap;

public class SelectionPropertyResolverPersistentCache implements SelectionPropertyResolverCache {
    private final ConcurrentHashMap<SelectionPropertyResolverCacheKey, PropertyResolver> hashMap;

    public SelectionPropertyResolverPersistentCache(ConcurrentHashMap<SelectionPropertyResolverCacheKey, PropertyResolver> hashMap) {
        this.hashMap = hashMap;
    }

    @Override
    public Optional<PropertyResolver> getCachedResolver(int leftValueClassHashcode, Expression expression) {
        return Optional.fromNullable(hashMap.get(SelectionPropertyResolverCacheKey.createFor(leftValueClassHashcode, expression)));
    }

    @Override
    public void cacheResolver(int leftValueClassHashcode, Expression expression, PropertyResolver propertyResolver) {
        hashMap.put(SelectionPropertyResolverCacheKey.createFor(leftValueClassHashcode, expression), propertyResolver);
    }
}
