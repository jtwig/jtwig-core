package org.jtwig.integration.expression.binary;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class MathOperatorsTest {
    @Test
    public void modSimple() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ 4 % 2 }}").render(JtwigModel.newModel());

        assertThat(result, is("0"));
    }
    @Test
    public void addSimple() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ 4 + 2 }}").render(JtwigModel.newModel());

        assertThat(result, is("6"));
    }
}
