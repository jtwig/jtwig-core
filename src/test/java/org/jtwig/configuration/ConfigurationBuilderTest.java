package org.jtwig.configuration;

import org.jtwig.property.CompositePropertyResolver;
import org.jtwig.resource.resolver.CompositeResourceResolver;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ConfigurationBuilderTest {
    @Test
    public void configurationWithDefaults() throws Exception {
        Configuration result = ConfigurationBuilder
                .configuration()
                .build();

        CompositePropertyResolver propertyResolver = (CompositePropertyResolver) result.propertyResolver();
        CompositeResourceResolver resourceResolver = (CompositeResourceResolver) result.resourceResolver();

        assertThat(propertyResolver.size(), is(3));
        assertThat(resourceResolver.size(), is(1));
    }
}