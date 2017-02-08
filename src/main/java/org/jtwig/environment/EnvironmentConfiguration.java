package org.jtwig.environment;

import org.jtwig.environment.initializer.EnvironmentInitializer;
import org.jtwig.escape.config.EscapeEngineConfiguration;
import org.jtwig.extension.Extension;
import org.jtwig.functions.JtwigFunction;
import org.jtwig.parser.config.JtwigParserConfiguration;
import org.jtwig.property.configuration.PropertyResolverConfiguration;
import org.jtwig.render.config.RenderConfiguration;
import org.jtwig.render.expression.calculator.enumerated.EnumerationListStrategy;
import org.jtwig.resource.config.ResourceConfiguration;
import org.jtwig.value.config.ValueConfiguration;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnvironmentConfiguration {
    private final ResourceConfiguration resourceConfiguration;
    private final JtwigParserConfiguration jtwigParserConfiguration;
    private final RenderConfiguration renderConfiguration;
    private final ValueConfiguration valueConfiguration;
    private final EscapeEngineConfiguration escapeConfiguration;
    private final PropertyResolverConfiguration propertyResolverConfiguration;
    private final Collection<EnumerationListStrategy> enumerationStrategies;
    private final Collection<JtwigFunction> functions;
    private final Map<String, Object> parameters = new HashMap<>();
    private final Collection<Extension> extensions;
    private final List<EnvironmentInitializer> initializers;

    public EnvironmentConfiguration(ResourceConfiguration resourceConfiguration, Collection<EnumerationListStrategy> enumerationStrategies, JtwigParserConfiguration jtwigParserConfiguration, ValueConfiguration valueConfiguration, RenderConfiguration renderConfiguration, EscapeEngineConfiguration escapeConfiguration, PropertyResolverConfiguration propertyResolverConfiguration, Collection<JtwigFunction> functions, Map<String, Object> parameters, Collection<Extension> extensions, List<EnvironmentInitializer> initializers) {
        this.resourceConfiguration = resourceConfiguration;
        this.escapeConfiguration = escapeConfiguration;
        this.enumerationStrategies = enumerationStrategies;
        this.jtwigParserConfiguration = jtwigParserConfiguration;
        this.valueConfiguration = valueConfiguration;
        this.renderConfiguration = renderConfiguration;
        this.propertyResolverConfiguration = propertyResolverConfiguration;
        this.functions = functions;
        this.extensions = extensions;
        this.initializers = initializers;
        this.parameters.putAll(parameters);
    }

    public ResourceConfiguration getResourceConfiguration() {
        return resourceConfiguration;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public Collection<JtwigFunction> getFunctions() {
        return functions;
    }

    public PropertyResolverConfiguration getPropertyResolverConfiguration() {
        return propertyResolverConfiguration;
    }

    public Collection<EnumerationListStrategy> getEnumerationStrategies() {
        return enumerationStrategies;
    }

    public ValueConfiguration getValueConfiguration() {
        return valueConfiguration;
    }

    public JtwigParserConfiguration getJtwigParserConfiguration() {
        return jtwigParserConfiguration;
    }

    public RenderConfiguration getRenderConfiguration() {
        return renderConfiguration;
    }

    public Collection<Extension> getExtensions() {
        return extensions;
    }

    public EscapeEngineConfiguration getEscapeConfiguration() {
        return escapeConfiguration;
    }

    public List<EnvironmentInitializer> getInitializers() {
        return initializers;
    }
}
