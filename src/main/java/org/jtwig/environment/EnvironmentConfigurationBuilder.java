package org.jtwig.environment;

import org.apache.commons.lang3.builder.Builder;
import org.jtwig.environment.and.*;
import org.jtwig.extension.Extension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class EnvironmentConfigurationBuilder implements Builder<EnvironmentConfiguration> {
    public static EnvironmentConfigurationBuilder configuration () {
        return new EnvironmentConfigurationBuilder(new DefaultEnvironmentConfiguration());
    }

    private final Map<String, Object> parameters = new HashMap<>();
    private final Collection<Extension> extensions;
    private final AndFunctionResolverConfigurationBuilder functionResolverConfiguration;
    private final AndRenderConfigurationBuilder renderConfiguration;
    private final AndJtwigParserConfigurationBuilder jtwigParserConfigurationBuilder;
    private final AndResourceConfigurationBuilder resourceConfigurationBuilder;
    private final AndPropertyResolverConfigurationBuilder propertyResolverConfigurationBuilder;
    private final AndEnumerationListStrategyConfigurationBuilder enumerationListStrategyConfigurationBuilder;
    private final AndValueConfigurationBuilder valueConfigurationBuilder;

    public EnvironmentConfigurationBuilder () {
        functionResolverConfiguration  = new AndFunctionResolverConfigurationBuilder(this);
        renderConfiguration = new AndRenderConfigurationBuilder(this);
        jtwigParserConfigurationBuilder = new AndJtwigParserConfigurationBuilder(this);
        resourceConfigurationBuilder = new AndResourceConfigurationBuilder(this);
        propertyResolverConfigurationBuilder = new AndPropertyResolverConfigurationBuilder(this);
        enumerationListStrategyConfigurationBuilder = new AndEnumerationListStrategyConfigurationBuilder(this);
        valueConfigurationBuilder = new AndValueConfigurationBuilder(this);
        extensions = new ArrayList<>();
    }
    public EnvironmentConfigurationBuilder (EnvironmentConfiguration prototype) {
        functionResolverConfiguration  = new AndFunctionResolverConfigurationBuilder(prototype.getFunctionResolverConfiguration(), this);
        renderConfiguration = new AndRenderConfigurationBuilder(prototype.getRenderConfiguration(), this);
        jtwigParserConfigurationBuilder = new AndJtwigParserConfigurationBuilder(prototype.getJtwigParserConfiguration(), this);
        resourceConfigurationBuilder = new AndResourceConfigurationBuilder(prototype.getResourceConfiguration(), this);
        propertyResolverConfigurationBuilder = new AndPropertyResolverConfigurationBuilder(prototype.getPropertyResolverConfiguration(), this);
        enumerationListStrategyConfigurationBuilder = new AndEnumerationListStrategyConfigurationBuilder(prototype.getEnumerationListConfiguration(), this);
        valueConfigurationBuilder = new AndValueConfigurationBuilder(prototype.getValueConfiguration(), this);
        extensions = new ArrayList<>();
        extensions.addAll(prototype.getExtensions());
    }

    @Override
    public EnvironmentConfiguration build() {
        return new EnvironmentConfiguration(
                valueConfigurationBuilder.build(),
                resourceConfigurationBuilder.build(),
                functionResolverConfiguration.build(),
                propertyResolverConfigurationBuilder.build(),
                enumerationListStrategyConfigurationBuilder.build(),
                jtwigParserConfigurationBuilder.build(),
                renderConfiguration.build(),
                parameters,
                extensions);
    }

    public AndJtwigParserConfigurationBuilder parser () {
        return jtwigParserConfigurationBuilder;
    }

    public AndFunctionResolverConfigurationBuilder functions () {
        return functionResolverConfiguration;
    }

    public AndRenderConfigurationBuilder render () {
        return renderConfiguration;
    }

    public AndResourceConfigurationBuilder resources() {
        return resourceConfigurationBuilder;
    }

    public AndPropertyResolverConfigurationBuilder propertyResolver() {
        return propertyResolverConfigurationBuilder;
    }

    public AndEnumerationListStrategyConfigurationBuilder listEnumeration() {
        return enumerationListStrategyConfigurationBuilder;
    }

    public AndValueConfigurationBuilder value () {
        return valueConfigurationBuilder;
    }

    public <T> EnvironmentConfigurationBuilder withParameter (String name, T value) {
        this.parameters.put(name, value);
        return this;
    }

    public <T extends Extension> EnvironmentConfigurationBuilder withExtension(T extension) {
        extensions.add(extension);
        return this;
    }
}
