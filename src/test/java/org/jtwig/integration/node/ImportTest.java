package org.jtwig.integration.node;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.integration.AbstractIntegrationTest;
import org.jtwig.parser.ParseException;
import org.jtwig.resource.exceptions.ResourceNotFoundException;
import org.jtwig.resource.loader.InMemoryResourceLoader;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.jtwig.environment.EnvironmentConfigurationBuilder.configuration;
import static org.jtwig.resource.reference.ResourceReference.MEMORY;

public class ImportTest extends AbstractIntegrationTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void simpleImport() throws Exception {
        JtwigTemplate template = JtwigTemplate.inlineTemplate("{% import 'classpath:/example/macros/macro-example.twig' as inputs %}{{ inputs.text('hello') }}");

        String result = template.render(JtwigModel.newModel());

        assertThat(result, is("hello"));
    }

    @Test
    public void macroImportLessArguments() throws Exception {
        JtwigTemplate template = JtwigTemplate.inlineTemplate("{% import 'classpath:/example/macros/macro-example.twig' as inputs %}{{ inputs.text() }}");

        String result = template.render(JtwigModel.newModel());

        assertThat(result, is(""));
    }

    @Test
    public void nestedImports() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{% import 'memory:a' as a %}{{ a.example('hello') }}",
                configuration()
                        .resources().resourceLoaders().add(MEMORY, InMemoryResourceLoader.builder()
                        .withResource("a", "{% import 'memory:b' as b %}{% macro example (input) %}{{ b.example(input) }}{% endmacro %}")
                        .withResource("b", "{% macro example (input) %}{{ input }}{% endmacro %}")
                        .build()).and().and()
                        .build())
                .render(JtwigModel.newModel());

        assertThat(result, is("hello"));
    }

    @Test
    public void importResourceNotFound() throws Exception {
        expectedException.expect(ResourceNotFoundException.class);
        expectedException.expectMessage(containsString("Resource 'one' not found"));

        JtwigTemplate.inlineTemplate("{% import 'one' as blah %}")
                .render(JtwigModel.newModel());
    }

    @Test
    public void importMissingExpression() throws Exception {
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Missing import path expression"));

        JtwigTemplate.inlineTemplate("{% import as inputs %}{{ inputs.text('hello') }}")
                .render(JtwigModel.newModel());

    }

    @Test
    public void importMissingAs() throws Exception {
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Wrong syntax expecting token 'as'"));

        JtwigTemplate.inlineTemplate("{% import 'asd' inputs %}{{ inputs.text('hello') }}")
                .render(JtwigModel.newModel());

    }

    @Test
    public void importMissingVariable() throws Exception {
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Missing alias declaration"));

        JtwigTemplate.inlineTemplate("{% import 'asd' as %}{{ inputs.text('hello') }}")
                .render(JtwigModel.newModel());

    }

    @Test
    public void importMissingEndCodeIsland() throws Exception {
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Code island not closed"));

        JtwigTemplate.inlineTemplate("{% import 'asd' as inputs{{ inputs.text('hello') }}")
                .render(JtwigModel.newModel());
    }
}
