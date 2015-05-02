package org.jtwig.configuration;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class BooleanConfigurationParametersTest {

    @Test
    public void strictModeInactiveByDefault() throws Exception {
        ConfigurationParameter<Boolean> configurationParameter = BooleanConfigurationParameters.STRICT_MODE;

        assertThat(configurationParameter.defaultValue(), is(false));
    }
}