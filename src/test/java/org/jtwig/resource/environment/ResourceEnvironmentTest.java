package org.jtwig.resource.environment;

import org.jtwig.resource.config.ResourceConfiguration;
import org.jtwig.resource.config.ResourceConfigurationBuilder;
import org.jtwig.resource.reference.ResourceReferenceExtractor;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class ResourceEnvironmentTest {
    @Test
    public void defaultResourceEnv() throws Exception {
        ResourceReferenceExtractor referenceExtractor = mock(ResourceReferenceExtractor.class);
        ResourceConfiguration resourceConfiguration = new ResourceConfigurationBuilder<>()
                .withResourceReferenceExtractor(referenceExtractor)
                .build();
        ResourceEnvironment resourceEnvironment = new ResourceEnvironmentFactory().create(resourceConfiguration);

        assertThat(resourceEnvironment.getResourceReferenceExtractor(), is(referenceExtractor));
    }
}