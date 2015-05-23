package org.jtwig.integration.function;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.integration.AbstractIntegrationTest;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

public class JsonEncodeTest extends AbstractIntegrationTest {
    @Test
    public void name() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ variable | json_encode }}")
                .render(JtwigModel.newModel().with("variable", new TestEntity().setName("hello")));

        assertThat(result, not(is("")));
    }

    public static class TestEntity {
        private String name;

        public String getName() {
            return name;
        }

        public TestEntity setName(String name) {
            this.name = name;
            return this;
        }
    }
}
