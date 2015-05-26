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

public class TranslateChoiceFunctionTest {

    @Test
    public void translateSimple() throws Exception {
        String result =
                inlineTemplate("{{ 'Hi' | translateChoice(1) }}")
                        .render(newModel());

        assertThat(result, is("Hi"));
    }

    @Test
    public void translateWithChoiceOne() throws Exception {
        String result =
                inlineTemplate("{{ '{0} Zero | {1} One | ]1, Inf[ Multi' | translateChoice(1) }}")
                        .render(newModel());

        assertThat(result, is("One"));
    }

    @Test
    public void translateWithChoiceZero() throws Exception {
        String result =
                inlineTemplate("{{ '{0} Zero | {1} One | ]1, Inf[ Multi' | translateChoice(0) }}")
                        .render(newModel());

        assertThat(result, is("Zero"));
    }

    @Test
    public void translateWithChoiceMulti() throws Exception {
        String result =
                inlineTemplate("{{ '{0} Zero | {1} One | ]1, Inf[ Multi' | translateChoice(2) }}")
                        .render(newModel());

        assertThat(result, is("Multi"));
    }

    @Test
    public void translateWithTranslation() throws Exception {
        String result =
                inlineTemplate("{{ '{0} Zero | {1} One | ]1, Inf[ Multi' | translateChoice(1) }}", configuration()
                        .messages()
                            .withMessageSource(Locale.ITALY, singleMessageSource("{0} Zero | {1} One | ]1, Inf[ Multi", "{0} Zero | [1, Inf[ Varios"))
                            .and()
                        .render()
                            .withLocaleSupplier(new StaticLocaleSupplier(Locale.ITALY))
                            .and()
                        .build())
                        .render(newModel());

        assertThat(result, is("Varios"));
    }

    @Test
    public void translateWithTranslationAndLocale() throws Exception {
        String result =
                inlineTemplate("{{ '{0} Zero | {1} One | ]1, Inf[ Multi' | translateChoice(3, 'it-IT') }}", configuration()
                        .messages()
                            .withMessageSource(Locale.ITALY, singleMessageSource("{0} Zero | {1} One | ]1, Inf[ Multi", "{0} Zero | [1, Inf[ Varios"))
                            .and()
                        .build())
                        .render(newModel());

        assertThat(result, is("Varios"));
    }

    @Test
    public void translateWithTranslationAndLocaleAndReplacements() throws Exception {
        String result =
                inlineTemplate("{{ '{0} Zero | {1} One | ]1, Inf[ Multi' | translateChoice(3, {'%name%': 'Joao'},'it-IT') }}", configuration()
                        .messages()
                            .withMessageSource(Locale.ITALY, singleMessageSource("{0} Zero | {1} One | ]1, Inf[ Multi", "{0} Zero | [1, Inf[ Varios %name%"))
                            .and()
                        .build())
                        .render(newModel());

        assertThat(result, is("Varios Joao"));
    }

    private MapMessageSource singleMessageSource(final String key, final String value) {
        return new MapMessageSource(new HashMap<String, String>() {{ put(key, value); }});
    }
}
