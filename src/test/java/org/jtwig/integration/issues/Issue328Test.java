package org.jtwig.integration.issues;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class Issue328Test {
    @Test
    public void nullPointerException() throws Exception {
        JtwigTemplate jtwigTemplate = JtwigTemplate.inlineTemplate("{% if test.value.internal %}KO{% else %}OK{% endif %}");

        String result1 = jtwigTemplate.render(JtwigModel.newModel().with("test", new TestBean(null)));
        String result2 = jtwigTemplate.render(JtwigModel.newModel().with("test", new TestBean(null)));

        assertThat(result1, is("OK"));
        assertThat(result2, is("OK")); // testing cache
    }

    public static class TestBean {
        private final String value;

        public TestBean(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
