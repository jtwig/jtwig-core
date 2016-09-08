package org.jtwig.integration.expression.binary;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class MatchesComparatorTest {
    @Test
    public void matchesUnhappy() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ 'one' matches '[a-z]{2}' }}")
                .render(JtwigModel.newModel());

        assertThat(result, is("false"));
    }

    @Test
    public void matchesHappy() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ 'one' matches '[a-z]{3}' }}")
                .render(JtwigModel.newModel());

        assertThat(result, is("true"));
    }
}
