package org.jtwig.environment;

import org.apache.commons.lang3.builder.Builder;
import org.jtwig.environment.and.AndJtwigParserConfigurationBuilder;
import org.jtwig.environment.and.AndRenderConfigurationBuilder;
import org.jtwig.environment.and.AndResourceConfigurationBuilder;
import org.jtwig.environment.and.AndValueConfigurationBuilder;
import org.jtwig.extension.Extension;
import org.jtwig.functions.JtwigFunction;
import org.jtwig.property.PropertyResolver;
import org.jtwig.render.expression.calculator.enumerated.EnumerationListStrategy;
import org.jtwig.util.builder.ListBuilder;
import org.jtwig.util.builder.MapBuilder;

public class EnvironmentConfigurationBuilder implements Builder<EnvironmentConfiguration> {
    public static EnvironmentConfigurationBuilder configuration () {
        return new EnvironmentConfigurationBuilder(new DefaultEnvironmentConfiguration());
    }

    private final MapBuilder<EnvironmentConfigurationBuilder, String, Object> parameters;
    private final ListBuilder<EnvironmentConfigurationBuilder, Extension> extensions;
    private final ListBuilder<EnvironmentConfigurationBuilder, JtwigFunction> functions;
    private final ListBuilder<EnvironmentConfigurationBuilder, PropertyResolver> propertyResolvers;
    private final ListBuilder<EnvironmentConfigurationBuilder, EnumerationListStrategy> enumerationListStrategies;
    private final AndRenderConfigurationBuilder renderConfiguration;
    private final AndJtwigParserConfigurationBuilder jtwigParserConfigurationBuilder;
    private final AndResourceConfigurationBuilder resourceConfigurationBuilder;
    private final AndValueConfigurationBuilder valueConfigurationBuilder;

    public EnvironmentConfigurationBuilder () {
        functions  = new ListBuilder<>(this);
        renderConfiguration = new AndRenderConfigurationBuilder(this);
        jtwigParserConfigurationBuilder = new AndJtwigParserConfigurationBuilder(this);
        resourceConfigurationBuilder = new AndResourceConfigurationBuilder(this);
        propertyResolvers = new ListBuilder<>(this);
        enumerationListStrategies = new ListBuilder<>(this);
        valueConfigurationBuilder = new AndValueConfigurationBuilder(this);
        extensions = new ListBuilder<>(this);
        parameters = new MapBuilder<>(this);
    }
    public EnvironmentConfigurationBuilder (EnvironmentConfiguration prototype) {
        functions = new ListBuilder<>(this, prototype.getFunctions());
        renderConfiguration = new AndRenderConfigurationBuilder(prototype.getRenderConfiguration(), this);
        jtwigParserConfigurationBuilder = new AndJtwigParserConfigurationBuilder(prototype.getJtwigParserConfiguration(), this);
        resourceConfigurationBuilder = new AndResourceConfigurationBuilder(prototype.getResourceConfiguration(), this);
        propertyResolvers = new ListBuilder<>(this, prototype.getPropertyResolvers());
        enumerationListStrategies = new ListBuilder<>(this, prototype.getEnumerationStrategies());
        valueConfigurationBuilder = new AndValueConfigurationBuilder(prototype.getValueConfiguration(), this);
        extensions = new ListBuilder<>(this, prototype.getExtensions());
        parameters = new MapBuilder<>(this, prototype.getParameters());
    }

    @Override
    public EnvironmentConfiguration build() {
        return new EnvironmentConfiguration(
                resourceConfigurationBuilder.build(),
                enumerationListStrategies.build(),
                jtwigParserConfigurationBuilder.build(),
                valueConfigurationBuilder.build(),
                renderConfiguration.build(),
                propertyResolvers.build(),
                functions.build(),
                parameters.build(),
                extensions.build());
    }

    public AndJtwigParserConfigurationBuilder parser () {
        return jtwigParserConfigurationBuilder;
    }

    public ListBuilder<EnvironmentConfigurationBuilder, JtwigFunction> functions () {
        return functions;
    }

    public AndRenderConfigurationBuilder render () {
        return renderConfiguration;
    }

    public AndResourceConfigurationBuilder resources() {
        return resourceConfigurationBuilder;
    }

    public ListBuilder<EnvironmentConfigurationBuilder, PropertyResolver> propertyResolvers() {
        return propertyResolvers;
    }

    public AndValueConfigurationBuilder value () { return valueConfigurationBuilder; }

    public ListBuilder<EnvironmentConfigurationBuilder, EnumerationListStrategy> enumerationStrategies() {
        return enumerationListStrategies;
    }

    public MapBuilder<EnvironmentConfigurationBuilder, String, Object> parameters () {
        return parameters;
    }

    public ListBuilder<EnvironmentConfigurationBuilder, Extension> extensions () {
        return extensions;
    }
}
