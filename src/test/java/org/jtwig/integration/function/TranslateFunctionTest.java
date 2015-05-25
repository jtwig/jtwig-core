package org.jtwig.integration.function;

import org.jtwig.content.locale.StaticLocaleSupplier;
import org.jtwig.i18n.source.message.MapMessageSource;
import org.junit.Test;

import java.util.HashMap;
import java.util.Locale;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.jtwig.JtwigModel.newModel;
import static org.jtwig.JtwigTemplate.inlineTemplate;
import static org.jtwig.environment.EnvironmentConfigurationBuilder.configuration;

public class TranslateFunctionTest {
    @Test
    public void translateSimple() throws Exception {
        String result =
                inlineTemplate("{{ 'Hi' | translate }}")
                .render(newModel());

        assertThat(result, is("Hi"));
    }

    @Test
    public void translateWithTranslation() throws Exception {
        Locale current = Locale.ITALIAN;

        String result =
                inlineTemplate("{{ 'Hi' | translate }}", configuration()
                        .messages().withMessageSource(current, singleEntryMap("Hi", "Ciao")).and()
                        .render().withLocaleSupplier(new StaticLocaleSupplier(current)).and()
                        .build())
                .render(newModel());

        assertThat(result, is("Ciao"));
    }

    @Test
    public void translateWithParameters() throws Exception {
        Locale current = Locale.ITALIAN;

        String result =
                inlineTemplate("{{ 'Hi %name%' | translate({ '%name%': 'Joao' }) }}", configuration()
                        .messages().withMessageSource(current, singleEntryMap("Hi %name%", "Ciao %name%")).and()
                        .render().withLocaleSupplier(new StaticLocaleSupplier(current)).and()
                        .build())
                        .render(newModel());

        assertThat(result, is("Ciao Joao"));
    }

    @Test
    public void translateWithParametersInAnotherLocale() throws Exception {
        Locale current = Locale.ITALIAN;

        String result =
                inlineTemplate("{{ 'Hi %name%' | translate({ '%name%': 'Joao' }, 'pt') }}", configuration()
                        .messages()
                            .withMessageSource(current, singleEntryMap("Hi %name%", "Ciao %name%"))
                            .withMessageSource(Locale.forLanguageTag("pt"), singleEntryMap("Hi %name%", "Ola %name%"))
                        .and()
                        .render().withLocaleSupplier(new StaticLocaleSupplier(current)).and()
                        .build())
                        .render(newModel());

        assertThat(result, is("Ola Joao"));
    }

    @Test
    public void translateInAnotherLocale() throws Exception {
        Locale current = Locale.ITALIAN;

        String result =
                inlineTemplate("{{ 'Hello' | translate('pt') }}", configuration()
                        .messages()
                            .withMessageSource(current, singleEntryMap("Hello", "Ciao"))
                            .withMessageSource(Locale.forLanguageTag("pt"), singleEntryMap("Hello", "Ola"))
                        .and()
                        .render().withLocaleSupplier(new StaticLocaleSupplier(current)).and()
                        .build())
                        .render(newModel());

        assertThat(result, is("Ola"));
    }

    private MapMessageSource singleEntryMap(final String origin, final String replacement) {
        return new MapMessageSource(new HashMap<String, String>() {{
            put(origin, replacement);
        }});
    }
}
