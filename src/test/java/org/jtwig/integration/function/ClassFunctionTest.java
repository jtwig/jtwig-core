package org.jtwig.integration.function;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ClassFunctionTest {
    @Test
    public void resolveClass() {
        String result = JtwigTemplate.inlineTemplate("{{ class('test') }}").render(JtwigModel.newModel());

        assertThat(result, is("class java.lang.String"));
    }

    @Test
    public void resolveClassWhenNull() {
        String result = JtwigTemplate.inlineTemplate("{{ class(null) }}").render(JtwigModel.newModel());

        assertThat(result, is(""));
    }

    @Test
    public void resolveClassWhenUndefined() {
        String result = JtwigTemplate.inlineTemplate("{{ class(value) }}").render(JtwigModel.newModel());

        assertThat(result, is(""));
    }
}
