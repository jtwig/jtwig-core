package org.jtwig.integration.function;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ConvertEncodingFunctionTest {
    @Test
    public void simpleConvertEncoding() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ convert_encoding('hello', 'ASCII', 'UTF-8') }}").render(JtwigModel.newModel());

        assertThat(result, is("hello"));
    }
}
