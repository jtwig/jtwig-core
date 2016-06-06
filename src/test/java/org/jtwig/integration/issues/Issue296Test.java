package org.jtwig.integration.issues;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class Issue296Test {
    @Test
    public void comparisonWithNonNullString() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{% if attr != \"\" %}{{ attr }}{% else %}no or empty attribute{% endif %}")
                .render(JtwigModel.newModel().with("attr", "a"));

        assertThat(result, is("a"));
    }
    @Test
    public void comparisonWithEmptyStringAndUndefined() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{% if attr.attr != \"\" %}{{ attr }}{% else %}no or empty attribute{% endif %}")
                .render(JtwigModel.newModel().with("attr", "a"));

        assertThat(result, is("no or empty attribute"));
    }
    @Test
    public void comparisonWithEmptyStringAndNull() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{% if attr != \"\" %}{{ attr }}{% else %}no or empty attribute{% endif %}")
                .render(JtwigModel.newModel().with("attr", null));

        assertThat(result, is("no or empty attribute"));
    }
}
