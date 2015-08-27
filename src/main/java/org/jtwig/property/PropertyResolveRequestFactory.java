package org.jtwig.property;

import org.jtwig.functions.FunctionArgument;
import org.jtwig.model.position.Position;
import org.jtwig.reflection.MethodInvoker;
import org.jtwig.reflection.model.Value;
import org.jtwig.value.converter.Converter;

import java.util.List;

public class PropertyResolveRequestFactory {
    public static PropertyResolveRequest create(Position position, Object value, String propertyName, List<FunctionArgument> arguments, Converter converter) {
        return new PropertyResolveRequest(position, new Value(value), propertyName, arguments, converter);
    }
}
