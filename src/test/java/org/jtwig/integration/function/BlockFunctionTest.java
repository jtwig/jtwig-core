package org.jtwig.integration.function;

import com.google.common.base.Optional;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.environment.Environment;
import org.jtwig.integration.AbstractIntegrationTest;
import org.jtwig.resource.Resource;
import org.jtwig.resource.StringResource;
import org.jtwig.resource.resolver.ResourceResolver;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.jtwig.environment.EnvironmentConfigurationBuilder.configuration;

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
                .resources().resourceResolvers().add(new ResourceResolver() {
                    @Override
                    public Optional<Resource> resolve(Environment env, Resource resource, String relativePath) {
                        if (relativePath.equals("a")) {
                            return Optional.<Resource>of(new StringResource("{% block one %}Hello{% endblock %}{{ block('one') }}"));
                        }
                        return Optional.absent();
                    }
                }).and().and()
                .build())
                .render(JtwigModel.newModel());

        assertThat(result, is("aa"));
    }
}
