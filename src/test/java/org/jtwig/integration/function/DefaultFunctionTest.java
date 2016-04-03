package org.jtwig.integration.function;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class DefaultFunctionTest {
    @Test
    public void simpleDefault() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ default(null, 'hi') }}").render(JtwigModel.newModel());

        assertThat(result, is("hi"));
    }
    @Test
    public void simpleDefaultUndefined() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ default(var, 'hi') }}").render(JtwigModel.newModel());

        assertThat(result, is("hi"));
    }
    @Test
    public void defaultNotNull() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ default(1, 'hi') }}").render(JtwigModel.newModel());

        assertThat(result, is("1"));
    }
}
