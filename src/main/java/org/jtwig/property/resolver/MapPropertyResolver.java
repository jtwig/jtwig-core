package org.jtwig.property.resolver;

import com.google.common.base.Optional;
import org.jtwig.property.resolver.request.PropertyResolveRequest;
import org.jtwig.reflection.model.Value;

import java.util.Map;

public class MapPropertyResolver implements PropertyResolver {
    @Override
    public Optional<Value> resolve(PropertyResolveRequest request) {
        if (request.getContext() == null) return Optional.absent();
        if (!(request.getContext() instanceof Map)) return Optional.absent();

        if(((Map) request.getContext()).containsKey(request.getPropertyName().get())){
            return Optional.of(new Value(((Map) request.getContext()).get(request.getPropertyName().get())));
        }else{
            return Optional.absent();
        }
    }
}
