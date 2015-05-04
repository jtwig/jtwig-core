package org.jtwig.integration.function;

import org.jtwig.JtwigModel;
import org.jtwig.integration.AbstractIntegrationTest;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class OddEvenFunctionTest extends AbstractIntegrationTest {
    @Test
    public void oddWhenOdd() throws Exception {
        String result = defaultStringTemplate("{% if (odd(1)) %}ok{% endif %}")
                .render(JtwigModel.newModel());

        assertThat(result, is("ok"));
    }

    @Test
    public void oddWhenEven() throws Exception {
        String result = defaultStringTemplate("{% if (odd(2)) %}ko{% else %}ok{% endif %}")
                .render(JtwigModel.newModel());

        assertThat(result, is("ok"));
    }

    @Test
    public void evenWhenOdd() throws Exception {
        String result = defaultStringTemplate("{% if (even(1)) %}ko{% else %}ok{% endif %}")
                .render(JtwigModel.newModel());

        assertThat(result, is("ok"));
    }

    @Test
    public void evenWhenEven() throws Exception {
        String result = defaultStringTemplate("{% if (even(2)) %}ok{% else %}ko{% endif %}")
                .render(JtwigModel.newModel());

        assertThat(result, is("ok"));
    }
}
