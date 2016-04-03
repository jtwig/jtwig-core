package org.jtwig.integration.function;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class SplitFunctionTest {
    @Test
    public void simpleSplit() throws Exception {

        String result = JtwigTemplate.inlineTemplate("{{ split('jtwig,2',',') }}").render(JtwigModel.newModel());

        assertThat(result, is("[jtwig, 2]"));

    }
}
