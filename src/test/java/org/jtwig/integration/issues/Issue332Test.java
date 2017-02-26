package org.jtwig.integration.issues;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class Issue332Test {
    @Test
    public void reproduceIssue() throws Exception {

        String result = JtwigTemplate.inlineTemplate("{% autoescape 'html' %}&{{ '&' }}{% endautoescape %}")
                .render(JtwigModel.newModel());

        assertThat(result, is("&&amp;"));
    }
}
