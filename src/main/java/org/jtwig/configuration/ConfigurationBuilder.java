package org.jtwig.configuration;

import org.apache.commons.lang3.builder.Builder;
import org.jtwig.content.json.CompositeJsonMapperFactory;
import org.jtwig.content.json.Jackson2JsonMapperFactory;
import org.jtwig.content.json.JacksonJsonMapperFactory;
import org.jtwig.content.json.JsonMapperFactory;
import org.jtwig.content.spaces.HtmlSpaceRemover;
import org.jtwig.content.spaces.SpaceRemover;
import org.jtwig.context.model.EscapeMode;
import org.jtwig.functions.SimpleFunction;
import org.jtwig.functions.convert.ObjectToIntegerConverter;
import org.jtwig.functions.impl.*;
import org.jtwig.functions.reference.FunctionReference;
import org.jtwig.functions.resolver.FunctionResolverBuilder;
import org.jtwig.model.expression.lists.*;
import org.jtwig.parser.JtwigParserBuilder;
import org.jtwig.parser.config.ParserConfigurationBuilder;
import org.jtwig.parser.parboiled.node.AddonParser;
import org.jtwig.property.*;
import org.jtwig.reflection.convert.Converter;
import org.jtwig.reflection.resolver.argument.ArgumentResolver;
import org.jtwig.resource.classpath.ResourceLoader;
import org.jtwig.resource.resolver.ClasspathResourceResolver;
import org.jtwig.resource.resolver.CompositeResourceResolver;
import org.jtwig.resource.resolver.ResourceResolver;
import org.jtwig.resource.util.RelativePathResolver;
import org.jtwig.util.ClasspathFinder;

import java.math.MathContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ConfigurationBuilder implements Builder<Configuration> {

    public static ConfigurationBuilder configuration() {
        return new ConfigurationBuilder();
    }

    private boolean strictMode = false;
    private SpaceRemover spaceRemover = new HtmlSpaceRemover();
    private MathContext mathContext = MathContext.DECIMAL128;
    private EscapeMode escapeMode = EscapeMode.NONE;
    private Collection<ResourceResolver> resourceResolvers = new ArrayList<>();
    private Collection<PropertyResolver> propertyResolvers = new ArrayList<>();
    private Collection<EnumerationListStrategy> enumerationListStrategies = new ArrayList<>();
    private ParserConfigurationBuilder parserConfigurationBuilder = ParserConfigurationBuilder.parserConfiguration();
    private FunctionResolverBuilder functionResolverBuilder = FunctionResolverBuilder.newBuilder();
    private Collection<JsonMapperFactory> jsonMapperFactory = new ArrayList<>();
    private Collection<Class<? extends AddonParser>> addOnParsers = new ArrayList<>();
    private Map<String, Object> parameters = new HashMap<>();

    private ConfigurationBuilder() {
    }

    public ConfigurationBuilder withParameter(String name, Object value) {
        this.parameters.put(name, value);
        return this;
    }

    public ConfigurationBuilder withJsonMapperFactory(JsonMapperFactory jsonMapperFactory) {
        this.jsonMapperFactory.add(jsonMapperFactory);
        return this;
    }

    public ConfigurationBuilder withResourceResolver(ResourceResolver resourceResolver) {
        this.resourceResolvers.add(resourceResolver);
        return this;
    }

    public ConfigurationBuilder withPropertyResolver(PropertyResolver propertyResolver) {
        this.propertyResolvers.add(propertyResolver);
        return this;
    }

    public ConfigurationBuilder withArgumentResolver(ArgumentResolver argumentResolver) {
        functionResolverBuilder.withArgumentResolver(argumentResolver);
        return this;
    }

    public ConfigurationBuilder withArgumentResolvers(Collection<ArgumentResolver> argumentResolver) {
        functionResolverBuilder.withArgumentResolvers(argumentResolver);
        return this;
    }

    public ConfigurationBuilder withConverter(Converter converter) {
        functionResolverBuilder.withConverter(converter);
        return this;
    }

    public ConfigurationBuilder withConverters(Collection<Converter> converter) {
        functionResolverBuilder.withConverters(converter);
        return this;
    }

    public ConfigurationBuilder include(Object bean) {
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

    public ConfigurationBuilder withAddOnParser(Class<? extends AddonParser> addOnParser) {
        addOnParsers.add(addOnParser);
        return this;
    }

    public ConfigurationBuilder withStrictMode(boolean strictMode) {
        this.strictMode = strictMode;
        return this;
    }

    public ConfigurationBuilder withSpaceRemover(SpaceRemover spaceRemover) {
        this.spaceRemover = spaceRemover;
        return this;
    }

    public ConfigurationBuilder withMathContext(MathContext mathContext) {
        this.mathContext = mathContext;
        return this;
    }

    public ConfigurationBuilder withInitialEscapeMode(EscapeMode escapeMode) {
        this.escapeMode = escapeMode;
        return this;
    }

    @Override
    public Configuration build() {
        Collection<PropertyResolver> propertyResolvers = new ArrayList<>();
        Collection<ResourceResolver> resourceResolvers = new ArrayList<>();

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
        functionResolverBuilder.include(new BooleanFunctions());
        functionResolverBuilder.include(new ConstantFunction(classpathFinder));
        functionResolverBuilder.include(new StringFunctions());

        // converters
        functionResolverBuilder.withConverter(new ObjectToIntegerConverter());

        propertyResolvers.addAll(this.propertyResolvers);
        resourceResolvers.addAll(this.resourceResolvers);

        return new ParameterizedConfiguration(
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
                ),
                parameters,
                strictMode,
                spaceRemover,
                mathContext,
                escapeMode
        );
    }

}
