package org.jtwig.environment;

import org.jtwig.extension.Extension;
import org.junit.Test;

import static org.jtwig.environment.EnvironmentConfigurationBuilder.configuration;
import static org.jtwig.support.MatcherUtils.theSame;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class EnvironmentFactoryTest {
    private EnvironmentFactory underTest = new EnvironmentFactory();

    @Test
    public void createWithExtension() throws Exception {
        Extension extension = mock(Extension.class);

        EnvironmentConfigurationBuilder builder = configuration().withExtension(extension);
        underTest.create(builder.build());

        verify(extension).configure(argThat(theSame(builder)));
    }
}