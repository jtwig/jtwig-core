package org.jtwig.integration.expression;

import org.jtwig.JtwigModel;
import org.jtwig.integration.AbstractIntegrationTest;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class TernaryOperatorTest extends AbstractIntegrationTest {
    @Test
    public void testTrue() throws Exception {
        JtwigModel model = new JtwigModel();

        String result = defaultStringTemplate("{{ true ? 1 : 2 }}")
                .render(model);

        assertThat(result, is(equalTo("1")));
    }

    @Test
    public void testTemplateTrue() throws Exception {
        JtwigModel model = new JtwigModel();
        model.with("value", true);

        String result = defaultStringTemplate("{{ value ? '1' : '2' }}")
                .render(model);

        assertThat(result, is(equalTo("1")));
    }

    @Test
    public void testFalse() throws Exception {
        JtwigModel model = new JtwigModel();

        String result = defaultStringTemplate("{{ false ? 1 : 2 }}")
                .render(model);

        assertThat(result, is(equalTo("2")));
    }
}
