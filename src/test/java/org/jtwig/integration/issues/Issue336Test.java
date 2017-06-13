package org.jtwig.integration.issues;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class Issue336Test {
    @Test
    public void cachingPropertyResolutionWithDistinctModels () {
        JtwigTemplate template = JtwigTemplate.inlineTemplate("{{ value.key }}");

        String result1 = template.render(JtwigModel.newModel().with("value", new SubModelA()));
        String result2 = template.render(JtwigModel.newModel().with("value", new SubModelB()));

        assertThat(result1, is("A"));
        assertThat(result2, is("B"));
    }

    public abstract class AbstractModel {
        public abstract String getKey();
    }
    public class SubModelA extends AbstractModel {
        @Override
        public String getKey() {
            return "A";
        }
    }
    public class SubModelB extends AbstractModel {
        @Override
        public String getKey() {
            return "B";
        }
    }
}
