package org.jtwig.integration.function;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.AnyOf.anyOf;
import static org.hamcrest.core.Is.is;

public class UrlEncodeFunctionTest {
    @Test
    public void simpleUrl() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ url_encode({id: 1, special: '&'}) }}").render(JtwigModel.newModel());

        assertThat(result, anyOf(equalTo("id=1&special=%26"), equalTo("special=%26&id=1")));
    }
    @Test
    public void urlString() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ url_encode('one&two') }}").render(JtwigModel.newModel());

        assertThat(result, is("one%26two"));
    }
}
