package org.jtwig.property;

import org.jtwig.functions.FunctionArgument;
import org.jtwig.model.position.Position;
import org.jtwig.reflection.model.Value;

import java.util.Collection;

public class PropertyResolveRequestFactory {
    public static PropertyResolveRequest create(Position position, Object value, String propertyName, Collection<FunctionArgument> arguments) {
        return new PropertyResolveRequest(position, new Value(value), propertyName, arguments);
    }
}
