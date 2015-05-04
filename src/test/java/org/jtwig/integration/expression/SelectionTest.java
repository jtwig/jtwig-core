package org.jtwig.integration.expression;

import org.jtwig.exceptions.CalculationException;
import org.jtwig.integration.AbstractIntegrationTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.jtwig.JtwigModel.newModel;
import static org.jtwig.configuration.ConfigurationBuilder.configuration;

public class SelectionTest extends AbstractIntegrationTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void selectionWithNonExistingPropertyAndStrictModeInactive() throws Exception {
        String result = defaultStringTemplate("{{ asd.test }}", configuration()
                .withStrictMode(false)
                .build())
                .render(newModel().with("asd", 1));

        assertThat(result, is(""));
    }
    @Test
    public void selectionWithNonExistingPropertyAndStrictModeActive() throws Exception {
        expectedException.expect(CalculationException.class);
        expectedException.expectMessage(containsString("Impossible to access an attribute 'test' on '1'"));

        defaultStringTemplate("{{ asd.test }}", configuration()
                .withStrictMode(true)
                .build())
                .render(newModel().with("asd", 1));
    }


}
