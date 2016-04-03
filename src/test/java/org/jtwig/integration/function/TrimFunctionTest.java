package org.jtwig.integration.function;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class TrimFunctionTest {
    @Test
    public void simpleTrim() throws Exception {

        String result = JtwigTemplate.inlineTemplate("{{ trim(' jtwig ') }}").render(JtwigModel.newModel());

        assertThat(result, is("jtwig"));

    }

    @Test
    public void trimNull() throws Exception {

        String result = JtwigTemplate.inlineTemplate("{{ trim(null) }}").render(JtwigModel.newModel());

        assertThat(result, is(""));

    }
}
