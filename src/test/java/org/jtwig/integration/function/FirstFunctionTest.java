package org.jtwig.integration.function;

import com.google.common.collect.ImmutableMap;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.junit.Test;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyMap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class FirstFunctionTest {
    @Test
    public void arrayFirst() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ first(var) }}").render(JtwigModel.newModel().with("var", new Object[]{1, 2, 3}));

        assertThat(result, is("1"));
    }
    @Test
    public void emptyArrayFirst() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ first(var) }}").render(JtwigModel.newModel().with("var", new Object[]{}));

        assertThat(result, is(""));
    }

    @Test
    public void firstString() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ first(var) }}").render(JtwigModel.newModel().with("var", "abcde"));

        assertThat(result, is("a"));
    }

    @Test
    public void emptyFirstString() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ first(var) }}").render(JtwigModel.newModel().with("var", ""));

        assertThat(result, is(""));
    }

    @Test
    public void firstIterable() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ first(var) }}").render(JtwigModel.newModel().with("var", asList(1, 2, 3)));

        assertThat(result, is("1"));
    }

    @Test
    public void emptyFirstIterable() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ first(var) }}").render(JtwigModel.newModel().with("var", asList()));

        assertThat(result, is(""));
    }

    @Test
    public void firstMap() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ first(var) }}").render(JtwigModel.newModel().with("var", ImmutableMap.builder()
                .put("a", 1)
                .build()));

        assertThat(result, is("1"));
    }

    @Test
    public void emptyFirstMap() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ first(var) }}").render(JtwigModel.newModel().with("var", emptyMap()));

        assertThat(result, is(""));
    }

    @Test
    public void firstAnother() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ first(1) }}").render(JtwigModel.newModel());

        assertThat(result, is("1"));
    }
}
