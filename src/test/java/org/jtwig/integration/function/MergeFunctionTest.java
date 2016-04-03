package org.jtwig.integration.function;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class MergeFunctionTest {
    @Test
    public void simpleMerge() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ [1, 2, 3] | merge([4,5]) }}").render(JtwigModel.newModel());

        assertThat(result, is("[1, 2, 3, 4, 5]"));
    }
}
