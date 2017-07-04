package org.jtwig.property.selection.cache;

import com.google.common.base.Optional;
import org.jtwig.property.resolver.PropertyResolver;

public class NoSelectionPropertyResolverCache implements SelectionPropertyResolverCache {
    private static final NoSelectionPropertyResolverCache INSTANCE = new NoSelectionPropertyResolverCache();

    public static NoSelectionPropertyResolverCache noSelectionPropertyResolverCache () {
        return INSTANCE;
    }

    private NoSelectionPropertyResolverCache () {}

    @Override
    public Optional<PropertyResolver> getCachedResolver(SelectionPropertyResolverCacheKey cacheKey) {
        return Optional.absent();
    }

    @Override
    public void cacheResolver(SelectionPropertyResolverCacheKey cacheKey, PropertyResolver propertyResolver) {

    }
}
