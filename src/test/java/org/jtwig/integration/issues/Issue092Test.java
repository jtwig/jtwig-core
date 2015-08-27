package org.jtwig.integration.issues;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class Issue092Test {
    @Test
    public void testDecimalResult() throws Exception {
        JtwigModel model = new JtwigModel();

        String result = JtwigTemplate
                .inlineTemplate("{{ 1 / 10 }}")
                .render(model);

        assertThat(result, is(equalTo("0.1")));
    }
    @Test
    public void testIntegerDivision() throws Exception {
        JtwigModel model = new JtwigModel();

        String result = JtwigTemplate
                .inlineTemplate("{{ 1 // 10 }}")
                .render(model);

        assertThat(result, is(equalTo("0")));
    }
}
