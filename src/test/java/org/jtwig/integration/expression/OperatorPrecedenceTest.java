package org.jtwig.integration.expression;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class OperatorPrecedenceTest {
    @Test
    public void multiplyOverSum() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ 2 + 5 * 3 }}")
                .render(JtwigModel.newModel());

        assertThat(result, is("17"));
    }
    @Test
    public void multiplyOverSum2() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ 2 * 5 + 3 }}")
                .render(JtwigModel.newModel());

        assertThat(result, is("13"));
    }
}
