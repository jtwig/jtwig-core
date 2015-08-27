package org.jtwig.integration.issues;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class Issue079Test {
    @Test
    public void notDefinedVariable() throws Exception {

        String result = JtwigTemplate
                .inlineTemplate("{% if a is not defined %}A{% endif %}")
                .render(new JtwigModel());

        assertThat(result, is(equalTo("A")));
    }

    @Test
    public void notDefinedMethod() throws Exception {
        JtwigModel model = new JtwigModel();
        model.with("a", "test");

        String result = JtwigTemplate
                .inlineTemplate("{% if (a.check) is not defined %}A{% endif %}")
                .render(model);

        assertThat(result, is(equalTo("A")));
    }
}
