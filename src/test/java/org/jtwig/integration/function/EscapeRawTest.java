package org.jtwig.integration.function;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.integration.AbstractIntegrationTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.Is.is;

public class EscapeRawTest extends AbstractIntegrationTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void latestApply() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ '&' | escape | raw }}").render(JtwigModel.newModel());

        assertThat(result, is("&"));
    }

    @Test
    public void escape() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ '&' | escape('html') | escape(false) }}").render(JtwigModel.newModel());

        assertThat(result, is("&"));
    }

    @Test
    public void escapeTrue() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ '&' | escape(true) }}").render(JtwigModel.newModel());

        assertThat(result, is("&amp;"));
    }

    @Test
    public void escapeInvalid() throws Exception {
        expectedException.expectMessage(containsString("Invalid escape engine requested 'blah'. Only supporting [javascript, js, none, html]"));

        JtwigTemplate.inlineTemplate("{{ '&' | escape('blah') }}").render(JtwigModel.newModel());
    }

    @Test
    public void latestApplyEscape() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ '&' | raw | escape }}").render(JtwigModel.newModel());

        assertThat(result, is("&amp;"));
    }

    @Test
    public void latestApplyEscapeJavascript() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ '\"' | raw | escape('js') }}").render(JtwigModel.newModel());

        assertThat(result, is("\\\""));
    }

    @Test
    public void latestApplyEscapeJavascriptVerbose() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ '\"' | raw | escape('javascript') }}").render(JtwigModel.newModel());

        assertThat(result, is("\\\""));
    }
}
