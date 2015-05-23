package org.jtwig.integration.issues;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.integration.AbstractIntegrationTest;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class Issue142Test extends AbstractIntegrationTest {
    @Test
    public void longFirstArgEvaluation() throws Exception {
        JtwigModel model = new JtwigModel();
        model.with("var", 5L);

        String result = JtwigTemplate.inlineTemplate("{{ var == 5 }}")
                .render(model);

        assertThat(result, is(equalTo("1")));
    }

    @Test
    public void longSecondArgEvaluation() throws Exception {
        JtwigModel model = new JtwigModel();
        model.with("var", 5L);

        String result = JtwigTemplate.inlineTemplate("{{ 5 == var }}")
                .render(model);

        assertThat(result, is(equalTo("1")));
    }
}
