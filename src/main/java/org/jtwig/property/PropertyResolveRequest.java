package org.jtwig.property;

import org.jtwig.environment.Environment;
import org.jtwig.model.position.Position;
import org.jtwig.reflection.model.Value;
import org.jtwig.render.RenderRequest;
import org.jtwig.render.context.model.RenderContext;

import java.util.List;

public class PropertyResolveRequest extends RenderRequest {
    private final Position position;
    private final Value entity;
    private final String propertyName;
    private final List<Object> arguments;

    public PropertyResolveRequest(RenderContext renderContext, Environment environment, Position position, Value entity, String propertyName, List<Object> arguments) {
        super(renderContext, environment);
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

    public List<Object> getArguments() {
        return arguments;
    }
}
