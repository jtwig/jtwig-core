package org.jtwig.property;

import com.google.common.base.Optional;
import org.jtwig.util.JtwigValue;

import java.util.Collection;

public class CompositePropertyResolver implements PropertyResolver {
    private final Collection<PropertyResolver> propertyResolvers;

    public CompositePropertyResolver(Collection<PropertyResolver> propertyResolvers) {
        this.propertyResolvers = propertyResolvers;
    }

    @Override
    public Optional<JtwigValue> resolve(PropertyResolveRequest request) {
        for (PropertyResolver propertyResolver : propertyResolvers) {
            Optional<JtwigValue> optional = propertyResolver.resolve(request);
            if (optional.isPresent()) {
                return optional;
            }
        }

        return Optional.absent();
    }

    public int size () {
        return propertyResolvers.size();
    }
}
