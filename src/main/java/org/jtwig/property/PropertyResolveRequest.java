package org.jtwig.property;

import org.jtwig.model.position.Position;
import org.jtwig.reflection.model.Value;
import org.jtwig.value.JtwigValue;
import org.jtwig.value.converter.Converter;

import java.util.List;

public class PropertyResolveRequest {
    private final Position position;
    private final Value entity;
    private final String propertyName;
    private final List<JtwigValue> arguments;
    private final Converter converter;

    public PropertyResolveRequest(Position position, Value entity, String propertyName, List<JtwigValue> arguments, Converter converter) {
        this.position = position;
        this.entity = entity;
        this.propertyName = propertyName;
        this.arguments = arguments;
        this.converter = converter;
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

    public List<JtwigValue> getArguments() {
        return arguments;
    }

    public Converter getConverter() {
        return converter;
    }
}
