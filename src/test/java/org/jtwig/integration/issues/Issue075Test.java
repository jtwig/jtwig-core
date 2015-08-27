package org.jtwig.integration.issues;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

public class Issue075Test {
    @Test
    public void issue75IsNull() throws Exception {
        JtwigModel model = new JtwigModel();
        model.with("a", new NullPointer());

        String result = JtwigTemplate
                .inlineTemplate("{% if (a.value is null) %}A{% else %}B{% endif %}")
                .render(model);

        assertThat(result, containsString("A"));
    }

    @Test
    public void issue75IsNullWithParentheses() throws Exception {
        JtwigModel model = new JtwigModel();
        model.with("a", new NullPointer());

        String result = JtwigTemplate
                .inlineTemplate("{% if (a.value) is null %}A{% else %}B{% endif %}")
                .render(model);

        assertThat(result, containsString("A"));
    }

    public class NullPointer {
        public String getValue () {
            return null;
        }

        @Override
        public String toString() {
            return "Null Pointer";
        }
    }
}
