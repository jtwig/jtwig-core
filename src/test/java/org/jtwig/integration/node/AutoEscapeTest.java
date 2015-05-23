package org.jtwig.integration.node;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.integration.AbstractIntegrationTest;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class AutoEscapeTest extends AbstractIntegrationTest {
    @Test
    public void simpleAutoEscape() throws Exception {
        JtwigTemplate template = JtwigTemplate.inlineTemplate("{% autoescape %}&{% endautoescape %}");
        String result = template.render(JtwigModel.newModel());
        assertThat(result, is("&amp;"));
    }

    @Test
    public void simpleAutoEscapeWithInnerRaw() throws Exception {
        JtwigTemplate template = JtwigTemplate.inlineTemplate("{% autoescape %}&{{ '&' | raw }}{% endautoescape %}");
        String result = template.render(JtwigModel.newModel());
        assertThat(result, is("&amp;&"));
    }

    @Test
    public void nestedAutoEscape() throws Exception {
        JtwigTemplate template = JtwigTemplate.inlineTemplate("{% autoescape 'js' %}&{% autoescape %}&{% endautoescape %}&{% endautoescape %}");
        String result = template.render(JtwigModel.newModel());
        assertThat(result, is("&&amp;&"));
    }

    @Test
    public void nestedAutoEscapeFalse() throws Exception {
        JtwigTemplate template = JtwigTemplate.inlineTemplate("{% autoescape 'js' %}&{% autoescape false %}&{% endautoescape %}&{% endautoescape %}");
        String result = template.render(JtwigModel.newModel());
        assertThat(result, is("&&&"));
    }
}
