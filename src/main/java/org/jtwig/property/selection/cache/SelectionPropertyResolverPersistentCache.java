package org.jtwig.property.selection.cache;

import com.google.common.base.Optional;
import org.jtwig.model.expression.Expression;
import org.jtwig.property.resolver.PropertyResolver;

import java.util.concurrent.ConcurrentHashMap;

public class SelectionPropertyResolverPersistentCache implements SelectionPropertyResolverCache {
    private final ConcurrentHashMap<Object, PropertyResolver> hashMap;

    public SelectionPropertyResolverPersistentCache(ConcurrentHashMap<Object, PropertyResolver> hashMap) {
        this.hashMap = hashMap;
    }

    @Override
    public Optional<PropertyResolver> getCachedResolver(int leftValueClassHashcode, Expression expression) {
        return Optional.fromNullable(hashMap.get(generateCacheKey(leftValueClassHashcode, expression)));
    }

    private static int generateCacheKey(int leftValueClassHashcode, Expression expression) {
        return 89 * expression.hashCode() ^ 43 * leftValueClassHashcode;
    }

    @Override
    public void cacheResolver(int leftValueClassHashcode, Expression expression, PropertyResolver propertyResolver) {
        hashMap.put(generateCacheKey(leftValueClassHashcode, expression), propertyResolver);
    }
}
