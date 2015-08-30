package org.jtwig.environment;

import org.jtwig.context.RenderConfiguration;
import org.jtwig.extension.Extension;
import org.jtwig.functions.resolver.FunctionResolverConfiguration;
import org.jtwig.model.expression.lists.EnumerationListStrategyConfiguration;
import org.jtwig.parser.JtwigParserConfiguration;
import org.jtwig.property.PropertyResolverConfiguration;
import org.jtwig.resource.resolver.ResourceResolverConfiguration;
import org.jtwig.value.configuration.ValueConfiguration;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class EnvironmentConfiguration {
    private final ValueConfiguration valueConfiguration;
    private final ResourceResolverConfiguration resourceResolverConfiguration;
    private final FunctionResolverConfiguration functionResolverConfiguration;
    private final PropertyResolverConfiguration propertyResolverConfiguration;
    private final EnumerationListStrategyConfiguration enumerationListConfiguration;
    private final JtwigParserConfiguration jtwigParserConfiguration;
    private final RenderConfiguration renderConfiguration;
    private final Map<String, Object> parameters = new HashMap<>();
    private final Collection<? extends Extension> extensions;

    public EnvironmentConfiguration(ValueConfiguration valueConfiguration, ResourceResolverConfiguration resourceResolverConfiguration, FunctionResolverConfiguration functionResolverConfiguration, PropertyResolverConfiguration propertyResolverConfiguration, EnumerationListStrategyConfiguration enumerationListConfiguration, JtwigParserConfiguration jtwigParserConfiguration, RenderConfiguration renderConfiguration, Map<String, Object> parameters, Collection<? extends Extension> extensions) {
        this.valueConfiguration = valueConfiguration;
        this.resourceResolverConfiguration = resourceResolverConfiguration;
        this.functionResolverConfiguration = functionResolverConfiguration;
        this.propertyResolverConfiguration = propertyResolverConfiguration;
        this.enumerationListConfiguration = enumerationListConfiguration;
        this.jtwigParserConfiguration = jtwigParserConfiguration;
        this.renderConfiguration = renderConfiguration;
        this.extensions = extensions;
        this.parameters.putAll(parameters);
    }

    public ValueConfiguration getValueConfiguration() {
        return valueConfiguration;
    }

    public ResourceResolverConfiguration getResourceResolverConfiguration() {
        return resourceResolverConfiguration;
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
