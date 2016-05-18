package org.jtwig.integration.function;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.integration.AbstractIntegrationTest;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.jtwig.environment.EnvironmentConfigurationBuilder.configuration;

public class DefinedFunctionTest extends AbstractIntegrationTest {
    @Test
    public void definedWhenUndefined() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{% if (defined(asd)) %}ko{% else %}ok{% endif %}").render(JtwigModel.newModel());
        assertThat(result, is("ok"));
    }

    @Test
    public void definedWhenUndefinedWithStrictMode() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{% if (defined(asd)) %}ko{% else %}ok{% endif %}", configuration()
                .render().withStrictMode(true).and()
                .build()).render(JtwigModel.newModel());
        assertThat(result, is("ok"));
    }

    @Test
    public void definedWhenNull() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{% if (defined(null)) %}ok{% endif %}").render(JtwigModel.newModel());
        assertThat(result, is("ok"));
    }
}
