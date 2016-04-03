package org.jtwig.render.expression.calculator.operation.binary.calculators.selection;

import com.google.common.base.Optional;
import org.jtwig.model.position.Position;
import org.jtwig.property.PropertyResolveRequest;
import org.jtwig.property.PropertyResolver;
import org.jtwig.reflection.model.Value;
import org.jtwig.render.RenderRequest;

import java.util.List;

public class ResolveSelectionPropertyCalculator {
    public Optional<Value> calculate(RenderRequest request, Position position, String propertyName, List<Object> arguments, Object value) {
        PropertyResolver propertyResolver = request.getEnvironment().getPropertyResolver();
        PropertyResolveRequest propertyResolveRequest = new PropertyResolveRequest(request.getRenderContext(), request.getEnvironment(), position, new Value(value), propertyName, arguments);
        return propertyResolver.resolve(propertyResolveRequest);
    }
}
