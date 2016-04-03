package org.jtwig.integration.function;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class UpperFunctionTest {
    @Test
    public void simpleUpper() throws Exception {

        String result = JtwigTemplate.inlineTemplate("{{ upper('jtwig') }}").render(JtwigModel.newModel());

        assertThat(result, is("JTWIG"));

    }
}
