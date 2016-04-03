package org.jtwig.environment;

import org.jtwig.extension.Extension;
import org.jtwig.functions.resolver.DefaultFunctionResolverConfiguration;
import org.jtwig.parser.DefaultJtwigParserConfiguration;
import org.jtwig.property.DefaultPropertyResolverConfiguration;
import org.jtwig.render.config.DefaultRenderConfiguration;
import org.jtwig.render.expression.calculator.enumerated.config.DefaultEnumerationListStrategyConfiguration;
import org.jtwig.resource.config.DefaultResourceConfiguration;
import org.jtwig.value.config.DefaultValueConfiguration;

import java.util.Collections;

public class DefaultEnvironmentConfiguration extends EnvironmentConfiguration {
    public DefaultEnvironmentConfiguration() {
        super(
                new DefaultResourceConfiguration(),
                new DefaultFunctionResolverConfiguration(),
                new DefaultPropertyResolverConfiguration(),
                new DefaultEnumerationListStrategyConfiguration(),
                new DefaultJtwigParserConfiguration(),
                new DefaultValueConfiguration(),
                new DefaultRenderConfiguration(),
                Collections.<String, Object>emptyMap(),
                Collections.<Extension>emptyList());
    }
}
