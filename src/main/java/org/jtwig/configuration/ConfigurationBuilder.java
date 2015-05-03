package org.jtwig.configuration;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import org.jtwig.functions.impl.EscapeRawFunction;
import org.jtwig.functions.impl.JsonMapperFunction;
import org.jtwig.functions.impl.json.CompositeJsonMapperFactory;
import org.jtwig.functions.impl.json.Jackson2JsonMapperFactory;
import org.jtwig.functions.impl.json.JacksonJsonMapperFactory;
import org.jtwig.functions.impl.json.JsonMapperFactory;
import org.jtwig.parser.parboiled.node.AddonParser;
import org.jtwig.reflection.convert.Converter;
import org.jtwig.reflection.resolver.argument.ArgumentResolver;
import org.apache.commons.lang3.builder.Builder;
import org.jtwig.functions.SimpleFunction;
import org.jtwig.functions.reference.FunctionReference;
import org.jtwig.functions.resolver.FunctionResolver;
import org.jtwig.functions.resolver.FunctionResolverBuilder;
import org.jtwig.model.expression.lists.*;
import org.jtwig.parser.JtwigParser;
import org.jtwig.parser.JtwigParserBuilder;
import org.jtwig.parser.config.ParserConfigurationBuilder;
import org.jtwig.property.*;
import org.jtwig.resource.classpath.ResourceLoader;
import org.jtwig.resource.resolver.ClasspathResourceResolver;
import org.jtwig.resource.resolver.CompositeResourceResolver;
import org.jtwig.resource.resolver.ResourceResolver;
import org.jtwig.resource.util.RelativePathResolver;
import org.jtwig.util.ClasspathFinder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ConfigurationBuilder implements Builder<Configuration> {
    public static ConfigurationBuilder configuration() {
        return new ConfigurationBuilder();
    }

    private boolean defaults = true;
    private Collection<ResourceResolver> resourceResolvers = new ArrayList<>();
    private Collection<PropertyResolver> propertyResolvers = new ArrayList<>();
    private Collection<EnumerationListStrategy> enumerationListStrategies = new ArrayList<>();
    private ParserConfigurationBuilder parserConfigurationBuilder = ParserConfigurationBuilder.parserConfiguration();
    private FunctionResolverBuilder functionResolverBuilder = FunctionResolverBuilder.newBuilder();
    private Collection<JsonMapperFactory> jsonMapperFactory = new ArrayList<>();
    private Collection<Class<? extends AddonParser>> addOnParsers = new ArrayList<>();
    private Map<String, Object> parameters = new HashMap<>();

    private ConfigurationBuilder () {}

    public <T> ConfigurationBuilder withParameter (ConfigurationParameter<T> parameter, T value) {
        this.parameters.put(parameter.name(), value);
        return this;
    }

    public ConfigurationBuilder withParameter (String name, Object value) {
        this.parameters.put(name, value);
        return this;
    }

    public ConfigurationBuilder withJsonMapperFactory (JsonMapperFactory jsonMapperFactory) {
        this.jsonMapperFactory.add(jsonMapperFactory);
        return this;
    }

    public ConfigurationBuilder withResourceResolver (ResourceResolver resourceResolver) {
        this.resourceResolvers.add(resourceResolver);
        return this;
    }

    public ConfigurationBuilder withPropertyResolver (PropertyResolver propertyResolver) {
        this.propertyResolvers.add(propertyResolver);
        return this;
    }

    public ConfigurationBuilder overrideDefaults () {
        defaults = false;
        return this;
    }


    public ConfigurationBuilder withArgumentResolver (ArgumentResolver argumentResolver) {
        functionResolverBuilder.withArgumentResolver(argumentResolver);
        return this;
    }

    public ConfigurationBuilder withArgumentResolvers (Collection<ArgumentResolver> argumentResolver) {
        functionResolverBuilder.withArgumentResolvers(argumentResolver);
        return this;
    }

    public ConfigurationBuilder withConverter (Converter converter) {
        functionResolverBuilder.withConverter(converter);
        return this;
    }

    public ConfigurationBuilder withConverters (Collection<Converter> converter) {
        functionResolverBuilder.withConverters(converter);
        return this;
    }

    public ConfigurationBuilder include (Object bean) {
        functionResolverBuilder.include(bean);
        return this;
    }

    public ConfigurationBuilder include(FunctionReference reference) {
        functionResolverBuilder.include(reference);
        return this;
    }

    public ConfigurationBuilder include(SimpleFunction function) {
        functionResolverBuilder.include(function);
        return this;
    }

    public ConfigurationBuilder withAddOnParser (Class<? extends AddonParser> addOnParser) {
        addOnParsers.add(addOnParser);
        return this;
    }

    @Override
    public Configuration build() {
        Collection<PropertyResolver> propertyResolvers = new ArrayList<>();
        Collection<ResourceResolver> resourceResolvers = new ArrayList<>();

        if (defaults) {
            propertyResolvers.add(new FieldPropertyResolver(true));
            propertyResolvers.add(new MethodPropertyResolver(MethodPropertyResolver.exactlyEqual()));
            propertyResolvers.add(new MethodPropertyResolver(MethodPropertyResolver.prefixedEqual("get")));
            propertyResolvers.add(new MethodPropertyResolver(MethodPropertyResolver.prefixedEqual("is")));
            propertyResolvers.add(new MethodPropertyResolver(MethodPropertyResolver.prefixedEqual("has")));
            propertyResolvers.add(new MacroPropertyResolver());

            // Resource loaders
            resourceResolvers.add(new ClasspathResourceResolver(new ResourceLoader(getClass().getClassLoader()), new RelativePathResolver()));

            // enumeration
            enumerationListStrategies.add(new IntegerAscendingOrderEnumerationListStrategy());
            enumerationListStrategies.add(new IntegerDescendingOrderEnumerationListStrategy());
            enumerationListStrategies.add(new CharAscendingOrderEnumerationListStrategy());
            enumerationListStrategies.add(new CharDescendingOrderEnumerationListStrategy());

            // Json Mappers
            ClasspathFinder classpathFinder = new ClasspathFinder(getClass().getClassLoader());
            jsonMapperFactory.add(new Jackson2JsonMapperFactory(classpathFinder));
            jsonMapperFactory.add(new JacksonJsonMapperFactory(classpathFinder));

            // Functions
            functionResolverBuilder.include(new JsonMapperFunction());
            functionResolverBuilder.include(new EscapeRawFunction());
        }

        propertyResolvers.addAll(this.propertyResolvers);
        resourceResolvers.addAll(this.resourceResolvers);

        return new InternalConfiguration(
                JtwigParserBuilder.jtwigParser()
                        .withConfiguration(parserConfigurationBuilder.build())
                        .withAddOnParsers(addOnParsers)
                        .build(),
                new CompositeResourceResolver(resourceResolvers),
                functionResolverBuilder.build(),
                new CompositePropertyResolver(propertyResolvers),
                new CompositeJsonMapperFactory(jsonMapperFactory),
                new CompositeEnumerationListStrategy(
                        enumerationListStrategies
                ), parameters
        );
    }

    private static class InternalConfiguration implements Configuration {
        private final JtwigParser jtwigParser;
        private final ResourceResolver resourceResolver;
        private final FunctionResolver functionResolver;
        private final PropertyResolver propertyResolver;
        private final JsonMapperFactory jsonMapperFactory;
        private final EnumerationListStrategy enumerationListStrategy;
        private final Map<String, Object> parameters;

        public InternalConfiguration(JtwigParser jtwigParser, ResourceResolver resourceResolver, FunctionResolver functionResolver, PropertyResolver propertyResolver, JsonMapperFactory jsonMapperFactory, EnumerationListStrategy enumerationListStrategy, Map<String, Object> parameters) {
            this.jtwigParser = jtwigParser;
            this.resourceResolver = resourceResolver;
            this.functionResolver = functionResolver;
            this.propertyResolver = propertyResolver;
            this.jsonMapperFactory = jsonMapperFactory;
            this.enumerationListStrategy = enumerationListStrategy;
            this.parameters = parameters;
        }

        @Override
        public JtwigParser parser() {
            return jtwigParser;
        }

        @Override
        public ResourceResolver resourceResolver() {
            return resourceResolver;
        }

        @Override
        public FunctionResolver functionResolver() {
            return functionResolver;
        }

        @Override
        public PropertyResolver propertyResolver() {
            return propertyResolver;
        }

        @Override
        public JsonMapperFactory jsonMapper() {
            return jsonMapperFactory;
        }

        @Override
        public EnumerationListStrategy enumerationStrategy() {
            return enumerationListStrategy;
        }

        @Override
        public <T> T parameter(String name, T defaultValue) {
            return (T) Optional.fromNullable(parameters.get(name)).or(defaultValue);
        }

        @Override
        public <T> T parameter(ConfigurationParameter<T> configurationParameter) {
            return parameter(configurationParameter.name(), configurationParameter.defaultValue());
        }
    }
}
