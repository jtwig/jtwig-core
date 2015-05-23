package org.jtwig.integration.expression;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.integration.AbstractIntegrationTest;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class InTest extends AbstractIntegrationTest {
    @Test
    public void inTrue() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{% if ('a' in ['a', 'b']) %}ok{% endif %}")
                .render(JtwigModel.newModel());

        assertThat(result, is("ok"));
    }
    @Test
    public void inFalse() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{% if ('a' in ['c', 'b']) %}ok{% endif %}")
                .render(JtwigModel.newModel());

        assertThat(result, is(""));
    }
}
