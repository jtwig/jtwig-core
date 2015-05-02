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

public class EmbedTest extends AbstractIntegrationTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void simpleEmbed() throws Exception {
        JtwigTemplate template = defaultStringTemplate("{% embed 'classpath:/example/extends/extendable-template.twig' %}{% block one %}Ola{% endblock %}{% endembed %}");
        String result = template.render(JtwigModel.newModel());
        assertThat(result, is("Ola"));
    }

    @Test
    public void embedResourceNotFound() throws Exception {
        expectedException.expect(ResourceNotFoundException.class);
        expectedException.expectMessage(containsString("Resource 'one' not found"));

        defaultStringTemplate("{% embed 'one' %}{% endembed %}")
                .render(JtwigModel.newModel());
    }

    @Test
    public void embedResourceNotFoundIgnoreMissing() throws Exception {
        String result = defaultStringTemplate("{% embed 'one' ignore missing %}{% endembed %}")
                .render(JtwigModel.newModel());

        assertThat(result, is(""));
    }

    @Test
    public void simpleEmbedWithComment() throws Exception {
        JtwigTemplate template = defaultStringTemplate("{% embed 'classpath:/example/extends/extendable-template.twig' %}{# test #}{% block one %}Ola{% endblock %}{% endembed %}");
        String result = template.render(JtwigModel.newModel());
        assertThat(result, is("Ola"));
    }

    @Test
    public void embedWithInvalidInnerElement() throws Exception {
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Embed construct can only contain block elements"));

        defaultStringTemplate("{% embed 'classpath:/example/extends/extendable-template.twig' %}{% if (true) %}{% endif %}{% endembed %}")
                .render(JtwigModel.newModel());
    }

    @Test
    public void embedWithoutExpression() throws Exception {
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Embed construct missing path expression"));

        defaultStringTemplate("{% embed %}{% endembed %}")
                .render(JtwigModel.newModel());
    }

    @Test
    public void embedMissingIgnoreMissing() throws Exception {
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Did you mean 'ignore missing'?"));

        defaultStringTemplate("{% embed 'asdasd' ignore %}{% endembed %}")
                .render(JtwigModel.newModel());
    }

    @Test
    public void embedMissingEndCode() throws Exception {
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Code island not closed"));

        defaultStringTemplate("{% embed 'asdasd' {% endembed %}")
                .render(JtwigModel.newModel());
    }

    @Test
    public void embedMissingEndEmbeded() throws Exception {
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Embed construct can only contain block elements. You might be missing the endembed tag."));

        defaultStringTemplate("{% embed 'asdasd' %}")
                .render(JtwigModel.newModel());
    }

}
