package org.jtwig.property.resolver;

import org.jtwig.property.resolver.request.PropertyResolveRequest;

public interface PropertyResolver {
    /**
     * @return Undefined.Undefined if no result
     */
    Object resolve (PropertyResolveRequest request);
}
