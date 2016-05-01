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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.jtwig.JtwigModel.newModel;
import static org.jtwig.environment.EnvironmentConfigurationBuilder.configuration;
import static org.jtwig.resource.reference.ResourceReference.MEMORY;

public class IncludeTest extends AbstractIntegrationTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void includingResourceFile() throws Exception {
        JtwigTemplate template = JtwigTemplate.inlineTemplate("{% include 'classpath:/example/classpath-template.twig' %}");

        String result = template.render(newModel());

        assertThat(result, is("Hello"));
    }

    @Test
    public void includeWithoutExportingModel() throws Exception {
        JtwigTemplate template = JtwigTemplate.inlineTemplate("{% include 'memory:a' only %}",
                configuration()
                        .resources().resourceLoaders().add(new TypedResourceLoader(MEMORY, InMemoryResourceLoader.builder()
                        .withResource("a", "{{ name }}")
                        .build())).and().and()
                        .build()
        );
        String result = template.render(newModel().with("name", "Hello"));

        assertThat(result, is(""));
    }

    @Test
    public void includeWithoutExportingModelButIncluding() throws Exception {
        JtwigTemplate template = JtwigTemplate.inlineTemplate("{% include 'memory:a' with { name: 'Joao' } only %}",
                configuration()
                        .resources().resourceLoaders().add(new TypedResourceLoader(MEMORY, InMemoryResourceLoader.builder()
                        .withResource("a", "{{ name }}")
                        .build())).and().and()
                        .build()
        );

        String result = template.render(newModel().with("name", "Hello"));

        assertThat(result, is("Joao"));
    }

    @Test
    public void includeShouldExportModel() throws Exception {
        JtwigTemplate template = JtwigTemplate.inlineTemplate("{% include 'memory:a' %}", configuration()
                .resources().resourceLoaders().add(new TypedResourceLoader(MEMORY, InMemoryResourceLoader.builder()
                        .withResource("a", "{{ name }}")
                        .build())).and().and()
                .build()
        );

        String result = template.render(newModel().with("name", "Hello"));

        assertThat(result, is("Hello"));
    }

    @Test
    public void includeShouldExportModelAndExtraData() throws Exception {
        JtwigTemplate template = JtwigTemplate.inlineTemplate("{% include 'memory:a' with { joao: 'Melo' } %}", configuration()
                .resources().resourceLoaders().add(new TypedResourceLoader(MEMORY, InMemoryResourceLoader.builder()
                        .withResource("a", "{{ name }} {{ joao }}")
                        .build())).and().and()
                .build()
        );

        String result = template.render(newModel().with("name", "Hello"));

        assertThat(result, is("Hello Melo"));
    }

    @Test
    public void includeShouldNotRedefineModelVariable() throws Exception {
        JtwigTemplate template = JtwigTemplate.inlineTemplate("{% include 'memory:a' %}{{ name }}", configuration()
                .resources().resourceLoaders().add(new TypedResourceLoader(MEMORY, InMemoryResourceLoader.builder()
                        .withResource("a", "{% set name = 'test' %}")
                        .build())).and().and()
                .build()
        );

        String result = template.render(newModel().with("name", "Hello"));

        assertThat(result, is("Hello"));
    }

    @Test
    public void includeShouldNotExposeNewModelVariable() throws Exception {
        JtwigTemplate template = JtwigTemplate.inlineTemplate("{% include 'memory:a' %}{{ var }}",configuration()
                .resources().resourceLoaders().add(new TypedResourceLoader(MEMORY, InMemoryResourceLoader.builder()
                        .withResource("a", "{% set var = 'test' %}")
                        .build())).and().and()
                .build()
        );

        String result = template.render(newModel());

        assertThat(result, is(""));
    }

    @Test
    public void includeResourceNotFound() throws Exception {
        expectedException.expect(ResourceNotFoundException.class);
        expectedException.expectMessage(containsString("Resource 'one' not found"));

        JtwigTemplate.inlineTemplate("{% include 'one' %}")
                .render(JtwigModel.newModel());
    }

    @Test
    public void includeResourceNotFoundIgnoreMissing() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{% include 'one' ignore missing %}")
                .render(JtwigModel.newModel());


        assertThat(result, is(""));
    }

    @Test
    public void includingResourceRelativeFile() throws Exception {
        JtwigTemplate template = JtwigTemplate.inlineTemplate("{% include 'classpath:/example/classpath-include.twig' %}");

        String result = template.render(newModel());

        assertThat(result, is("Hello World"));
    }

    @Test
    public void includeMissingExpression() throws Exception {
        JtwigTemplate template = JtwigTemplate.inlineTemplate("{% include %}");
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Include missing path expression"));

        template.render(newModel());
    }

    @Test
    public void includeMissingEndCode() throws Exception {
        JtwigTemplate template = JtwigTemplate.inlineTemplate("{% include 'bla' ");
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Code island not closed"));

        template.render(newModel());
    }

    @Test
    public void includeMissingIgnoreMissing() throws Exception {
        JtwigTemplate template = JtwigTemplate.inlineTemplate("{% include 'bla' ignore %}");
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Did you mean 'ignore missing'?"));

        template.render(newModel());
    }

    @Test
    public void includeMissingMapOfValues() throws Exception {
        JtwigTemplate template = JtwigTemplate.inlineTemplate("{% include 'bla' with %}");
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Expecting map of values"));

        template.render(newModel());
    }
}
