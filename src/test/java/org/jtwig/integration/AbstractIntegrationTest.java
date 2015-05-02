package org.jtwig.integration;

import org.jtwig.JtwigTemplate;
import org.jtwig.configuration.Configuration;
import org.jtwig.resource.StringResource;

import static org.jtwig.configuration.ConfigurationBuilder.configuration;

public abstract class AbstractIntegrationTest {
    protected JtwigTemplate defaultStringTemplate(String content) {
        return new JtwigTemplate(new StringResource(content), configuration().build());
    }
    protected JtwigTemplate defaultStringTemplate(String content, Configuration config) {
        return new JtwigTemplate(new StringResource(content), config);
    }
}
