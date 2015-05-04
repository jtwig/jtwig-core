package org.jtwig.integration.node;

import org.hamcrest.CoreMatchers;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.integration.AbstractIntegrationTest;
import org.jtwig.parser.ParseException;
import org.jtwig.resource.exceptions.ResourceNotFoundException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringContains.containsString;

public class ImportTest extends AbstractIntegrationTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void simpleImport() throws Exception {
        JtwigTemplate template = defaultStringTemplate("{% import 'classpath:/example/macros/macro-example.twig' as inputs %}{{ inputs.text('hello') }}");

        String result = template.render(JtwigModel.newModel());

        assertThat(result, is("hello"));
    }



    @Test
    public void importResourceNotFound() throws Exception {
        expectedException.expect(ResourceNotFoundException.class);
        expectedException.expectMessage(CoreMatchers.containsString("Resource 'one' not found"));

        defaultStringTemplate("{% import 'one' as blah %}")
                .render(JtwigModel.newModel());
    }

    @Test
    public void importMissingExpression() throws Exception {
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Missing import path expression"));

        defaultStringTemplate("{% import as inputs %}{{ inputs.text('hello') }}")
                .render(JtwigModel.newModel());

    }

    @Test
    public void importMissingAs() throws Exception {
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Wrong syntax expecting token 'as'"));

        defaultStringTemplate("{% import 'asd' inputs %}{{ inputs.text('hello') }}")
                .render(JtwigModel.newModel());

    }

    @Test
    public void importMissingVariable() throws Exception {
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Missing alias declaration"));

        defaultStringTemplate("{% import 'asd' as %}{{ inputs.text('hello') }}")
                .render(JtwigModel.newModel());

    }

    @Test
    public void importMissingEndCodeIsland() throws Exception {
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Code island not closed"));

        defaultStringTemplate("{% import 'asd' as inputs{{ inputs.text('hello') }}")
                .render(JtwigModel.newModel());
    }
}
