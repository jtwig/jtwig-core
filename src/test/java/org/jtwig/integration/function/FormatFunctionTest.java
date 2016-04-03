package org.jtwig.integration.function;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class FormatFunctionTest {
    @Test
    public void simpleFormat() throws Exception {

        String result = JtwigTemplate.inlineTemplate("{{ format('Hello %s', 'World') }}").render(JtwigModel.newModel());

        assertThat(result, is("Hello World"));
    }
}
