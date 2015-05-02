package org.jtwig.integration;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.parser.ParseException;
import org.jtwig.resource.exceptions.ResourceNotFoundException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ExtendsTest extends AbstractIntegrationTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void simpleExtends() throws Exception {
        JtwigTemplate template = defaultStringTemplate("{% extends 'classpath:/example/extends/extendable-template.twig' %}{% block one %}Override{% endblock %}");

        String result = template.render(JtwigModel.newModel());

        assertThat(result, is("Override"));
    }

    @Test
    public void extendsResourceNotFound() throws Exception {
        expectedException.expect(ResourceNotFoundException.class);
        expectedException.expectMessage(containsString("Resource 'one' not found"));

        defaultStringTemplate("{% extends 'one' %}{% block one %}Override{% endblock %}")
                .render(JtwigModel.newModel());
    }

    @Test
    public void simpleExtendsWithComment() throws Exception {
        JtwigTemplate template = defaultStringTemplate("{% extends 'classpath:/example/extends/extendable-template.twig' %}{# one #}{% block one %}Override{% endblock %}");

        String result = template.render(JtwigModel.newModel());

        assertThat(result, is("Override"));
    }

    @Test
    public void nestedExtends() throws Exception {
        JtwigTemplate template = defaultStringTemplate("{% extends 'classpath:/example/extends/nested-extendable-template.twig' %}{% block one %}Override{% endblock %}");

        String result = template.render(JtwigModel.newModel());

        assertThat(result, is("Override"));
    }

    @Test
    public void nestedExtendsWithoutOverride() throws Exception {
        JtwigTemplate template = defaultStringTemplate("{% extends 'classpath:/example/extends/nested-extendable-template.twig' %}");

        String result = template.render(JtwigModel.newModel());

        assertThat(result, is("One"));
    }

    @Test
    public void invalidExtendsBlockMissingExpression() throws Exception {
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Extends construct missing path expression"));

        defaultStringTemplate("{% extends %}")
                .render(JtwigModel.newModel());
    }

    @Test
    public void invalidExtendsBlockMissingEndCode() throws Exception {
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Code island not closed"));

        defaultStringTemplate("{% extends 'asdasd' ")
                .render(JtwigModel.newModel());
    }

    @Test
    public void invalidInnerElement() throws Exception {
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Extends templates only allow you to specify either block or set constructs"));

        defaultStringTemplate("{% extends 'asdasd' %}{% if (true) %}{% endif %}")
                .render(JtwigModel.newModel());
    }


}
