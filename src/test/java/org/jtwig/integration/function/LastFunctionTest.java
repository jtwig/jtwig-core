package org.jtwig.integration.function;

import com.google.common.collect.ImmutableMap;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.junit.Test;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyMap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class LastFunctionTest {
    @Test
    public void arrayLast() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ last(var) }}").render(JtwigModel.newModel().with("var", new Object[]{1, 2, 3}));

        assertThat(result, is("3"));
    }
    @Test
    public void emptyArrayLast() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ last(var) }}").render(JtwigModel.newModel().with("var", new Object[]{}));

        assertThat(result, is(""));
    }

    @Test
    public void lastString() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ last(var) }}").render(JtwigModel.newModel().with("var", "abcde"));

        assertThat(result, is("e"));
    }

    @Test
    public void emptyLastString() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ last(var) }}").render(JtwigModel.newModel().with("var", ""));

        assertThat(result, is(""));
    }

    @Test
    public void lastIterable() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ last(var) }}").render(JtwigModel.newModel().with("var", asList(1, 2, 3)));

        assertThat(result, is("3"));
    }

    @Test
    public void emptyLastIterable() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ last(var) }}").render(JtwigModel.newModel().with("var", asList()));

        assertThat(result, is(""));
    }

    @Test
    public void lastMap() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ last(var) }}").render(JtwigModel.newModel().with("var", ImmutableMap.builder()
                .put("a", 1)
                .build()));

        assertThat(result, is("1"));
    }

    @Test
    public void emptyLastMap() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ last(var) }}").render(JtwigModel.newModel().with("var", emptyMap()));

        assertThat(result, is(""));
    }

    @Test
    public void lastAnother() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ last(1) }}").render(JtwigModel.newModel());

        assertThat(result, is("1"));
    }
}
