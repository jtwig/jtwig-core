package org.jtwig.property;

import com.google.common.base.Optional;
import org.jtwig.reflection.model.Value;

import java.util.Map;

public class MapPropertyResolver implements PropertyResolver {
    @Override
    public Optional<Value> resolve(PropertyResolveRequest request) {
        if (request.getEntity() == null) return Optional.absent();
        if (request.getEntity().getValue() == null) return Optional.absent();

        if (request.getArguments().isEmpty()) {
            if (request.getEntity().getValue() instanceof Map) {
                Map map = (Map) request.getEntity().getValue();
                String propertyName = request.getPropertyName();
                if (map.containsKey(propertyName)) {
                    return Optional.of(new Value(map.get(propertyName)));
                }
            }
        }
        return Optional.absent();
    }
}
