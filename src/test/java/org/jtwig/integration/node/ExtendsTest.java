package org.jtwig.integration.node;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.integration.AbstractIntegrationTest;
import org.jtwig.parser.ParseException;
import org.jtwig.resource.exceptions.ResourceNotFoundException;
import org.jtwig.resource.loader.InMemoryResourceLoader;
import org.jtwig.resource.loader.TypedResourceLoader;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.Is.*;
import static org.hamcrest.core.StringContains.*;
import static org.jtwig.environment.EnvironmentConfigurationBuilder.*;
import static org.jtwig.resource.reference.ResourceReference.*;

public class ExtendsTest extends AbstractIntegrationTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void extendsTest() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{% extends 'memory:a' %}{% block c %}a{% endblock %}", configuration()
                .resources().resourceLoaders().add(new TypedResourceLoader(MEMORY, InMemoryResourceLoader.builder()
                        .withResource("a", "{% block c %}d{% endblock %}")
                        .build())).and().and()
                .build())
                .render(JtwigModel.newModel());

        assertThat(result, is("a"));
    }

    @Test
    public void nestedExtendsTest() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{% extends 'memory:a' %}{% block c %}a{% endblock %}", configuration()
                .resources().resourceLoaders().add(new TypedResourceLoader(MEMORY, InMemoryResourceLoader.builder()
                        .withResource("a", "{% extends 'memory:b' %}{% block c %}d{% endblock %}")
                        .withResource("b", "{% block c %}e{% endblock %}")
                        .build())).and().and()
                .build())
                .render(JtwigModel.newModel());

        assertThat(result, is("a"));
    }

    @Test
    public void extendsWithSetTest() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{% extends 'memory:a' %}{% set var = 1 %}",
                configuration()
                        .resources().resourceLoaders().add(new TypedResourceLoader(MEMORY, InMemoryResourceLoader.builder()
                        .withResource("a", "{% extends 'memory:b' %}{% block c %}d{% endblock %}")
                        .withResource("b", "{{ var }}")
                        .build())).and().and()
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


    @Test
    public void extendsInsideFor() throws Exception {

        String result = JtwigTemplate.inlineTemplate("{% extends 'memory:a' %}" +
                "{% block post %}- {{ item.title }}{% endblock %}", configuration()
                .resources().resourceLoaders().add(new TypedResourceLoader(MEMORY, InMemoryResourceLoader.builder()
                        .withResource("a", "{% for item in posts %}" +
                                "{% block post %}{{ item.title }}{% endblock %}" +
                                "{% endfor %}")
                        .build())).and().and()
                .build())
                .render(JtwigModel.newModel().with("posts", asList(
                        new Item("a"),
                        new Item("b")
                )));

        assertThat(result, is("- a- b"));
    }

    public static class Item {
        private final String title;

        public Item(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }

    @Test
    public void extendFallbacks() throws Exception {
        JtwigTemplate template = JtwigTemplate.inlineTemplate("{% extends ['memory:b', 'memory:a'] %}", configuration()
                .resources().resourceLoaders().add(new TypedResourceLoader(MEMORY, InMemoryResourceLoader
                        .builder()
                        .withResource("a", "{% block one %}{{ 'a' }}{% endblock %}")
                        .build())).and().and()
                .build()
        );

        String result = template.render(JtwigModel.newModel());

        assertThat(result, is("a"));
    }

    @Test
    public void extendFirstAvailableOfFallbacks() throws Exception {
        JtwigTemplate template = JtwigTemplate.inlineTemplate("{% extends ['memory:b', 'memory:a'] %}", configuration()
                .resources().resourceLoaders().add(new TypedResourceLoader(MEMORY, InMemoryResourceLoader
                        .builder()
                        .withResource("a", "{% block one %}{{ 'a' }}{% endblock %}")
                        .withResource("b", "{% block one %}{{ 'b' }}{% endblock %}")
                        .build())).and().and()
                .build()
        );

        String result = template.render(JtwigModel.newModel());

        assertThat(result, is("b"));
    }

    @Test
    public void extendFirstAvailableOfFallbacksFromIterableModel() throws Exception {
        JtwigTemplate template = JtwigTemplate.inlineTemplate("{% extends paths %}", configuration()
                .resources().resourceLoaders().add(new TypedResourceLoader(MEMORY, InMemoryResourceLoader
                        .builder()
                        .withResource("a", "{% block one %}{{ 'a' }}{% endblock %}")
                        .withResource("b", "{% block one %}{{ 'b' }}{% endblock %}")
                        .build())).and().and()
                .build()
        );

        List<String> pathList = new ArrayList<>();
        pathList.add("memory:b");
        pathList.add("memory:a");

        Map<String, Object> modelData = new HashMap<>();
        modelData.put("paths", pathList);

        String result = template.render(JtwigModel.newModel(modelData));

        assertThat(result, is("b"));
    }

    @Test
    public void extendFirstAvailableOfFallbacksFromArrayModel() throws Exception {
        JtwigTemplate template = JtwigTemplate.inlineTemplate("{% extends paths %}", configuration()
                .resources().resourceLoaders().add(new TypedResourceLoader(MEMORY, InMemoryResourceLoader
                        .builder()
                        .withResource("a", "{% block one %}{{ 'a' }}{% endblock %}")
                        .withResource("b", "{% block one %}{{ 'b' }}{% endblock %}")
                        .build())).and().and()
                .build()
        );

        Map<String, Object> modelData = new HashMap<>();
        modelData.put("paths", new String[] {"memory:b", "memory:a"});

        String result = template.render(JtwigModel.newModel(modelData));

        assertThat(result, is("b"));
    }
}
