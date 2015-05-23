package org.jtwig.environment;

import com.google.common.base.Supplier;
import org.apache.commons.lang3.builder.Builder;
import org.jtwig.content.json.JsonMapperProviderConfigurationBuilder;
import org.jtwig.context.RenderConfigurationBuilder;
import org.jtwig.functions.SimpleFunction;
import org.jtwig.functions.resolver.FunctionResolverConfigurationBuilder;
import org.jtwig.i18n.MessageResolverConfigurationBuilder;
import org.jtwig.i18n.source.LocalizedMessageSource;
import org.jtwig.i18n.source.message.MessageSource;
import org.jtwig.model.expression.lists.EnumerationListStrategyConfigurationBuilder;
import org.jtwig.parser.JtwigParserConfigurationBuilder;
import org.jtwig.parser.addon.AddonParserProvider;
import org.jtwig.property.PropertyResolverConfigurationBuilder;
import org.jtwig.resource.resolver.ResourceResolver;
import org.jtwig.resource.resolver.ResourceResolverConfigurationBuilder;
import org.jtwig.value.configuration.CompatibleModeValueConfiguration;
import org.jtwig.value.configuration.ValueConfiguration;

import java.nio.charset.Charset;
import java.util.*;

public class EnvironmentConfigurationBuilder implements Builder<EnvironmentConfiguration> {
    public static EnvironmentConfigurationBuilder configuration () {
        return new EnvironmentConfigurationBuilder(new DefaultEnvironmentConfiguration());
    }

    private Map<String, Object> parameters = new HashMap<>();
    private final ValueConfiguration valueConfiguration;
    private final FunctionResolverConfigurationBuilder functionResolverConfiguration;
    private final RenderConfigurationBuilder renderConfiguration;
    private final JtwigParserConfigurationBuilder jtwigParserConfigurationBuilder;
    private final ResourceResolverConfigurationBuilder resourceResolverConfigurationBuilder;
    private final PropertyResolverConfigurationBuilder propertyResolverConfigurationBuilder;
    private final JsonMapperProviderConfigurationBuilder jsonMapperProviderConfigurationBuilder;
    private final EnumerationListStrategyConfigurationBuilder enumerationListStrategyConfigurationBuilder;
    private final MessageResolverConfigurationBuilder messageResolverConfigurationBuilder;

    public EnvironmentConfigurationBuilder () {
        valueConfiguration = new CompatibleModeValueConfiguration();
        functionResolverConfiguration  = new FunctionResolverConfigurationBuilder();
        renderConfiguration = new RenderConfigurationBuilder();
        jtwigParserConfigurationBuilder = new JtwigParserConfigurationBuilder();
        resourceResolverConfigurationBuilder = new ResourceResolverConfigurationBuilder();
        propertyResolverConfigurationBuilder = new PropertyResolverConfigurationBuilder();
        jsonMapperProviderConfigurationBuilder = new JsonMapperProviderConfigurationBuilder();
        enumerationListStrategyConfigurationBuilder = new EnumerationListStrategyConfigurationBuilder();
        messageResolverConfigurationBuilder = new MessageResolverConfigurationBuilder();
    }
    public EnvironmentConfigurationBuilder (EnvironmentConfiguration prototype) {
        functionResolverConfiguration  = new FunctionResolverConfigurationBuilder(prototype.getFunctionResolverConfiguration());
        renderConfiguration = new RenderConfigurationBuilder(prototype.getRenderConfiguration());
        jtwigParserConfigurationBuilder = new JtwigParserConfigurationBuilder(prototype.getJtwigParserConfiguration());
        resourceResolverConfigurationBuilder = new ResourceResolverConfigurationBuilder(prototype.getResourceResolverConfiguration());
        valueConfiguration = prototype.getValueConfiguration();
        propertyResolverConfigurationBuilder = new PropertyResolverConfigurationBuilder(prototype.getPropertyResolverConfiguration());
        jsonMapperProviderConfigurationBuilder = new JsonMapperProviderConfigurationBuilder(prototype.getJsonMapperProviderConfiguration());
        enumerationListStrategyConfigurationBuilder = new EnumerationListStrategyConfigurationBuilder(prototype.getEnumerationListConfiguration());
        messageResolverConfigurationBuilder = new MessageResolverConfigurationBuilder(prototype.getMessageResolverConfiguration());
    }

    @Override
    public EnvironmentConfiguration build() {
        return new EnvironmentConfiguration(
                valueConfiguration,
                resourceResolverConfigurationBuilder.build(),
                functionResolverConfiguration.build(),
                propertyResolverConfigurationBuilder.build(),
                messageResolverConfigurationBuilder.build(),
                jsonMapperProviderConfigurationBuilder.build(),
                enumerationListStrategyConfigurationBuilder.build(),
                jtwigParserConfigurationBuilder.build(),
                renderConfiguration.build(),
                parameters
        );
    }

    public EnvironmentConfigurationBuilder include(SimpleFunction simpleFunction) {
        functionResolverConfiguration.withSimpleFunctions(Collections.singletonList(simpleFunction));
        return this;
    }

    public EnvironmentConfigurationBuilder withStrictMode(boolean strictMode) {
        renderConfiguration.withStrictMode(strictMode);
        return this;
    }

    public EnvironmentConfigurationBuilder withInputCharset(Charset charset) {
        jtwigParserConfigurationBuilder.withInputCharset(charset);
        return this;
    }

    public EnvironmentConfigurationBuilder withOutputCharset(Charset charset) {
        renderConfiguration.withOutputCharset(charset);
        return this;
    }

    public EnvironmentConfigurationBuilder withResourceResolver(ResourceResolver resourceResolver) {
        resourceResolverConfigurationBuilder.withResourceResolvers(Collections.singletonList(resourceResolver));
        return this;
    }

    public EnvironmentConfigurationBuilder withAddOnParser(AddonParserProvider provider) {
        jtwigParserConfigurationBuilder.withAddonParserProviders(Collections.singletonList(provider));
        return this;
    }


    public EnvironmentConfigurationBuilder withMessageSource(LocalizedMessageSource messageSource) {
        this.messageResolverConfigurationBuilder.withMessageSource(messageSource);
        return this;
    }

    public EnvironmentConfigurationBuilder withMessageSource(Locale locale, MessageSource messageSource) {
        this.messageResolverConfigurationBuilder.withMessageSource(locale, messageSource);
        return this;
    }

    public EnvironmentConfigurationBuilder withLocaleSupplier(Supplier<Locale> localeSupplier) {
        this.renderConfiguration.withLocaleSupplier(localeSupplier);
        return this;
    }
}
