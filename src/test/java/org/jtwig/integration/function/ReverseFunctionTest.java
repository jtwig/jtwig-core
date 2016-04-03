package org.jtwig.integration.function;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ReverseFunctionTest {
    @Test
    public void simpleReverse() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ reverse('abc') }}").render(JtwigModel.newModel());

        assertThat(result, is("cba"));
    }
    @Test
    public void reverseArray() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ reverse(var)[0] }}").render(JtwigModel.newModel().with("var", new Object[]{1,2}));

        assertThat(result, is("2"));
    }
    @Test
    public void reverseList() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ reverse(var) }}").render(JtwigModel.newModel().with("var", asList(1,2)));

        assertThat(result, is("[2, 1]"));
    }
    @Test
    public void reverseObject() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ reverse(123) }}").render(JtwigModel.newModel());

        assertThat(result, is("123"));
    }
}
