package org.jtwig.environment;

import com.google.common.collect.ImmutableMap;
import org.jtwig.extension.Extension;
import org.jtwig.functions.resolver.FunctionResolverFactory;
import org.jtwig.model.expression.lists.EnumerationListStrategyFactory;
import org.jtwig.parser.JtwigParserFactory;
import org.jtwig.property.PropertyResolverFactory;
import org.jtwig.render.environment.RenderEnvironmentFactory;
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
    private final EnumerationListStrategyFactory enumerationListStrategyFactory;
    private final ValueEnvironmentFactory valueEnvironmentFactory;

    public EnvironmentFactory () {
        this(new JtwigParserFactory(), new ResourceEnvironmentFactory(),
                new RenderEnvironmentFactory(), new FunctionResolverFactory(),
                new PropertyResolverFactory(), new EnumerationListStrategyFactory(),
                new ValueEnvironmentFactory());
    }

    public EnvironmentFactory(JtwigParserFactory jtwigParserFactory, ResourceEnvironmentFactory resourceEnvironmentFactory, RenderEnvironmentFactory renderEnvironmentFactory, FunctionResolverFactory functionResolverFactory, PropertyResolverFactory propertyResolverFactory, EnumerationListStrategyFactory enumerationListStrategyFactory, ValueEnvironmentFactory valueEnvironmentFactory) {
        this.jtwigParserFactory = jtwigParserFactory;
        this.resourceEnvironmentFactory = resourceEnvironmentFactory;
        this.renderEnvironmentFactory = renderEnvironmentFactory;
        this.functionResolverFactory = functionResolverFactory;
        this.propertyResolverFactory = propertyResolverFactory;
        this.enumerationListStrategyFactory = enumerationListStrategyFactory;
        this.valueEnvironmentFactory = valueEnvironmentFactory;
    }

    public Environment create(EnvironmentConfiguration configuration) {
        EnvironmentConfigurationBuilder builder = new EnvironmentConfigurationBuilder(configuration);

        for (Extension extension : configuration.getExtensions()) {
            extension.configure(builder);
        }

        EnvironmentConfiguration environmentConfiguration = builder.build();

        return new Environment(
                jtwigParserFactory.create(environmentConfiguration.getJtwigParserConfiguration()),
                ImmutableMap.copyOf(environmentConfiguration.getParameters()),
                resourceEnvironmentFactory.create(environmentConfiguration.getResourceConfiguration()),
                functionResolverFactory.create(environmentConfiguration.getFunctionResolverConfiguration()),
                propertyResolverFactory.create(environmentConfiguration.getPropertyResolverConfiguration()),
                renderEnvironmentFactory.create(environmentConfiguration.getRenderConfiguration()),
                valueEnvironmentFactory.crete(environmentConfiguration.getValueConfiguration()),
                enumerationListStrategyFactory.create(environmentConfiguration.getEnumerationListConfiguration())
        );
    }
}
