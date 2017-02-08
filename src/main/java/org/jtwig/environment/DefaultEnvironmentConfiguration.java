package org.jtwig.environment;

import org.jtwig.environment.initializer.EnvironmentInitializer;
import org.jtwig.escape.config.DefaultEscapeEngineConfiguration;
import org.jtwig.extension.Extension;
import org.jtwig.functions.config.DefaultJtwigFunctionList;
import org.jtwig.parser.config.DefaultJtwigParserConfiguration;
import org.jtwig.property.configuration.DefaultPropertyResolverConfiguration;
import org.jtwig.render.config.DefaultRenderConfiguration;
import org.jtwig.render.expression.calculator.enumerated.config.DefaultEnumerationListStrategyList;
import org.jtwig.resource.config.DefaultResourceConfiguration;
import org.jtwig.value.config.DefaultValueConfiguration;

import java.util.Collections;

public class DefaultEnvironmentConfiguration extends EnvironmentConfiguration {
    public DefaultEnvironmentConfiguration() {
        super(
                new DefaultResourceConfiguration(),
                new DefaultEnumerationListStrategyList(),
                new DefaultJtwigParserConfiguration(),
                new DefaultValueConfiguration(),
                new DefaultRenderConfiguration(),
                new DefaultEscapeEngineConfiguration(),
                new DefaultPropertyResolverConfiguration(),
                new DefaultJtwigFunctionList(),
                Collections.<String, Object>emptyMap(),
                Collections.<Extension>emptyList(),
                Collections.<EnvironmentInitializer>emptyList());
    }
}
