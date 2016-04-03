package org.jtwig.environment;

import com.google.common.base.Optional;
import org.jtwig.functions.resolver.FunctionResolver;
import org.jtwig.parser.JtwigParser;
import org.jtwig.property.PropertyResolver;
import org.jtwig.render.environment.RenderEnvironment;
import org.jtwig.render.expression.calculator.enumerated.EnumerationListStrategy;
import org.jtwig.resource.environment.ResourceEnvironment;
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
                       PropertyResolver propertyResolver, RenderEnvironment renderEnvironment, ValueEnvironment valueEnvironment, EnumerationListStrategy enumerationStrategy) {
        this.parser = parser;
        this.parameters = parameters;
        this.resourceEnvironment = resourceEnvironment;
        this.functionResolver = functionResolver;
        this.propertyResolver = propertyResolver;
        this.renderEnvironment = renderEnvironment;
        this.valueEnvironment = valueEnvironment;
        this.enumerationStrategy = enumerationStrategy;
    }

    public JtwigParser getParser() {
        return parser;
    }

    public ResourceEnvironment getResourceEnvironment() {
        return resourceEnvironment;
    }

    public FunctionResolver getFunctionResolver() {
        return functionResolver;
    }

    public PropertyResolver getPropertyResolver() {
        return propertyResolver;
    }

    public RenderEnvironment getRenderEnvironment() {
        return renderEnvironment;
    }

    public <T> T parameter(String name, T defaultValue) {
        return (T) Optional.fromNullable(parameters.get(name)).or(defaultValue);
    }

    public <T> T parameter (String name) {
        Optional<Object> optional = Optional.fromNullable(parameters.get(name));

        if (optional.isPresent()) {
            return (T) optional.get();
        } else {
            throw new IllegalArgumentException(String.format("No property found with name '%s'", name));
        }
    }

    public EnumerationListStrategy getListEnumerationStrategy() {
        return enumerationStrategy;
    }

    public ValueEnvironment getValueEnvironment() {
        return valueEnvironment;
    }
}
