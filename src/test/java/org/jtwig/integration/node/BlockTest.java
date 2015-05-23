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

public class BlockTest extends AbstractIntegrationTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void simpleBlock() throws Exception {
        JtwigTemplate template = JtwigTemplate.inlineTemplate("{% block name %}hello{% endblock %}");
        String result = template.render(newModel());
        assertThat(result, is("hello"));
    }

    @Test
    public void blockWhiteSpaceControl() throws Exception {
        JtwigTemplate template = JtwigTemplate.inlineTemplate(" {%- block name -%} hello {%- endblock -%} ");
        String result = template.render(newModel());
        assertThat(result, is("hello"));
    }

    @Test
    public void blockWithoutName() throws Exception {
        JtwigTemplate template = JtwigTemplate.inlineTemplate("{% block %}hello{% endblock name %}");
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Block identifier not specified"));

        template.render(newModel());
    }

    @Test
    public void blockStartMissingEndCode() throws Exception {
        JtwigTemplate template = JtwigTemplate.inlineTemplate("{% block name hello{% endblock name %}");
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Missing end of code island"));

        template.render(newModel());
    }


    @Test
    public void blockEndMissingEndCode() throws Exception {
        JtwigTemplate template = JtwigTemplate.inlineTemplate("{% block name %}hello{% endblock name");
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Missing end of code island"));

        template.render(newModel());
    }

    @Test
    public void blockDifferentNames() throws Exception {
        JtwigTemplate template = JtwigTemplate.inlineTemplate("{% block one %}hello{% endblock name %}");
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Expecting block 'one' to end with the same identifier but found 'name' instead"));

        template.render(newModel());
    }
    @Test
    public void blockMissingEndTag() throws Exception {
        JtwigTemplate template = JtwigTemplate.inlineTemplate("{% block one %}hello");
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Missing endblock tag"));

        template.render(newModel());
    }

}
