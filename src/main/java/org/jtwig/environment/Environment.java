package org.jtwig.environment;

import com.google.common.base.Optional;
import org.jtwig.escape.environment.EscapeEnvironment;
import org.jtwig.functions.resolver.FunctionResolver;
import org.jtwig.parser.JtwigParser;
import org.jtwig.property.environment.PropertyResolverEnvironment;
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
    private final PropertyResolverEnvironment propertyResolverEnvironment;
    private final RenderEnvironment renderEnvironment;
    private final ValueEnvironment valueEnvironment;
    private final EnumerationListStrategy enumerationStrategy;
    private final EscapeEnvironment escapeEnvironment;

    public Environment(JtwigParser parser, Map<String, Object> parameters,
                       ResourceEnvironment resourceEnvironment, FunctionResolver functionResolver,
                       PropertyResolverEnvironment propertyResolverEnvironment, RenderEnvironment renderEnvironment,
                       ValueEnvironment valueEnvironment, EnumerationListStrategy enumerationStrategy,
                       EscapeEnvironment escapeEnvironment) {
        this.parser = parser;
        this.parameters = parameters;
        this.resourceEnvironment = resourceEnvironment;
        this.functionResolver = functionResolver;
        this.propertyResolverEnvironment = propertyResolverEnvironment;
        this.renderEnvironment = renderEnvironment;
        this.valueEnvironment = valueEnvironment;
        this.enumerationStrategy = enumerationStrategy;
        this.escapeEnvironment = escapeEnvironment;
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

    public PropertyResolverEnvironment getPropertyResolverEnvironment() {
        return propertyResolverEnvironment;
    }

    public RenderEnvironment getRenderEnvironment() {
        return renderEnvironment;
    }

    public EscapeEnvironment getEscapeEnvironment() {
        return escapeEnvironment;
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
