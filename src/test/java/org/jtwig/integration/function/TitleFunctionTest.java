package org.jtwig.integration.function;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class TitleFunctionTest {
    @Test
    public void simpleTitle() throws Exception {

        String result = JtwigTemplate.inlineTemplate("{{ title('hello world') }}").render(JtwigModel.newModel());

        assertThat(result, is("Hello World"));

    }
}
