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

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.Is.*;
import static org.hamcrest.core.StringContains.*;
import static org.jtwig.environment.EnvironmentConfigurationBuilder.*;
import static org.jtwig.resource.reference.ResourceReference.*;

public class EmbedTest extends AbstractIntegrationTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void simpleEmbed() throws Exception {
        JtwigTemplate template = JtwigTemplate.inlineTemplate("{% embed 'memory:a' %}{% block one %}Ola{% endblock %}{% endembed %}", configuration()
                .resources().resourceLoaders().add(new TypedResourceLoader(MEMORY, InMemoryResourceLoader.builder()
                        .withResource("a", "{% block one %}three{% endblock %}")
                        .build())).and().and()
                .build());
        String result = template.render(JtwigModel.newModel());
        assertThat(result, is("Ola"));
    }

    @Test
    public void embedResourceNotFound() throws Exception {
        expectedException.expect(ResourceNotFoundException.class);
        expectedException.expectMessage(containsString("Resource 'one' not found"));

        JtwigTemplate.inlineTemplate("{% embed 'one' %}{% endembed %}")
                .render(JtwigModel.newModel());
    }

    @Test
    public void embedResourceNotFoundIgnoreMissing() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{% embed 'one' ignore missing %}{% endembed %}")
                .render(JtwigModel.newModel());

        assertThat(result, is(""));
    }

    @Test
    public void simpleEmbedWithComment() throws Exception {
        JtwigTemplate template = JtwigTemplate.inlineTemplate("{% embed 'classpath:/example/extends/extendable-template.twig' %}{# test #}{% block one %}Ola{% endblock %}{% endembed %}");
        String result = template.render(JtwigModel.newModel());
        assertThat(result, is("Ola"));
    }

    @Test
    public void embedWithInvalidInnerElement() throws Exception {
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Embed construct can only contain block elements"));

        JtwigTemplate.inlineTemplate("{% embed 'classpath:/example/extends/extendable-template.twig' %}{% if (true) %}{% endif %}{% endembed %}")
                .render(JtwigModel.newModel());
    }

    @Test
    public void embedWithoutExpression() throws Exception {
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Embed construct missing path expression"));

        JtwigTemplate.inlineTemplate("{% embed %}{% endembed %}")
                .render(JtwigModel.newModel());
    }

    @Test
    public void embedMissingIgnoreMissing() throws Exception {
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Did you mean 'ignore missing'?"));

        JtwigTemplate.inlineTemplate("{% embed 'asdasd' ignore %}{% endembed %}")
                .render(JtwigModel.newModel());
    }

    @Test
    public void embedMissingEndCode() throws Exception {
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Code island not closed"));

        JtwigTemplate.inlineTemplate("{% embed 'asdasd' {% endembed %}")
                .render(JtwigModel.newModel());
    }

    @Test
    public void embedMissingEndEmbeded() throws Exception {
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Embed construct can only contain block elements. You might be missing the endembed tag."));

        JtwigTemplate.inlineTemplate("{% embed 'asdasd' %}")
                .render(JtwigModel.newModel());
    }

    @Test
    public void embedFallbacks() throws Exception {
        JtwigTemplate template = JtwigTemplate.inlineTemplate("{% embed ['memory:b', 'memory:a'] %}{% endembed %}", configuration()
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
    public void embedFirstAvailableOfFallbacks() throws Exception {
        JtwigTemplate template = JtwigTemplate.inlineTemplate("{% embed ['memory:b', 'memory:a'] %}{% endembed %}", configuration()
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
    public void embedFirstAvailableOfFallbacksFromIterableModel() throws Exception {
        JtwigTemplate template = JtwigTemplate.inlineTemplate("{% embed paths %}{% endembed %}", configuration()
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
    public void embedFirstAvailableOfFallbacksFromArrayModel() throws Exception {
        JtwigTemplate template = JtwigTemplate.inlineTemplate("{% embed paths %}{% endembed %}", configuration()
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
