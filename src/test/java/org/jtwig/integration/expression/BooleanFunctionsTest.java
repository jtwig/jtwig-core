package org.jtwig.integration.expression;

import org.jtwig.JtwigModel;
import org.jtwig.integration.AbstractIntegrationTest;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class BooleanFunctionsTest extends AbstractIntegrationTest {
    @Test
    public void definedWhenUndefined() throws Exception {
        String result = defaultStringTemplate("{% if (defined(asd)) %}ko{% else %}ok{% endif %}").render(JtwigModel.newModel());
        assertThat(result, is("ok"));
    }

    @Test
    public void definedWhenNull() throws Exception {
        String result = defaultStringTemplate("{% if (defined(null)) %}ok{% endif %}").render(JtwigModel.newModel());
        assertThat(result, is("ok"));
    }
}
