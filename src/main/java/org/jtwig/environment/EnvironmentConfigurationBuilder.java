package org.jtwig.environment;

import org.apache.commons.lang3.builder.Builder;
import org.jtwig.addon.AddonProvider;
import org.jtwig.environment.and.*;
import org.jtwig.value.configuration.CompatibleModeValueConfiguration;
import org.jtwig.value.configuration.ValueConfiguration;

import java.util.HashMap;
import java.util.Map;

public class EnvironmentConfigurationBuilder implements Builder<EnvironmentConfiguration> {
    public static EnvironmentConfigurationBuilder configuration () {
        return new EnvironmentConfigurationBuilder(new DefaultEnvironmentConfiguration());
    }

    private Map<String, Object> parameters = new HashMap<>();
    private ValueConfiguration valueConfiguration;
    private final AndFunctionResolverConfigurationBuilder functionResolverConfiguration;
    private final AndRenderConfigurationBuilder renderConfiguration;
    private final AndJtwigParserConfigurationBuilder jtwigParserConfigurationBuilder;
    private final AndResourceResolverConfigurationBuilder resourceResolverConfigurationBuilder;
    private final AndPropertyResolverConfigurationBuilder propertyResolverConfigurationBuilder;
    private final AndJsonMapperProviderConfigurationBuilder jsonMapperProviderConfigurationBuilder;
    private final AndEnumerationListStrategyConfigurationBuilder enumerationListStrategyConfigurationBuilder;

    public EnvironmentConfigurationBuilder () {
        valueConfiguration = new CompatibleModeValueConfiguration();
        functionResolverConfiguration  = new AndFunctionResolverConfigurationBuilder(this);
        renderConfiguration = new AndRenderConfigurationBuilder(this);
        jtwigParserConfigurationBuilder = new AndJtwigParserConfigurationBuilder(this);
        resourceResolverConfigurationBuilder = new AndResourceResolverConfigurationBuilder(this);
        propertyResolverConfigurationBuilder = new AndPropertyResolverConfigurationBuilder(this);
        jsonMapperProviderConfigurationBuilder = new AndJsonMapperProviderConfigurationBuilder(this);
        enumerationListStrategyConfigurationBuilder = new AndEnumerationListStrategyConfigurationBuilder(this);
    }
    public EnvironmentConfigurationBuilder (EnvironmentConfiguration prototype) {
        functionResolverConfiguration  = new AndFunctionResolverConfigurationBuilder(prototype.getFunctionResolverConfiguration(), this);
        renderConfiguration = new AndRenderConfigurationBuilder(prototype.getRenderConfiguration(), this);
        jtwigParserConfigurationBuilder = new AndJtwigParserConfigurationBuilder(prototype.getJtwigParserConfiguration(), this);
        resourceResolverConfigurationBuilder = new AndResourceResolverConfigurationBuilder(prototype.getResourceResolverConfiguration(), this);
        valueConfiguration = prototype.getValueConfiguration();
        propertyResolverConfigurationBuilder = new AndPropertyResolverConfigurationBuilder(prototype.getPropertyResolverConfiguration(), this);
        jsonMapperProviderConfigurationBuilder = new AndJsonMapperProviderConfigurationBuilder(prototype.getJsonMapperProviderConfiguration(), this);
        enumerationListStrategyConfigurationBuilder = new AndEnumerationListStrategyConfigurationBuilder(prototype.getEnumerationListConfiguration(), this);
    }

    @Override
    public EnvironmentConfiguration build() {
        return new EnvironmentConfiguration(
                valueConfiguration,
                resourceResolverConfigurationBuilder.build(),
                functionResolverConfiguration.build(),
                propertyResolverConfigurationBuilder.build(),
                jsonMapperProviderConfigurationBuilder.build(),
                enumerationListStrategyConfigurationBuilder.build(),
                jtwigParserConfigurationBuilder.build(),
                renderConfiguration.build(),
                parameters
        );
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

    public AndResourceResolverConfigurationBuilder resources() {
        return resourceResolverConfigurationBuilder;
    }

    public AndPropertyResolverConfigurationBuilder propertyResolver() {
        return propertyResolverConfigurationBuilder;
    }

    public AndJsonMapperProviderConfigurationBuilder jsonMapper() {
        return jsonMapperProviderConfigurationBuilder;
    }

    public AndEnumerationListStrategyConfigurationBuilder listEnumeration() {
        return enumerationListStrategyConfigurationBuilder;
    }

    public EnvironmentConfigurationBuilder withValueConfiguration (ValueConfiguration configuration) {
        this.valueConfiguration = configuration;
        return this;
    }

    public <T> EnvironmentConfigurationBuilder withParameter (String name, T value) {
        this.parameters.put(name, value);
        return this;
    }

    public <T extends AddonProvider> EnvironmentConfigurationBuilder withAddon (T addonProvider) {
        addonProvider.configure(this);
        return this;
    }
}
