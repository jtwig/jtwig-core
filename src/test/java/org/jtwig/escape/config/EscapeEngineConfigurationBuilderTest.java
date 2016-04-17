package org.jtwig.escape.config;

import org.junit.Test;

import static org.jtwig.support.MatcherUtils.theSameBean;
import static org.junit.Assert.assertThat;

public class EscapeEngineConfigurationBuilderTest {
    @Test
    public void cloneConstructor() throws Exception {
        EscapeEngineConfiguration prototype = new DefaultEscapeEngineConfiguration();

        EscapeEngineConfiguration result = new EscapeEngineConfigurationBuilder<>(prototype)
                .build();

        assertThat(result, theSameBean(prototype));
    }
}