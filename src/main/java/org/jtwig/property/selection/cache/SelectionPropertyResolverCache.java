package org.jtwig.property.selection.cache;

import com.google.common.base.Optional;
import org.jtwig.model.expression.Expression;
import org.jtwig.property.resolver.PropertyResolver;

public interface SelectionPropertyResolverCache {
    Optional<PropertyResolver> getCachedResolver (Expression expression);
    void cacheResolver (Expression expression, PropertyResolver propertyResolver);
}
