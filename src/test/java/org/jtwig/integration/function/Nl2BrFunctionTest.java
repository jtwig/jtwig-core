package org.jtwig.integration.function;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class Nl2BrFunctionTest {
    @Test
    public void nl2brSimple() throws Exception {

        String result = JtwigTemplate.inlineTemplate("{{ nl2br(var) }}").render(JtwigModel.newModel().with("var", "\n"));

        assertThat(result, is("<br />"));

    }
}
