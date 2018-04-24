package org.jtwig.integration.function;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.integration.AbstractIntegrationTest;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class EmptyFunctionTest extends AbstractIntegrationTest {
    @Test
    public void isEmptyArrayEmpty() throws Exception {

        String result = JtwigTemplate.inlineTemplate("{% if (empty(test)) %}ok{% else %}ko{% endif %}")
                .render(JtwigModel.newModel().with("test", new Object[]{}));

        assertThat(result, is("ok"));
    }

    @Test
    public void isEmptyArrayNonEmpty() throws Exception {

        String result = JtwigTemplate.inlineTemplate("{% if (empty(test)) %}ko{% else %}ok{% endif %}")
                .render(JtwigModel.newModel().with("test", new Object[]{new Object()}));

        assertThat(result, is("ok"));
    }

    @Test
    public void emptyWhenNull() throws Exception {

        String result = JtwigTemplate.inlineTemplate("{% if (empty(null)) %}ok{% else %}ko{% endif %}")
                .render(JtwigModel.newModel());

        assertThat(result, is("ok"));
    }

    @Test
    public void emptyWhenNonNull() throws Exception {

        String result = JtwigTemplate.inlineTemplate("{% if (empty(1)) %}ko{% else %}ok{% endif %}")
                .render(JtwigModel.newModel());

        assertThat(result, is("ok"));
    }

    @Test
    public void emptyWhenEmptyList() throws Exception {

        String result = JtwigTemplate.inlineTemplate("{% if (empty([])) %}ok{% else %}ko{% endif %}")
                .render(JtwigModel.newModel());

        assertThat(result, is("ok"));
    }

    @Test
    public void emptyWhenNonEmptyList() throws Exception {

        String result = JtwigTemplate.inlineTemplate("{% if (empty([1])) %}ko{% else %}ok{% endif %}")
                .render(JtwigModel.newModel());

        assertThat(result, is("ok"));
    }

    @Test
    public void emptyWhenEmptyMap() throws Exception {

        String result = JtwigTemplate.inlineTemplate("{% if (empty({})) %}ok{% else %}ko{% endif %}")
                .render(JtwigModel.newModel());

        assertThat(result, is("ok"));
    }

    @Test
    public void emptyWhenNonEmptyMap() throws Exception {

        String result = JtwigTemplate.inlineTemplate("{% if (empty({a:1})) %}ko{% else %}ok{% endif %}")
                .render(JtwigModel.newModel());

        assertThat(result, is("ok"));
    }

    @Test
    public void emptyWhenZero() throws Exception {

        String result = JtwigTemplate.inlineTemplate("{% if (empty(0)) %}ok{% else %}ko{% endif %}")
                .render(JtwigModel.newModel());

        assertThat(result, is("ok"));
    }

    @Test
    public void emptyWhenUndefined() throws Exception {

        String result = JtwigTemplate.inlineTemplate("{% if (empty(blah)) %}ok{% else %}ko{% endif %}")
                .render(JtwigModel.newModel());

        assertThat(result, is("ok"));
    }

    @Test
    public void emptyWhenEmptyString() throws Exception {

        String result = JtwigTemplate.inlineTemplate("{% if (empty('')) %}ok{% else %}ko{% endif %}")
            .render(JtwigModel.newModel());

        assertThat(result, is("ok"));
    }

    @Test
    public void emptyWhenNonEmptyString() throws Exception {

        String result = JtwigTemplate.inlineTemplate("{% if (empty('no')) %}kk{% else %}ok{% endif %}")
            .render(JtwigModel.newModel());

        assertThat(result, is("ok"));
    }
}
