package org.jtwig.property.resolver.request;

import org.jtwig.property.selection.SelectionRequest;

public class PropertyResolveRequestFactory {
    private final PropertyNameExtractor propertyNameExtractor;
    private final ArgumentsExtractor argumentsExtractor;

    public PropertyResolveRequestFactory(PropertyNameExtractor propertyNameExtractor, ArgumentsExtractor argumentsExtractor) {
        this.propertyNameExtractor = propertyNameExtractor;
        this.argumentsExtractor = argumentsExtractor;
    }

    public PropertyResolveRequest create(SelectionRequest request, Object leftValue) {
        return new PropertyResolveRequest(
                request,
                leftValue,
                propertyNameExtractor.extract(request.getRightExpression()),
                argumentsExtractor.extract(request, request.getRightExpression())
        );
    }
}
