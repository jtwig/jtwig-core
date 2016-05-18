package org.jtwig.integration.function;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.jtwig.environment.EnvironmentConfigurationBuilder.configuration;

public class DefaultFunctionTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void simpleDefault() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ default(null, 'hi') }}").render(JtwigModel.newModel());

        assertThat(result, is("hi"));
    }
    @Test
    public void simpleDefaultUndefined() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ default(var, 'hi') }}").render(JtwigModel.newModel());

        assertThat(result, is("hi"));
    }
    @Test
    public void defaultNotNull() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ default(1, 'hi') }}").render(JtwigModel.newModel());

        assertThat(result, is("1"));
    }

    @Test
    public void defaultWhenStrictModeActive() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ default(var, 'hi') }}", configuration()
                .render().withStrictMode(true).and()
                .build()).render(JtwigModel.newModel());

        assertThat(result, is("hi"));
    }
}
