package org.jtwig.integration.issues;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.resource.loader.InMemoryResourceLoader;
import org.jtwig.resource.loader.TypedResourceLoader;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.jtwig.environment.EnvironmentConfigurationBuilder.configuration;
import static org.jtwig.resource.reference.ResourceReference.MEMORY;

public class Issue297Test {
    @Test
    public void extendsWithSet() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{% extends 'memory:a' %}{% set var = 'one' %}{% block c %}{{ var }}{% endblock %}", configuration()
                .resources().resourceLoaders().add(new TypedResourceLoader(MEMORY, InMemoryResourceLoader.builder()
                        .withResource("a", "{% block c %}d{% endblock %}")
                        .build())).and().and()
                .build())
                .render(JtwigModel.newModel());

        assertThat(result, is("one"));
    }
}
