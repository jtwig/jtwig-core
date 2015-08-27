package org.jtwig.integration.expression;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.exceptions.CalculationException;
import org.jtwig.integration.AbstractIntegrationTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringContains.containsString;

public class TestExpressionTest extends AbstractIntegrationTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void isNull() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{% if (null is null) %}ok{% endif %}")
                .render(JtwigModel.newModel());

        assertThat(result, is("ok"));
    }

    @Test
    public void isNullFalse() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{% if (1 is null) %}ko{% else %}ok{% endif %}")
                .render(JtwigModel.newModel());

        assertThat(result, is("ok"));
    }

    @Test
    public void isDefinedTrue() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{% if (null is defined) %}ok{% else %}ko{% endif %}")
                .render(JtwigModel.newModel());

        assertThat(result, is("ok"));
    }

    @Test
    public void isDefinedFalse() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{% if (variable is defined) %}ko{% else %}ok{% endif %}")
                .render(JtwigModel.newModel());

        assertThat(result, is("ok"));
    }

    @Test
    public void isDivisibleByFalse() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{% if (2 is divisible by 3) %}ko{% else %}ok{% endif %}")
                .render(JtwigModel.newModel());

        assertThat(result, is("ok"));
    }

    @Test
    public void isDivisibleByTrue() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{% if (2 is divisible by 1) %}ok{% else %}ko{% endif %}")
                .render(JtwigModel.newModel());

        assertThat(result, is("ok"));
    }

    @Test
    public void isDivisibleByError() throws Exception {
        expectedException.expect(CalculationException.class);
        expectedException.expectMessage(containsString("Expecting a number"));

        String result = JtwigTemplate.inlineTemplate("{% if (2 is divisible by 'a') %}ok{% else %}ko{% endif %}")
                .render(JtwigModel.newModel());

        assertThat(result, is("ok"));
    }

    @Test
    public void sameAsFalse() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{% if (2 is same as('a')) %}ko{% else %}ok{% endif %}")
                .render(JtwigModel.newModel());

        assertThat(result, is("ok"));
    }

    @Test
    public void sameAsTrue() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{% if (variable is same as(variable)) %}ok{% else %}ko{% endif %}")
                .render(JtwigModel.newModel().with("variable", ""));

        assertThat(result, is("ok"));
    }

    @Test
    public void sameAsBooleanTrue() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{% if (variable is same as(true)) %}ok{% else %}ko{% endif %}")
                .render(JtwigModel.newModel().with("variable", true));

        assertThat(result, is("ok"));
    }
}
