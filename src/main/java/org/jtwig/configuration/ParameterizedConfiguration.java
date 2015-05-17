package org.jtwig.configuration;

import com.google.common.base.Optional;
import org.jtwig.content.json.JsonMapperFactory;
import org.jtwig.content.spaces.SpaceRemover;
import org.jtwig.context.model.EscapeMode;
import org.jtwig.functions.resolver.FunctionResolver;
import org.jtwig.model.expression.lists.EnumerationListStrategy;
import org.jtwig.parser.JtwigParser;
import org.jtwig.property.PropertyResolver;
import org.jtwig.resource.resolver.ResourceResolver;
import org.jtwig.value.configuration.ValueConfiguration;

import java.math.MathContext;
import java.nio.charset.Charset;
import java.util.Map;

public class ParameterizedConfiguration implements Configuration {
    private final JtwigParser jtwigParser;
    private final ResourceResolver resourceResolver;
    private final FunctionResolver functionResolver;
    private final PropertyResolver propertyResolver;
    private final JsonMapperFactory jsonMapperFactory;
    private final EnumerationListStrategy enumerationListStrategy;
    private final Map<String, Object> parameters;
    private final boolean strictMode;
    private final SpaceRemover spaceRemover;
    private final MathContext mathContext;
    private final EscapeMode initialEscapeMode;
    private final ValueConfiguration valueConfiguration;
    private final Charset charset;

    public ParameterizedConfiguration(JtwigParser jtwigParser, ResourceResolver resourceResolver, FunctionResolver functionResolver, PropertyResolver propertyResolver, JsonMapperFactory jsonMapperFactory, EnumerationListStrategy enumerationListStrategy, Map<String, Object> parameters, boolean strictMode, SpaceRemover spaceRemover, MathContext mathContext, EscapeMode escapeMode, ValueConfiguration valueConfiguration, Charset charset) {
        this.jtwigParser = jtwigParser;
        this.resourceResolver = resourceResolver;
        this.functionResolver = functionResolver;
        this.propertyResolver = propertyResolver;
        this.jsonMapperFactory = jsonMapperFactory;
        this.enumerationListStrategy = enumerationListStrategy;
        this.parameters = parameters;
        this.strictMode = strictMode;
        this.spaceRemover = spaceRemover;
        this.mathContext = mathContext;
        this.initialEscapeMode = escapeMode;
        this.valueConfiguration = valueConfiguration;
        this.charset = charset;
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
    public boolean strictMode() {
        return strictMode;
    }

    @Override
    public Charset outputCharset() {
        return charset;
    }

    @Override
    public SpaceRemover spaceRemover() {
        return spaceRemover;
    }

    @Override
    public MathContext mathContext() {
        return mathContext;
    }

    @Override
    public EscapeMode initialEscapeMode() {
        return initialEscapeMode;
    }

    @Override
    public ValueConfiguration valueConfiguration() {
        return valueConfiguration;
    }

    @Override
    public <T> T parameter(String name, T defaultValue) {
        return (T) Optional.fromNullable(parameters.get(name)).or(defaultValue);
    }
}
