package org.jtwig.integration.node;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.integration.AbstractIntegrationTest;
import org.jtwig.parser.ParseException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.jtwig.environment.EnvironmentConfigurationBuilder.configuration;

public class MacroTest extends AbstractIntegrationTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void simpleMacro() throws Exception {
        JtwigTemplate template = JtwigTemplate.inlineTemplate("{% macro name () %}{% endmacro %}");
        String result = template.render(JtwigModel.newModel());
        assertThat(result, is(""));
    }


    @Test
    public void mixedImports() throws Exception {
        String result = JtwigTemplate.classpathTemplate("example/macros/mixed-imports-example.twig", configuration()
                .render().withStrictMode(true).and()
                .build())
                .render(JtwigModel.newModel());

        assertThat(result.trim(), is("Hello! oi"));
    }

    @Test
    public void macroSelf() throws Exception {
        String result = JtwigTemplate.classpathTemplate("example/macros/macro-self-example.twig", configuration()
                .render().withStrictMode(true).and()
                .build())
                .render(JtwigModel.newModel());

        assertThat(result.trim(), is("one\n1"));
    }

    @Test
    public void nestedMacro() throws Exception {
        String result = JtwigTemplate.classpathTemplate("example/macros/nested-macro-example.twig", configuration()
                .render().withStrictMode(true).and()
                .build())
                .render(JtwigModel.newModel());

        assertThat(result.trim(), is("oi"));
    }

    @Test
    public void macroMissingName() throws Exception {
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Missing macro name"));

        JtwigTemplate.inlineTemplate("{% macro () %}{% endmacro %}")
                .render(JtwigModel.newModel());
    }

    @Test
    public void macroMissingArguments() throws Exception {
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Missing macro arguments"));

        JtwigTemplate.inlineTemplate("{% macro name %}{% endmacro %}")
                .render(JtwigModel.newModel());
    }

    @Test
    public void macroMissingEndCode() throws Exception {
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Code island not closed"));

        JtwigTemplate.inlineTemplate("{% macro name () {% endmacro %}")
                .render(JtwigModel.newModel());
    }

    @Test
    public void macroMissingEndTag() throws Exception {
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Missing macroend tag"));

        JtwigTemplate.inlineTemplate("{% macro name () %}")
                .render(JtwigModel.newModel());
    }
}
