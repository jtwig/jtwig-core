package org.jtwig.functions.impl.map;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class KeysFunctionTest {
    @Test
    public void keysSimple() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ keys({'1':'one', '2':'two'}) }}").render(JtwigModel.newModel());

        assertThat(result, is("[1, 2]"));
    }
}