package org.jtwig.integration.function;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class StripTagsFunctionTest {
    @Test
    public void simpleStripTags() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ striptags('<a>jtwig</a>') }}").render(JtwigModel.newModel());

        assertThat(result, is("jtwig"));
    }
    @Test
    public void simpleStripTagsWithIgnore() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ striptags('<a>jtwig</a>', 'a') }}").render(JtwigModel.newModel());

        assertThat(result, is("<a>jtwig</a>"));
    }
}
