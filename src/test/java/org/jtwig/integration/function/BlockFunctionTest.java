package org.jtwig.integration.function;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.integration.AbstractIntegrationTest;
import org.jtwig.resource.loader.InMemoryResourceLoader;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.jtwig.environment.EnvironmentConfigurationBuilder.configuration;
import static org.jtwig.resource.reference.ResourceReference.MEMORY;

public class BlockFunctionTest extends AbstractIntegrationTest {
    @Test
    public void block() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{% block one %}Hello{% endblock %}{{ block('one') }}")
                .render(JtwigModel.newModel());

        assertThat(result, is("HelloHello"));
    }

    @Test
    public void blockIfNotFound() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ block('one') }}")
                .render(JtwigModel.newModel());

        assertThat(result, is(""));
    }

    @Test
    public void blockWithExtends() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{% extends 'a' %}{% block one %}a{% endblock %}", configuration()
                .resources().resourceLoaders().add(MEMORY, InMemoryResourceLoader.builder()
                        .withResource("a", "{% block one %}Hello{% endblock %}{{ block('one') }}")
                        .build()).and().and()
                .build())
                .render(JtwigModel.newModel());

        assertThat(result, is("aa"));
    }
}
