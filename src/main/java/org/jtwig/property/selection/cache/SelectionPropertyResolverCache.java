package org.jtwig.property.selection.cache;

import com.google.common.base.Optional;
import org.jtwig.model.expression.Expression;
import org.jtwig.property.resolver.PropertyResolver;

public interface SelectionPropertyResolverCache {
    Optional<PropertyResolver> getCachedResolver(int leftValueClassHashcode, Expression expression);
    void cacheResolver(int leftValueClassHashcode, Expression expression, PropertyResolver propertyResolver);
}
