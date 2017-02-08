package org.jtwig.property.resolver.request;

import com.google.common.base.Optional;
import org.jtwig.render.RenderRequest;

import java.util.List;

public class PropertyResolveRequest extends RenderRequest {
    private final Object context;
    private final Optional<String> propertyName;
    private final List<Object> arguments;

    public PropertyResolveRequest(RenderRequest request, Object context, Optional<String> propertyName, List<Object> arguments) {
        super(request.getRenderContext(), request.getEnvironment());
        this.context = context;
        this.propertyName = propertyName;
        this.arguments = arguments;
    }

    public Object getContext() {
        return context;
    }

    public Optional<String> getPropertyName() {
        return propertyName;
    }

    public List<Object> getArguments() {
        return arguments;
    }
}
