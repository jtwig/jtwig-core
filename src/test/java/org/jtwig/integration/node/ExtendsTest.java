package org.jtwig.integration.node;

import com.google.common.base.Optional;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.integration.AbstractIntegrationTest;
import org.jtwig.parser.ParseException;
import org.jtwig.resource.Resource;
import org.jtwig.resource.StringResource;
import org.jtwig.resource.exceptions.ResourceNotFoundException;
import org.jtwig.resource.resolver.ResourceResolver;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.jtwig.environment.EnvironmentConfigurationBuilder.configuration;

public class ExtendsTest extends AbstractIntegrationTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void extendsTest() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{% extends 'a' %}{% block c %}a{% endblock %}", configuration()
                .withResourceResolver(resolvePath("a", "{% block c %}d{% endblock %}"))
                .build())
                .render(JtwigModel.newModel());

        assertThat(result, is("a"));
    }

    @Test
    public void nestedExtendsTest() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{% extends 'a' %}{% block c %}a{% endblock %}", configuration()
                .withResourceResolver(resolvePath("a", "{% extends 'b' %}{% block c %}d{% endblock %}"))
                .withResourceResolver(resolvePath("b", "{% block c %}e{% endblock %}"))
                .build())
                .render(JtwigModel.newModel());

        assertThat(result, is("a"));
    }

    @Test
    public void extendsWithSetTest() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{% extends 'a' %}{% set var = 1 %}", configuration()
                .withResourceResolver(resolvePath("a", "{% extends 'b' %}{% block c %}d{% endblock %}"))
                .withResourceResolver(resolvePath("b", "{{ var }}"))
                .build())
                .render(JtwigModel.newModel());

        assertThat(result, is("1"));
    }

    @Test
    public void simpleExtends() throws Exception {
        JtwigTemplate template = JtwigTemplate.inlineTemplate("{% extends 'classpath:/example/extends/extendable-template.twig' %}{% block one %}Override{% endblock %}");

        String result = template.render(JtwigModel.newModel());

        assertThat(result, is("Override"));
    }

    @Test
    public void extendsResourceNotFound() throws Exception {
        expectedException.expect(ResourceNotFoundException.class);
        expectedException.expectMessage(containsString("Resource 'one' not found"));

        JtwigTemplate.inlineTemplate("{% extends 'one' %}{% block one %}Override{% endblock %}")
                .render(JtwigModel.newModel());
    }

    @Test
    public void simpleExtendsWithComment() throws Exception {
        JtwigTemplate template = JtwigTemplate.inlineTemplate("{% extends 'classpath:/example/extends/extendable-template.twig' %}{# one #}{% block one %}Override{% endblock %}");

        String result = template.render(JtwigModel.newModel());

        assertThat(result, is("Override"));
    }

    @Test
    public void nestedExtends() throws Exception {
        JtwigTemplate template = JtwigTemplate.inlineTemplate("{% extends 'classpath:/example/extends/nested-extendable-template.twig' %}{% block one %}Override{% endblock %}");

        String result = template.render(JtwigModel.newModel());

        assertThat(result, is("Override"));
    }

    @Test
    public void nestedExtendsWithoutOverride() throws Exception {
        JtwigTemplate template = JtwigTemplate.inlineTemplate("{% extends 'classpath:/example/extends/nested-extendable-template.twig' %}");

        String result = template.render(JtwigModel.newModel());

        assertThat(result, is("One"));
    }

    @Test
    public void invalidExtendsBlockMissingExpression() throws Exception {
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Extends construct missing path expression"));

        JtwigTemplate.inlineTemplate("{% extends %}")
                .render(JtwigModel.newModel());
    }

    @Test
    public void invalidExtendsBlockMissingEndCode() throws Exception {
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Code island not closed"));

        JtwigTemplate.inlineTemplate("{% extends 'asdasd' ")
                .render(JtwigModel.newModel());
    }

    @Test
    public void invalidInnerElement() throws Exception {
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Extends templates only allow you to specify either block or set constructs"));

        JtwigTemplate.inlineTemplate("{% extends 'asdasd' %}{% if (true) %}{% endif %}")
                .render(JtwigModel.newModel());
    }

    private ResourceResolver resolvePath(final String path, final String content) {
        return new ResourceResolver() {
            @Override
            public Optional<Resource> resolve(Resource resource, String relativePath) {
                if (path.equals(relativePath)) {
                    return Optional.<Resource>of(new StringResource(content));
                }
                return Optional.absent();
            }
        };
    }


}
