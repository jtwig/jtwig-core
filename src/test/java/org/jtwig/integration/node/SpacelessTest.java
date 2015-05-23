package org.jtwig.integration.node;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.integration.AbstractIntegrationTest;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class SpacelessTest extends AbstractIntegrationTest {
    @Test
    public void simpleSpaceless() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{% spaceless %}<div>\n" +
                "        <strong>foo</strong>\n" +
                "    </div>{% endspaceless %}").render(JtwigModel.newModel());
        assertThat(result, is("<div><strong>foo</strong></div>"));
    }

    @Test
    public void spacelessNestedWithAutoEspace() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{% autoescape 'html' %}" +
                "{% spaceless %}" +
                "{% autoescape 'html' %}&amp;{% endautoescape %}" +
                "{% endspaceless %}" +
                "{% endautoescape %}").render(JtwigModel.newModel());

        assertThat(result, is("&amp;amp;"));
    }
}
