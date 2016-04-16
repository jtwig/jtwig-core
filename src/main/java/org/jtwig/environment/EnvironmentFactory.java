package org.jtwig.environment;

import org.jtwig.escape.environment.EscapeEnvironmentFactory;
import org.jtwig.extension.Extension;
import org.jtwig.functions.environment.FunctionResolverFactory;
import org.jtwig.parser.JtwigParserFactory;
import org.jtwig.property.PropertyResolverFactory;
import org.jtwig.render.environment.RenderEnvironmentFactory;
import org.jtwig.render.expression.calculator.enumerated.environment.EnumerationListStrategyFactory;
import org.jtwig.resource.environment.ResourceEnvironmentFactory;
import org.jtwig.value.environment.ValueEnvironmentFactory;

/**
 * Stateless and thread-safe implementation of a Environment factory.
 */
public class EnvironmentFactory {
    private final JtwigParserFactory jtwigParserFactory;
    private final ResourceEnvironmentFactory resourceEnvironmentFactory;
    private final RenderEnvironmentFactory renderEnvironmentFactory;
    private final FunctionResolverFactory functionResolverFactory;
    private final PropertyResolverFactory propertyResolverFactory;
    private final ValueEnvironmentFactory valueEnvironmentFactory;
    private final EnumerationListStrategyFactory enumerationListStrategyFactory;
    private final EscapeEnvironmentFactory escapeEnvironmentFactory;

    public EnvironmentFactory () {
        this(new JtwigParserFactory(), new ResourceEnvironmentFactory(),
                new RenderEnvironmentFactory(),
                new FunctionResolverFactory(),
                new PropertyResolverFactory(),
                new ValueEnvironmentFactory(),
                new EnumerationListStrategyFactory(),
                new EscapeEnvironmentFactory());
    }

    public EnvironmentFactory(JtwigParserFactory jtwigParserFactory, ResourceEnvironmentFactory resourceEnvironmentFactory, RenderEnvironmentFactory renderEnvironmentFactory, FunctionResolverFactory functionResolverFactory, PropertyResolverFactory propertyResolverFactory, ValueEnvironmentFactory valueEnvironmentFactory, EnumerationListStrategyFactory enumerationListStrategyFactory, EscapeEnvironmentFactory escapeEnvironmentFactory) {
        this.jtwigParserFactory = jtwigParserFactory;
        this.resourceEnvironmentFactory = resourceEnvironmentFactory;
        this.renderEnvironmentFactory = renderEnvironmentFactory;
        this.functionResolverFactory = functionResolverFactory;
        this.propertyResolverFactory = propertyResolverFactory;
        this.enumerationListStrategyFactory = enumerationListStrategyFactory;
        this.valueEnvironmentFactory = valueEnvironmentFactory;
        this.escapeEnvironmentFactory = escapeEnvironmentFactory;
    }

    public Environment create(EnvironmentConfiguration environmentConfiguration) {
        if (!environmentConfiguration.getExtensions().isEmpty()) {
            EnvironmentConfigurationBuilder builder = new EnvironmentConfigurationBuilder(environmentConfiguration);
            for (Extension extension : environmentConfiguration.getExtensions()) {
                extension.configure(builder);
            }
            environmentConfiguration = builder.build();
        }

        return new Environment(
                jtwigParserFactory.create(environmentConfiguration.getJtwigParserConfiguration()),
                environmentConfiguration.getParameters(),
                resourceEnvironmentFactory.create(environmentConfiguration.getResourceConfiguration()),
                functionResolverFactory.create(environmentConfiguration.getFunctions()),
                propertyResolverFactory.create(environmentConfiguration.getPropertyResolvers()),
                renderEnvironmentFactory.create(environmentConfiguration.getRenderConfiguration()),
                valueEnvironmentFactory.create(environmentConfiguration.getValueConfiguration()),
                enumerationListStrategyFactory.create(environmentConfiguration.getEnumerationStrategies()),
                escapeEnvironmentFactory.create(environmentConfiguration.getEscapeConfiguration()));
    }
}
