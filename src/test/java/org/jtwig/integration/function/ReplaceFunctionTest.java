package org.jtwig.integration.function;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ReplaceFunctionTest {
    @Test
    public void simpleReplace() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ replace('one two', {two:'three'}) }}").render(JtwigModel.newModel());

        assertThat(result, is("one three"));
    }
}
