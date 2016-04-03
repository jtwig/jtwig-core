package org.jtwig.integration.expression.binary;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ComparisonOperatorsTest {
    @Test
    public void equalFalse() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{% if (true == false) %}KO{% else %}OK{% endif %}").render(JtwigModel.newModel());

        assertThat(result, is("OK"));
    }
    @Test
    public void equalTrue() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{% if (true == true) %}OK{% else %}KO{% endif %}").render(JtwigModel.newModel());

        assertThat(result, is("OK"));
    }
    @Test
    public void differentFalse() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{% if (true != true) %}KO{% else %}OK{% endif %}").render(JtwigModel.newModel());

        assertThat(result, is("OK"));
    }
    @Test
    public void differentTrue() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{% if (true != false) %}OK{% else %}KO{% endif %}").render(JtwigModel.newModel());

        assertThat(result, is("OK"));
    }
    @Test
    public void lessFalse() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{% if (1 < 1) %}KO{% else %}OK{% endif %}").render(JtwigModel.newModel());

        assertThat(result, is("OK"));
    }
    @Test
    public void lessTrue() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{% if (1 < 2) %}OK{% else %}KO{% endif %}").render(JtwigModel.newModel());

        assertThat(result, is("OK"));
    }
    @Test
    public void lessOrEqualFalse() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{% if (1 <= 0) %}KO{% else %}OK{% endif %}").render(JtwigModel.newModel());

        assertThat(result, is("OK"));
    }
    @Test
    public void lessOrEqualTrue() throws Exception {
        assertThat(JtwigTemplate
                .inlineTemplate("{% if (1 <= 1) %}OK{% else %}KO{% endif %}")
                .render(JtwigModel.newModel()), is("OK"));
        assertThat(JtwigTemplate
                .inlineTemplate("{% if (1 <= 2) %}OK{% else %}KO{% endif %}")
                .render(JtwigModel.newModel()), is("OK"));
    }
    @Test
    public void greaterFalse() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{% if (1 > 1) %}KO{% else %}OK{% endif %}").render(JtwigModel.newModel());

        assertThat(result, is("OK"));
    }
    @Test
    public void greaterTrue() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{% if (2 > 1) %}OK{% else %}KO{% endif %}").render(JtwigModel.newModel());

        assertThat(result, is("OK"));
    }
    @Test
    public void greaterOrEqualFalse() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{% if (0 >= 1) %}KO{% else %}OK{% endif %}").render(JtwigModel.newModel());

        assertThat(result, is("OK"));
    }
    @Test
    public void greaterOrEqualTrue() throws Exception {
        assertThat(JtwigTemplate
                .inlineTemplate("{% if (1 >= 1) %}OK{% else %}KO{% endif %}")
                .render(JtwigModel.newModel()), is("OK"));
        assertThat(JtwigTemplate
                .inlineTemplate("{% if (2 >= 1) %}OK{% else %}KO{% endif %}")
                .render(JtwigModel.newModel()), is("OK"));
    }
}
