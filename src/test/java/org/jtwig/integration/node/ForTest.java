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

public class ForTest extends AbstractIntegrationTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void simpleFor() throws Exception {
        JtwigTemplate jtwigTemplate = JtwigTemplate.inlineTemplate("{% for i in list %}{{i}}{% endfor %}");
        String result = jtwigTemplate.render(newModel().with("list", new Integer[]{1, 2}));
        assertThat(result, is("12"));
    }

    @Test
    public void forLoopVariable() throws Exception {
        JtwigTemplate jtwigTemplate = JtwigTemplate.inlineTemplate("{% for i in [1, 2] %}{% if (loop.first) %}f{% endif %}{{loop.index}}{{loop.index0}}{{loop.revindex}}{{loop.revindex0}}{% if (loop.last) %}l{% endif %}{% endfor %}");
        String result = jtwigTemplate.render(newModel());
        assertThat(result, is("f10212110l"));
    }

    @Test
    public void nestedForLoopsAndLoopVariable() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{% for i in [1] %}" +
                "{% for j in [1, 2, 3] %}" +
                "a{{ loop.index }}" +
                "{% endfor %}" +
                "b{{ loop.index }}" +
                "{% endfor %}").render(newModel());

        assertThat(result, is("a1a2a3b1"));
    }

    @Test
    public void forWhiteSpaceControl() throws Exception {
        JtwigTemplate jtwigTemplate = JtwigTemplate.inlineTemplate(" {%- for i in list -%} {{i}} {%- endfor -%} ");
        String result = jtwigTemplate.render(newModel().with("list", new Integer[]{1, 2}));

        assertThat(result, is("12"));
    }

    @Test
    public void forIsolatedContextOldVariable() throws Exception {
        JtwigTemplate jtwigTemplate = JtwigTemplate.inlineTemplate("{% set a = 2 %}{% for i in list %}{% set a = 1 %}{% endfor %}{{ a }}");
        String result = jtwigTemplate.render(newModel().with("list", new Integer[]{1, 2}));

        assertThat(result, is("1"));
    }
    @Test
    public void forIsolatedContextNewVariable() throws Exception {
        JtwigTemplate jtwigTemplate = JtwigTemplate.inlineTemplate("{% for i in list %}{% set a = 1 %}{% endfor %}{{ a }}");
        String result = jtwigTemplate.render(newModel().with("list", new Integer[]{1, 2}));

        assertThat(result, is("1"));
    }

    @Test
    public void simpleForMap() throws Exception {
        JtwigTemplate jtwigTemplate = JtwigTemplate.inlineTemplate("{% for k,v in list %}{{k}}{{v}}{% endfor %}");
        String result = jtwigTemplate.render(newModel().with("list", new Integer[]{1,2}));

        assertThat(result, is("0112"));
    }

    @Test
    public void invalidForWithoutVariable() throws Exception {
        JtwigTemplate jtwigTemplate = JtwigTemplate.inlineTemplate("{% for in list %}{{k}}={{v}} {% endfor %}");
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Expecting a variable name in for loop"));

        jtwigTemplate.render(newModel());
    }

    @Test
    public void invalidForWithoutListVariable() throws Exception {
        JtwigTemplate jtwigTemplate = JtwigTemplate.inlineTemplate("{% for i in %}{{k}}={{v}} {% endfor %}");
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Expecting an expression in for loop"));

        jtwigTemplate.render(newModel());
    }

    @Test
    public void invalidForWithoutIn() throws Exception {
        JtwigTemplate jtwigTemplate = JtwigTemplate.inlineTemplate("{% for i list %}{{k}}={{v}} {% endfor %}");
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Malformed for loop, missing 'in' keyword. For example: {% for i in list %}"));

        jtwigTemplate.render(newModel());
    }

    @Test
    public void invalidForMissingEndCode() throws Exception {
        JtwigTemplate jtwigTemplate = JtwigTemplate.inlineTemplate("{% for i in list ");
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Malformed for loop start syntax, missing code island ending symbol"));

        jtwigTemplate.render(newModel());
    }

    @Test
    public void invalidEndForMissingEndCode() throws Exception {
        JtwigTemplate jtwigTemplate = JtwigTemplate.inlineTemplate("{% for i in list %}{% endfor ");
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Malformed for loop end syntax, missing code island ending symbol"));

        jtwigTemplate.render(newModel());
    }

    @Test
    public void invalidForMissingSecondVariable() throws Exception {
        JtwigTemplate jtwigTemplate = JtwigTemplate.inlineTemplate("{% for k, in list %}");
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Expecting a second variable name in for loop. Example: {% for key, value in list %}"));

        jtwigTemplate.render(newModel());
    }

    @Test
    public void invalidForMissingEndTag() throws Exception {
        JtwigTemplate jtwigTemplate = JtwigTemplate.inlineTemplate("{% for k in list %}");
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Missing endfor tag"));

        jtwigTemplate.render(newModel());
    }
}
