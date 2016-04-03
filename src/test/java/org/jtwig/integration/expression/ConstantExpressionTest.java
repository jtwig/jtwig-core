package org.jtwig.integration.expression;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.integration.AbstractIntegrationTest;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ConstantExpressionTest extends AbstractIntegrationTest {
    @Test
    public void constantTest() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ 1 }}").render(JtwigModel.newModel());

        assertThat(result, is("1"));
    }

    @Test
    public void constantNullTest() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ null }}").render(JtwigModel.newModel());

        assertThat(result, is(""));
    }

    @Test
    public void constantStringTest() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ 'hello' }}").render(JtwigModel.newModel());

        assertThat(result, is("hello"));
    }
}
