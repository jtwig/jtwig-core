package org.jtwig.property.selection.cache;

import com.google.common.base.Optional;
import org.jtwig.model.expression.Expression;
import org.jtwig.property.resolver.PropertyResolver;

public class NoSelectionPropertyResolverCache implements SelectionPropertyResolverCache {
    private static final NoSelectionPropertyResolverCache INSTANCE = new NoSelectionPropertyResolverCache();

    public static NoSelectionPropertyResolverCache noSelectionPropertyResolverCache () {
        return INSTANCE;
    }

    private NoSelectionPropertyResolverCache () {}

    @Override
    public Optional<PropertyResolver> getCachedResolver(int leftValueClassHashcode, Expression expression) {
        return Optional.absent();
    }

    @Override
    public void cacheResolver(int leftValueClassHashcode, Expression expression, PropertyResolver propertyResolver) {

    }
}
