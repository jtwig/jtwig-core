package org.jtwig.environment;

import org.jtwig.environment.initializer.EnvironmentInitializer;
import org.jtwig.extension.Extension;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
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

        EnvironmentConfigurationBuilder builder = configuration().extensions().add(extension).and();
        underTest.create(builder.build());

        verify(extension).configure(argThat(theSame(builder)));
    }

    @Test
    public void initializersTest() throws Exception {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        EnvironmentInitializer initializer = new EnvironmentInitializer() {
            @Override
            public void initialize(Environment environment) {
                atomicBoolean.set(true);
            }
        };

        EnvironmentConfiguration configuration = EnvironmentConfigurationBuilder
                .configuration()
                    .initializers()
                        .add(initializer)
                    .and()
                .build();

        underTest.create(configuration);

        assertThat(atomicBoolean.get(), is(true));
    }
}