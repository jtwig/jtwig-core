package org.jtwig.integration.function;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ConcatFunctionTest {
    @Test
    public void simpleConcat() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ concat('hello', ' world') }}").render(JtwigModel.newModel());

        assertThat(result, is("hello world"));
    }
    @Test
    public void concatWithNulls() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ concat('hello', null, ' world') }}").render(JtwigModel.newModel());

        assertThat(result, is("hello world"));
    }
}
