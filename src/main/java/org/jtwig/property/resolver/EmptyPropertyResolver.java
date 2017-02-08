package org.jtwig.property.resolver;

import org.jtwig.property.resolver.request.PropertyResolveRequest;
import org.jtwig.value.Undefined;

public class EmptyPropertyResolver implements PropertyResolver {
    private static final EmptyPropertyResolver INSTANCE = new EmptyPropertyResolver();

    public static EmptyPropertyResolver instance () {
        return INSTANCE;
    }

    private EmptyPropertyResolver () {}

    @Override
    public Object resolve(PropertyResolveRequest request) {
        return Undefined.UNDEFINED;
    }
}
