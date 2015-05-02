package org.jtwig.property;

import org.jtwig.functions.FunctionArgument;
import org.jtwig.model.expression.function.Argument;
import org.jtwig.model.position.Position;

import java.util.Collection;

public class PropertyResolveRequest {
    private final Position position;
    private final Object entity;
    private final String propertyName;
    private final Collection<FunctionArgument> arguments;

    public PropertyResolveRequest(Position position, Object entity, String propertyName, Collection<FunctionArgument> arguments) {
        this.position = position;
        this.entity = entity;
        this.propertyName = propertyName;
        this.arguments = arguments;
    }

    public Position getPosition() {
        return position;
    }

    public Object getEntity() {
        return entity;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public Collection<FunctionArgument> getArguments() {
        return arguments;
    }
}
