package org.jtwig.environment;

import org.jtwig.environment.initializer.EnvironmentInitializer;
import org.jtwig.escape.environment.EscapeEnvironmentFactory;
import org.jtwig.extension.Extension;
import org.jtwig.functions.environment.FunctionResolverFactory;
import org.jtwig.parser.JtwigParserFactory;
import org.jtwig.property.environment.PropertyResolverEnvironmentFactory;
import org.jtwig.render.environment.RenderEnvironmentFactory;
import org.jtwig.render.expression.calculator.enumerated.environment.EnumerationListStrategyFactory;
import org.jtwig.resource.environment.ResourceEnvironmentFactory;
import org.jtwig.value.environment.ValueEnvironmentFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Stateless and thread-safe implementation of a Environment factory.
 */
public class EnvironmentFactory {
    private static final Logger log = LoggerFactory.getLogger(EnvironmentFactory.class);

    private final JtwigParserFactory jtwigParserFactory;
    private final ResourceEnvironmentFactory resourceEnvironmentFactory;
    private final RenderEnvironmentFactory renderEnvironmentFactory;
    private final FunctionResolverFactory functionResolverFactory;
    private final PropertyResolverEnvironmentFactory propertyResolverFactory;
    private final ValueEnvironmentFactory valueEnvironmentFactory;
    private final EnumerationListStrategyFactory enumerationListStrategyFactory;
    private final EscapeEnvironmentFactory escapeEnvironmentFactory;

    public EnvironmentFactory () {
        this(new JtwigParserFactory(), new ResourceEnvironmentFactory(),
                new RenderEnvironmentFactory(),
                new FunctionResolverFactory(),
                new PropertyResolverEnvironmentFactory(),
                new ValueEnvironmentFactory(),
                new EnumerationListStrategyFactory(),
                new EscapeEnvironmentFactory());
    }

    public EnvironmentFactory(JtwigParserFactory jtwigParserFactory, ResourceEnvironmentFactory resourceEnvironmentFactory, RenderEnvironmentFactory renderEnvironmentFactory, FunctionResolverFactory functionResolverFactory, PropertyResolverEnvironmentFactory propertyResolverFactory, ValueEnvironmentFactory valueEnvironmentFactory, EnumerationListStrategyFactory enumerationListStrategyFactory, EscapeEnvironmentFactory escapeEnvironmentFactory) {
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
        EnvironmentConfiguration configuration = environmentConfiguration;

        if (!environmentConfiguration.getExtensions().isEmpty()) {
            log.info("Jtwig base configuration extended with:");
            EnvironmentConfigurationBuilder builder = new EnvironmentConfigurationBuilder(environmentConfiguration);
            for (Extension extension : environmentConfiguration.getExtensions()) {
                log.info("- {}", extension.getClass().getSimpleName());
                extension.configure(builder);
            }
            configuration = builder.build();
        }

        Environment instance = new Environment(
                jtwigParserFactory.create(configuration.getJtwigParserConfiguration()),
                configuration.getParameters(),
                resourceEnvironmentFactory.create(configuration.getResourceConfiguration()),
                functionResolverFactory.create(configuration.getFunctions()),
                propertyResolverFactory.create(configuration.getPropertyResolverConfiguration()),
                renderEnvironmentFactory.create(configuration.getRenderConfiguration()),
                valueEnvironmentFactory.create(configuration.getValueConfiguration()),
                enumerationListStrategyFactory.create(configuration.getEnumerationStrategies()),
                escapeEnvironmentFactory.create(configuration.getEscapeConfiguration()));

        if (!configuration.getInitializers().isEmpty()) {
            log.info("Jtwig pre-loading resources");
            for (EnvironmentInitializer initializer : configuration.getInitializers()) {
                initializer.initialize(instance);
            }
        }

        return instance;
    }
}
