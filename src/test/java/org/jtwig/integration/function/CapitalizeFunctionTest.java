package org.jtwig.integration.function;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CapitalizeFunctionTest {
    @Test
    public void simpleCapitalize() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ capitalize('jtwig') }}").render(JtwigModel.newModel());

        assertThat(result, is("Jtwig"));
    }

    @Test
    public void simpleCapitalize2() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ capitalize('jtwig rocks') }}").render(JtwigModel.newModel());

        assertThat(result, is("Jtwig rocks"));
    }
    @Test
    public void capitalizeEmpty() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ capitalize('') }}").render(JtwigModel.newModel());

        assertThat(result, is(""));
    }
}
