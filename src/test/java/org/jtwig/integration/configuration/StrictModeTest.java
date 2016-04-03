package org.jtwig.integration.configuration;

import org.jtwig.JtwigTemplate;
import org.jtwig.exceptions.ResolveValueException;
import org.jtwig.integration.AbstractIntegrationTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.jtwig.JtwigModel.newModel;
import static org.jtwig.environment.EnvironmentConfigurationBuilder.configuration;

public class StrictModeTest extends AbstractIntegrationTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void strictModeActive() throws Exception {
        expectedException.expect(ResolveValueException.class);

        JtwigTemplate.inlineTemplate("{{ undefined }}", configuration()
                .render().withStrictMode(true).and()
                .build()).render(newModel());
    }
    @Test
    public void strictModeInactive() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ undefined }}", configuration()
                .render().withStrictMode(false).and()
                .build()).render(newModel());

        assertThat(result, is(""));
    }
}
