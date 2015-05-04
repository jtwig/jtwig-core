package org.jtwig.integration.function;

import org.jtwig.JtwigModel;
import org.jtwig.integration.AbstractIntegrationTest;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class IterableFunctionTest extends AbstractIntegrationTest {
    @Test
    public void iterableWhenList() throws Exception {
        String result = defaultStringTemplate("{% if (iterable([])) %}ok{% else %}ko{% endif %}")
                .render(JtwigModel.newModel());
        assertThat(result, is("ok"));
    }
    @Test
    public void iterableWhenInteger() throws Exception {
        String result = defaultStringTemplate("{% if (iterable(1)) %}a{% else %}b{% endif %}")
                .render(JtwigModel.newModel());
        assertThat(result, is("b"));
    }
}
