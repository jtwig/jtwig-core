package org.jtwig.environment;

import com.google.common.base.Optional;
import org.jtwig.functions.resolver.FunctionResolver;
import org.jtwig.model.expression.lists.EnumerationListStrategy;
import org.jtwig.parser.JtwigParser;
import org.jtwig.property.PropertyResolver;
import org.jtwig.render.environment.RenderEnvironment;
import org.jtwig.resource.environment.ResourceEnvironment;
import org.jtwig.util.OptionalUtils;
import org.jtwig.value.environment.ValueEnvironment;

import java.util.Map;

/**
 * A representation of all Jtwig configured behaviour.
 */
public class Environment {
    private final JtwigParser parser;
    private final Map<String, Object> parameters;
    private final ResourceEnvironment resourceEnvironment;
    private final FunctionResolver functionResolver;
    private final PropertyResolver propertyResolver;
    private final RenderEnvironment renderEnvironment;
    private final ValueEnvironment valueEnvironment;
    private final EnumerationListStrategy enumerationStrategy;

    public Environment(JtwigParser parser, Map<String, Object> parameters,
                       ResourceEnvironment resourceEnvironment, FunctionResolver functionResolver,
                       PropertyResolver propertyResolver, RenderEnvironment renderEnvironment,
                       ValueEnvironment valueEnvironment, EnumerationListStrategy enumerationStrategy) {
        this.parser = parser;
        this.parameters = parameters;
        this.resourceEnvironment = resourceEnvironment;
        this.functionResolver = functionResolver;
        this.propertyResolver = propertyResolver;
        this.renderEnvironment = renderEnvironment;
        this.valueEnvironment = valueEnvironment;
        this.enumerationStrategy = enumerationStrategy;
    }

    public JtwigParser parser() {
        return parser;
    }

    public ResourceEnvironment resources() {
        return resourceEnvironment;
    }

    public FunctionResolver functionResolver() {
        return functionResolver;
    }

    public PropertyResolver propertyResolver() {
        return propertyResolver;
    }

    public ValueEnvironment value() {
        return valueEnvironment;
    }

    public RenderEnvironment rendering() {
        return renderEnvironment;
    }

    public <T> T parameter(String name, T defaultValue) {
        return (T) Optional.fromNullable(parameters.get(name)).or(defaultValue);
    }

    public <T> T parameter (String name) {
        return (T) Optional.fromNullable(parameters.get(name))
                .or(OptionalUtils.<T>throwException(String.format("No property found with name '%s'", name)));
    }

    public EnumerationListStrategy enumerationStrategy() {
        return enumerationStrategy;
    }
}
