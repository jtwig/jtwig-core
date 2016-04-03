package org.jtwig.integration.function;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class JoinFunctionTest {
    @Test
    public void simpleJoin() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ [1,2,3] | join }}").render(JtwigModel.newModel());

        assertThat(result, is("123"));
    }

    @Test
    public void joinWithSeparator() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ [1,2,3] | join(', ') }}").render(JtwigModel.newModel());

        assertThat(result, is("1, 2, 3"));
    }

    @Test
    public void joinWithNullsSeparator() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ [1,2,null,3] | join(', ') }}").render(JtwigModel.newModel());

        assertThat(result, is("1, 2, 3"));
    }
}
