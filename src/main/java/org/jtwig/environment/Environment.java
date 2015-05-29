package org.jtwig.environment;

import com.google.common.base.Optional;
import org.jtwig.context.RenderConfiguration;
import org.jtwig.functions.resolver.FunctionResolver;
import org.jtwig.model.expression.lists.EnumerationListStrategy;
import org.jtwig.parser.JtwigParser;
import org.jtwig.property.PropertyResolver;
import org.jtwig.resource.resolver.ResourceResolver;
import org.jtwig.util.OptionalUtils;
import org.jtwig.value.configuration.ValueConfiguration;

import java.util.Map;

public class Environment {
    private final JtwigParser parser;
    private final Map<String, Object> parameters;
    private final ResourceResolver resourceResolver;
    private final FunctionResolver functionResolver;
    private final PropertyResolver propertyResolver;
    private final RenderConfiguration renderConfiguration;
    private final ValueConfiguration valueConfiguration;
    private final EnumerationListStrategy enumerationStrategy;

    public Environment(JtwigParser parser, Map<String, Object> parameters, ResourceResolver resourceResolver, FunctionResolver functionResolver, PropertyResolver propertyResolver, RenderConfiguration renderConfiguration, ValueConfiguration valueConfiguration, EnumerationListStrategy enumerationStrategy) {
        this.parser = parser;
        this.parameters = parameters;
        this.resourceResolver = resourceResolver;
        this.functionResolver = functionResolver;
        this.propertyResolver = propertyResolver;
        this.renderConfiguration = renderConfiguration;
        this.valueConfiguration = valueConfiguration;
        this.enumerationStrategy = enumerationStrategy;
    }

    public JtwigParser parser() {
        return parser;
    }

    public ResourceResolver resourceResolver() {
        return resourceResolver;
    }

    public FunctionResolver functionResolver() {
        return functionResolver;
    }

    public PropertyResolver propertyResolver() {
        return propertyResolver;
    }

    public ValueConfiguration valueConfiguration() {
        return valueConfiguration;
    }

    public RenderConfiguration renderConfiguration() {
        return renderConfiguration;
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
