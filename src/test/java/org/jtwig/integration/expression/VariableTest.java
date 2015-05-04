package org.jtwig.integration.expression;

import org.jtwig.JtwigModel;
import org.jtwig.exceptions.CalculationException;
import org.jtwig.integration.AbstractIntegrationTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.jtwig.configuration.ConfigurationBuilder.configuration;

public class VariableTest extends AbstractIntegrationTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void existingVariable() throws Exception {
        String result = defaultStringTemplate("{{ asd }}")
                .render(JtwigModel.newModel().with("asd", "hello"));

        assertThat(result, is("hello"));
    }

    @Test
    public void nonExistingVariableAndStrictModeActive() throws Exception {
        expectedException.expect(CalculationException.class);
        expectedException.expectMessage(containsString("Variable 'asd' undefined"));

        defaultStringTemplate("{{ asd }}", configuration()
                .withStrictMode(true)
                .build()).render(JtwigModel.newModel());
    }

    @Test
    public void nonExistingVariableAndStrictModeInactive() throws Exception {
        String result = defaultStringTemplate("{{ asd }}", configuration()
                .withStrictMode(false)
                .build()).render(JtwigModel.newModel());

        assertThat(result, is(""));
    }
}
