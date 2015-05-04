package org.jtwig.configuration;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class BooleanConfigurationParametersTest {

    @Test
    public void strictModeInactiveByDefault() throws Exception {
        Configuration configuration = ConfigurationBuilder.configuration().build();

        assertThat(configuration.strictMode(), is(false));
    }
}