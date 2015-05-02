package org.jtwig.integration;

import org.jtwig.JtwigTemplate;
import org.jtwig.parser.ParseException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.jtwig.JtwigModel.newModel;

public class SetTest extends AbstractIntegrationTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void simpleSet() throws Exception {

        JtwigTemplate jtwigTemplate = defaultStringTemplate("{% set variable = 1 %}{{ variable }}");

        String result = jtwigTemplate.render(newModel());

        assertThat(result, is("1"));
    }

    @Test
    public void setWhiteSpaceControl() throws Exception {
        JtwigTemplate jtwigTemplate = defaultStringTemplate(" {%- set variable = 1 -%} {{ variable }}");

        String result = jtwigTemplate.render(newModel());

        assertThat(result, is("1"));
    }

    @Test
    public void setNotEnding() throws Exception {
        JtwigTemplate jtwigTemplate = defaultStringTemplate(" {%- set variable = 1 {{ variable }}");
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Expecting end of set code island"));

        jtwigTemplate.render(newModel());
    }

    @Test
    public void setNoAttribution() throws Exception {
        JtwigTemplate jtwigTemplate = defaultStringTemplate(" {%- set variable 1 %}{{ variable }}");
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Expecting attribution operation '='"));

        jtwigTemplate.render(newModel());
    }
}
