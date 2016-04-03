package org.jtwig.integration.node;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.integration.AbstractIntegrationTest;
import org.jtwig.render.context.model.EscapeMode;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.jtwig.environment.EnvironmentConfigurationBuilder.configuration;

public class TextTest extends AbstractIntegrationTest {
    @Test
    public void simpleText() throws Exception {
        String result = JtwigTemplate.inlineTemplate(" hello ").render(JtwigModel.newModel());

        assertThat(result, is(" hello "));
    }

    @Test
    public void textWithWhitespaceControlLeft() throws Exception {
        String result = JtwigTemplate.inlineTemplate(" {{- hello }} world").render(JtwigModel.newModel().with("hello", "hello"));

        assertThat(result, is("hello world"));
    }

    @Test
    public void textWithWhitespaceControlRight() throws Exception {
        String result = JtwigTemplate.inlineTemplate(" {{ hello -}} world").render(JtwigModel.newModel().with("hello", "hello"));

        assertThat(result, is(" helloworld"));
    }

    @Test
    public void textWithWhitespaceControlBoth() throws Exception {
        String result = JtwigTemplate.inlineTemplate(" {{- hello -}} world").render(JtwigModel.newModel().with("hello", "hello"));

        assertThat(result, is("helloworld"));
    }

    @Test
    public void textWithEscapeJs() throws Exception {
        String result = JtwigTemplate.inlineTemplate("'one time', \"Stop!\"", configuration()
                .render().withInitialEscapeMode(EscapeMode.JS).and().build())
                .render(JtwigModel.newModel());

        assertThat(result, is("\\'one time\\', \\\"Stop!\\\""));
    }

    @Test
    public void textWithEscapeJavascript() throws Exception {
        String result = JtwigTemplate.inlineTemplate("'one time', \"Stop!\"", configuration()
                .render().withInitialEscapeMode(EscapeMode.JAVASCRIPT).and().build())
                .render(JtwigModel.newModel());

        assertThat(result, is("\\'one time\\', \\\"Stop!\\\""));
    }

    @Test
    public void textWithEscapeHtml() throws Exception {
        String result = JtwigTemplate.inlineTemplate("\"bread\" & \"butter\"", configuration()
                .render().withInitialEscapeMode(EscapeMode.HTML).and().build())
                .render(JtwigModel.newModel());

        assertThat(result, is("&quot;bread&quot; &amp; &quot;butter&quot;"));
    }
}
