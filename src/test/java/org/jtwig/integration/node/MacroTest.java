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

public class MacroTest extends AbstractIntegrationTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void simpleMacro() throws Exception {
        JtwigTemplate template = defaultStringTemplate("{% macro name () %}{% endmacro %}");
        String result = template.render(JtwigModel.newModel());
        assertThat(result, is(""));
    }

    @Test
    public void macroMissingName() throws Exception {
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Missing macro name"));

        defaultStringTemplate("{% macro () %}{% endmacro %}")
                .render(JtwigModel.newModel());
    }

    @Test
    public void macroMissingArguments() throws Exception {
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Missing macro arguments"));

        defaultStringTemplate("{% macro name %}{% endmacro %}")
                .render(JtwigModel.newModel());
    }

    @Test
    public void macroMissingEndCode() throws Exception {
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Code island not closed"));

        defaultStringTemplate("{% macro name () {% endmacro %}")
                .render(JtwigModel.newModel());
    }

    @Test
    public void macroMissingEndTag() throws Exception {
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Missing macroend tag"));

        defaultStringTemplate("{% macro name () %}")
                .render(JtwigModel.newModel());
    }
}
