package org.jtwig.integration.issues;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class Issue086Test {

    @Test
    public void issue86() throws Exception {
        JtwigModel model = new JtwigModel();

        String result = JtwigTemplate
                .inlineTemplate("{{ -1 }}")
                .render(model);

        assertThat(result, is(equalTo("-1")));
    }

    @Test
    public void issue86WithConstant() throws Exception {
        JtwigModel model = new JtwigModel();

        String result = JtwigTemplate
                .inlineTemplate("{{ 1-1 }}")
                .render(model);

        assertThat(result, is(equalTo("0")));
    }

    @Test
    public void issue86WithVariable() throws Exception {
        JtwigModel model = new JtwigModel();
        model.with("value", 1);

        String result = JtwigTemplate
                .inlineTemplate("{{ value-1 }}")
                .render(model);

        assertThat(result, is(equalTo("0")));
    }

    @Test
    public void issue86WithVariableUnary() throws Exception {
        JtwigModel model = new JtwigModel();
        model.with("value", -1);

        String result = JtwigTemplate
                .inlineTemplate("{{ -value }}")
                .render(model);

        assertThat(result, is(equalTo("1")));
    }
}
