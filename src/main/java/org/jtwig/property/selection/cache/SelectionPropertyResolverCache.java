package org.jtwig.property.selection.cache;

import com.google.common.base.Optional;
import org.jtwig.property.resolver.PropertyResolver;

public interface SelectionPropertyResolverCache {
    Optional<PropertyResolver> getCachedResolver(SelectionPropertyResolverCacheKey cacheKey);

    void cacheResolver(SelectionPropertyResolverCacheKey cacheKey, PropertyResolver propertyResolver);
}
