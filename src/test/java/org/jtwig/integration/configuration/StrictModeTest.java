package org.jtwig.integration.configuration;

import org.jtwig.exceptions.CalculationException;
import org.jtwig.integration.AbstractIntegrationTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.jtwig.JtwigModel.newModel;
import static org.jtwig.configuration.ConfigurationBuilder.configuration;

public class StrictModeTest extends AbstractIntegrationTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void strictModeActive() throws Exception {
        expectedException.expect(CalculationException.class);

        defaultStringTemplate("{{ undefined }}", configuration()
                .withStrictMode(true)
                .build()).render(newModel());
    }
    @Test
    public void strictModeInactive() throws Exception {
        String result = defaultStringTemplate("{{ undefined }}", configuration()
                .withStrictMode(false)
                .build()).render(newModel());

        assertThat(result, is(""));
    }
}
