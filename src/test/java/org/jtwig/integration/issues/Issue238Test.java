package org.jtwig.integration.issues;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.integration.AbstractIntegrationTest;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class Issue238Test extends AbstractIntegrationTest {
    @Test
    public void canConcatenateConstants() throws Exception {
        JtwigModel model = new JtwigModel();

        String result = JtwigTemplate
                .inlineTemplate("{{ 'hello' ~ 'world' }}")
                .render(model);

        assertThat(result, is(equalTo("helloworld")));
    }

    @Test
    public void canConcatenateVariables() throws Exception {
        JtwigModel model = new JtwigModel();
        model.with("a", "hello").with("b", "world");

        String result = JtwigTemplate
                .inlineTemplate("{{ a ~ b }}")
                .render(model);

        assertThat(result, is(equalTo("helloworld")));
    }
}
