package org.jtwig.integration.expression;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.exceptions.ResolveValueException;
import org.jtwig.integration.AbstractIntegrationTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.jtwig.environment.EnvironmentConfigurationBuilder.configuration;

public class VariableTest extends AbstractIntegrationTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void existingVariable() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ asd }}")
                .render(JtwigModel.newModel().with("asd", "hello"));

        assertThat(result, is("hello"));
    }

    @Test
    public void existingNullVariable() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ asd }}")
                .render(JtwigModel.newModel().with("asd", null));

        assertThat(result, is(""));
    }

    @Test
    public void existingNullVariableWithoutStrictMode() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ asd }}", configuration()
                .render().withStrictMode(false).and()
                .build())
                .render(JtwigModel.newModel().with("asd", null));

        assertThat(result, is(""));
    }

    @Test
    public void existingNullVariableWithStrictMode() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ asd }}", configuration()
                .render().withStrictMode(true).and()
                .build())
                .render(JtwigModel.newModel().with("asd", null));

        assertThat(result, is(""));
    }

    @Test
    public void nonExistingVariableAndStrictModeActive() throws Exception {
        expectedException.expect(ResolveValueException.class);
        expectedException.expectMessage(containsString("Variable 'asd' undefined"));

        JtwigTemplate.inlineTemplate("{{ asd }}", configuration()
                .render().withStrictMode(true).and()
                .build()).render(JtwigModel.newModel());
    }

    @Test
    public void nonExistingVariableAndStrictModeInactive() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ asd }}", configuration()
                .render().withStrictMode(false).and()
                .build()).render(JtwigModel.newModel());

        assertThat(result, is(""));
    }
}
