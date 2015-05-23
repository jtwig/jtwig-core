package org.jtwig.integration.node;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.content.locale.StaticLocaleSupplier;
import org.jtwig.i18n.source.message.MapMessageSource;
import org.junit.Test;

import java.util.HashMap;
import java.util.Locale;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.jtwig.environment.EnvironmentConfigurationBuilder.configuration;

public class TranslateNodeTest {
    @Test
    public void simpleTranslate() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{% trans %}Hi{% endtrans %}")
                .render(JtwigModel.newModel());

        assertThat(result, is("Hi"));
    }

    @Test
    public void simpleTranslateWithTranslation() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{% trans %}Hi{% endtrans %}", configuration()
                .withMessageSource(Locale.ITALY, singleMessageResource("Hi", "Ciao"))
                .withLocaleSupplier(new StaticLocaleSupplier(Locale.ITALY))
                .build())
                .render(JtwigModel.newModel());

        assertThat(result, is("Ciao"));
    }

    @Test
    public void simpleTranslateWithTranslationInto() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{% trans into 'it-IT' %}Hi{% endtrans %}", configuration()
                .withMessageSource(Locale.ITALY, singleMessageResource("Hi", "Ciao"))
                .withLocaleSupplier(new StaticLocaleSupplier(Locale.ENGLISH))
                .build())
                .render(JtwigModel.newModel());

        assertThat(result, is("Ciao"));
    }

    @Test
    public void simpleTranslateWithTranslationWithParameter() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{% trans with { '%name%': 'Joao' } %}Hi %name%{% endtrans %}", configuration()
                .withMessageSource(Locale.ITALY, singleMessageResource("Hi %name%", "Ciao %name%"))
                .withLocaleSupplier(new StaticLocaleSupplier(Locale.ITALY))
                .build())
                .render(JtwigModel.newModel());

        assertThat(result, is("Ciao Joao"));
    }

    @Test
    public void translateWithTranslationWithContextVariable() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{% trans %}Hi %name%{% endtrans %}", configuration()
                .withMessageSource(Locale.ITALY, singleMessageResource("Hi %name%", "Ciao %name%"))
                .withLocaleSupplier(new StaticLocaleSupplier(Locale.ITALY))
                .build())
                .render(JtwigModel.newModel().with("name", "World"));

        assertThat(result, is("Ciao World"));
    }

    private MapMessageSource singleMessageResource(final String key, final String value) {
        return new MapMessageSource(new HashMap<String, String>() {{ put(key, value); }});
    }
}
