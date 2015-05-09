package org.jtwig.property;

import org.jtwig.functions.FunctionArgument;
import org.jtwig.model.position.Position;
import org.jtwig.reflection.model.Value;

import java.util.Collection;

public class PropertyResolveRequest {
    private final Position position;
    private final Value entity;
    private final String propertyName;
    private final Collection<FunctionArgument> arguments;

    public PropertyResolveRequest(Position position, Value entity, String propertyName, Collection<FunctionArgument> arguments) {
        this.position = position;
        this.entity = entity;
        this.propertyName = propertyName;
        this.arguments = arguments;
    }

    public Position getPosition() {
        return position;
    }

    public Value getEntity() {
        return entity;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public Collection<FunctionArgument> getArguments() {
        return arguments;
    }
}
