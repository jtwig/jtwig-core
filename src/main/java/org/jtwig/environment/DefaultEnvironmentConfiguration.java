package org.jtwig.environment;

import org.jtwig.context.DefaultRenderConfiguration;
import org.jtwig.functions.resolver.DefaultFunctionResolverConfiguration;
import org.jtwig.model.expression.lists.DefaultEnumerationListStrategyConfiguration;
import org.jtwig.parser.DefaultJtwigParserConfiguration;
import org.jtwig.property.DefaultPropertyResolverConfiguration;
import org.jtwig.resource.resolver.DefaultResourceResolverConfiguration;
import org.jtwig.value.configuration.CompatibleModeValueConfiguration;

import java.util.Collections;

public class DefaultEnvironmentConfiguration extends EnvironmentConfiguration {
    public DefaultEnvironmentConfiguration() {
        super(
                new CompatibleModeValueConfiguration(),
                new DefaultResourceResolverConfiguration(),
                new DefaultFunctionResolverConfiguration(),
                new DefaultPropertyResolverConfiguration(),
                new DefaultEnumerationListStrategyConfiguration(),
                new DefaultJtwigParserConfiguration(),
                new DefaultRenderConfiguration(),
                Collections.<String, Object>emptyMap()
        );
    }
}
