package org.jtwig.integration.node;

import org.jtwig.JtwigTemplate;
import org.jtwig.integration.AbstractIntegrationTest;
import org.jtwig.parser.ParseException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.jtwig.JtwigModel.newModel;

public class OutputTest extends AbstractIntegrationTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void output() throws Exception {
        JtwigTemplate template = JtwigTemplate.inlineTemplate(" {{ variable }} ");

        String result = template.render(newModel().with("variable", "hello"));

        assertThat(result, is(" hello "));
    }

    @Test
    public void outputWithStripLeftWhiteSpace() throws Exception {
        JtwigTemplate template = JtwigTemplate.inlineTemplate(" {{- variable }} ");

        String result = template.render(newModel().with("variable", "hello"));

        assertThat(result, is("hello "));
    }

    @Test
    public void outputWithStripRightWhiteSpace() throws Exception {
        JtwigTemplate template = JtwigTemplate.inlineTemplate(" {{ variable -}} ");

        String result = template.render(newModel().with("variable", "hello"));

        assertThat(result, is(" hello"));
    }

    @Test
    public void outputWithStripBothWhiteSpace() throws Exception {
        JtwigTemplate template = JtwigTemplate.inlineTemplate(" {{- variable -}} ");

        String result = template.render(newModel().with("variable", "hello"));

        assertThat(result, is("hello"));
    }

    @Test
    public void outputWithoutEndCodeIsland() throws Exception {
        JtwigTemplate template = JtwigTemplate.inlineTemplate(" {{- variable } ");
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Expecting end of output code island"));

        template.render(newModel());
    }

    @Test
    public void outputWithoutExpression() throws Exception {
        JtwigTemplate template = JtwigTemplate.inlineTemplate(" {{-  }} ");
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Missing or invalid output expression"));

        template.render(newModel());
    }
}
