package org.jtwig.integration.expression;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ContentEscapeTest {
    @Test
    public void example() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{% contentescape %}&{% endcontentescape %}")
                .render(JtwigModel.newModel());

        assertThat(result, is("&amp;"));
    }
}
