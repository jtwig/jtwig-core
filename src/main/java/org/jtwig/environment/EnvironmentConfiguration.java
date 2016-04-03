package org.jtwig.environment;

import org.jtwig.extension.Extension;
import org.jtwig.functions.resolver.FunctionResolverConfiguration;
import org.jtwig.parser.JtwigParserConfiguration;
import org.jtwig.property.PropertyResolverConfiguration;
import org.jtwig.render.config.RenderConfiguration;
import org.jtwig.render.expression.calculator.enumerated.config.EnumerationListStrategyConfiguration;
import org.jtwig.resource.config.ResourceConfiguration;
import org.jtwig.value.config.ValueConfiguration;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class EnvironmentConfiguration {
    private final ResourceConfiguration resourceConfiguration;
    private final FunctionResolverConfiguration functionResolverConfiguration;
    private final PropertyResolverConfiguration propertyResolverConfiguration;
    private final EnumerationListStrategyConfiguration enumerationListConfiguration;
    private final JtwigParserConfiguration jtwigParserConfiguration;
    private final RenderConfiguration renderConfiguration;
    private final Map<String, Object> parameters = new HashMap<>();
    private final Collection<? extends Extension> extensions;
    private final ValueConfiguration valueConfiguration;

    public EnvironmentConfiguration(ResourceConfiguration resourceConfiguration, FunctionResolverConfiguration functionResolverConfiguration, PropertyResolverConfiguration propertyResolverConfiguration, EnumerationListStrategyConfiguration enumerationListConfiguration, JtwigParserConfiguration jtwigParserConfiguration, ValueConfiguration valueConfiguration, RenderConfiguration renderConfiguration, Map<String, Object> parameters, Collection<? extends Extension> extensions) {
        this.resourceConfiguration = resourceConfiguration;
        this.functionResolverConfiguration = functionResolverConfiguration;
        this.propertyResolverConfiguration = propertyResolverConfiguration;
        this.enumerationListConfiguration = enumerationListConfiguration;
        this.jtwigParserConfiguration = jtwigParserConfiguration;
        this.valueConfiguration = valueConfiguration;
        this.renderConfiguration = renderConfiguration;
        this.extensions = extensions;
        this.parameters.putAll(parameters);
    }

    public ResourceConfiguration getResourceConfiguration() {
        return resourceConfiguration;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public FunctionResolverConfiguration getFunctionResolverConfiguration() {
        return functionResolverConfiguration;
    }

    public PropertyResolverConfiguration getPropertyResolverConfiguration() {
        return propertyResolverConfiguration;
    }

    public EnumerationListStrategyConfiguration getEnumerationListConfiguration() {
        return enumerationListConfiguration;
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
