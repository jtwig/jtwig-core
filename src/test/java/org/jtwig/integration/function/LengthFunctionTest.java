package org.jtwig.integration.function;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class LengthFunctionTest {
    @Test
    public void lengthNull() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ length(null) }}").render(JtwigModel.newModel());

        assertThat(result, is("0"));
    }
    @Test
    public void lengthUndefined() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ length(var) }}").render(JtwigModel.newModel());

        assertThat(result, is("0"));
    }
    @Test
    public void lengthIterable() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ length([1,2]) }}").render(JtwigModel.newModel());

        assertThat(result, is("2"));
    }
    @Test
    public void lengthObject() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ length(1) }}").render(JtwigModel.newModel());

        assertThat(result, is("1"));
    }
    @Test
    public void lengthString() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ length('asd') }}").render(JtwigModel.newModel());

        assertThat(result, is("3"));
    }
    @Test
    public void lengthMap() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ length({asd:3, bcd:null}) }}").render(JtwigModel.newModel());

        assertThat(result, is("2"));
    }
    @Test
    public void lengthArray() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ length(var) }}").render(JtwigModel.newModel().with("var", new Object[]{1, 2}));

        assertThat(result, is("2"));
    }
}
