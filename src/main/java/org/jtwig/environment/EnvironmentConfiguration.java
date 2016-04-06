package org.jtwig.environment;

import org.jtwig.extension.Extension;
import org.jtwig.functions.JtwigFunction;
import org.jtwig.parser.config.JtwigParserConfiguration;
import org.jtwig.property.PropertyResolver;
import org.jtwig.render.config.RenderConfiguration;
import org.jtwig.render.expression.calculator.enumerated.EnumerationListStrategy;
import org.jtwig.resource.config.ResourceConfiguration;
import org.jtwig.value.config.ValueConfiguration;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class EnvironmentConfiguration {
    private final ResourceConfiguration resourceConfiguration;
    private final JtwigParserConfiguration jtwigParserConfiguration;
    private final RenderConfiguration renderConfiguration;
    private final ValueConfiguration valueConfiguration;
    private final Collection<EnumerationListStrategy> enumerationStrategies;
    private final Collection<PropertyResolver> propertyResolvers;
    private final Collection<JtwigFunction> functions;
    private final Map<String, Object> parameters = new HashMap<>();
    private final Collection<? extends Extension> extensions;

    public EnvironmentConfiguration(ResourceConfiguration resourceConfiguration, Collection<EnumerationListStrategy> enumerationStrategies, JtwigParserConfiguration jtwigParserConfiguration, ValueConfiguration valueConfiguration, RenderConfiguration renderConfiguration, Collection<PropertyResolver> propertyResolvers, Collection<JtwigFunction> functions, Map<String, Object> parameters, Collection<? extends Extension> extensions) {
        this.resourceConfiguration = resourceConfiguration;
        this.propertyResolvers = propertyResolvers;
        this.enumerationStrategies = enumerationStrategies;
        this.jtwigParserConfiguration = jtwigParserConfiguration;
        this.valueConfiguration = valueConfiguration;
        this.renderConfiguration = renderConfiguration;
        this.functions = functions;
        this.extensions = extensions;
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

    public Collection<PropertyResolver> getPropertyResolvers() {
        return propertyResolvers;
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

    public Collection<? extends Extension> getExtensions() {
        return extensions;
    }
}
