package org.jtwig.integration.expression.binary;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class LogicOperatorsTest {
    @Test
    public void andSimple() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{% if (true and false) %}KO{% else %}OK{% endif %}").render(JtwigModel.newModel());

        assertThat(result, is("OK"));
    }

    @Test
    public void orSimple() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{% if (true or false) %}OK{% else %}KO{% endif %}").render(JtwigModel.newModel());

        assertThat(result, is("OK"));
    }

    @Test
    public void notSimple() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{% if (not false) %}OK{% else %}KO{% endif %}").render(JtwigModel.newModel());

        assertThat(result, is("OK"));
    }
}
