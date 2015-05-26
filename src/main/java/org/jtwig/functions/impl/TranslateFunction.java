package org.jtwig.functions.impl;

import com.google.common.base.Supplier;
import org.jtwig.context.RenderContext;
import org.jtwig.context.RenderContextHolder;
import org.jtwig.functions.annotations.JtwigFunction;
import org.jtwig.functions.annotations.Parameter;
import org.jtwig.i18n.MessageResolver;
import org.jtwig.i18n.decorate.CompositeMessageDecorator;
import org.jtwig.i18n.decorate.MessageDecorator;
import org.jtwig.i18n.decorate.ReplacementMessageDecorator;
import org.jtwig.plural.PluralOptions;
import org.jtwig.util.OptionalUtils;
import org.jtwig.value.JtwigValueFactory;
import org.jtwig.value.configuration.ValueConfiguration;

import java.util.*;

import static java.util.Arrays.asList;

public class TranslateFunction {
    @JtwigFunction(value = "translate", aliases = {"trans", "message"})
    public String translate (@Parameter("message") final String message, @Parameter("replacements") Map replacements, @Parameter("locale") Locale locale) {
        String key = message.trim();
        MessageDecorator messageDecorator = new ReplacementMessageDecorator(toReplacementCollection(replacements, getValueConfiguration()));
        return getMessageResolver()
                .resolve(locale, key, messageDecorator)
                .or(defaultMessage(key, messageDecorator));
    }


    @JtwigFunction(value = "translate", aliases = {"trans", "message"})
    public String translate (@Parameter("message") final String message, @Parameter("replacements") Map replacements) {
        return translate(message, replacements, getLocaleSupplier().get());
    }

    @JtwigFunction(value = "translate", aliases = {"trans", "message"})
    public String translate (@Parameter("message") final String message) {
        return translate(message, Collections.emptyMap(), getLocaleSupplier().get());
    }

    @JtwigFunction(value = "translate", aliases = {"trans", "message"})
    public String translate (@Parameter("message") final String message, @Parameter("locale") Locale locale) {
        return translate(message, Collections.emptyMap(), locale);
    }

    @JtwigFunction(value = "translateChoice", aliases = {"transchoice"})
    public String translateChoice (@Parameter("message") final String message, @Parameter("count") int count, @Parameter("replacements") Map replacements, @Parameter("locale") Locale locale) {
        String key = message.trim();
        MessageDecorator messageDecorator = new CompositeMessageDecorator(asList(
                new PluralSelector(count),
                new ReplacementMessageDecorator(toReplacementCollection(replacements, getValueConfiguration()))
        ));
        return getMessageResolver()
                .resolve(locale, key, messageDecorator)
                .or(defaultMessage(key, messageDecorator));
    }

    @JtwigFunction(value = "translateChoice", aliases = {"transchoice"})
    public String translateChoice (@Parameter("message") final String message, @Parameter("count") int count, @Parameter("replacements") Map replacements) {
        return translateChoice(message, count, replacements, getLocaleSupplier().get());
    }

    @JtwigFunction(value = "translateChoice", aliases = {"transchoice"})
    public String translateChoice (@Parameter("message") final String message, @Parameter("count") int count) {
        return translateChoice(message, count, Collections.emptyMap(), getLocaleSupplier().get());
    }

    @JtwigFunction(value = "translateChoice", aliases = {"transchoice"})
    public String translateChoice (@Parameter("message") final String message, @Parameter("count") int count, @Parameter("locale") Locale locale) {
        return translateChoice(message, count, Collections.emptyMap(), locale);
    }

    private static class PluralSelector implements MessageDecorator {
        private final int value;

        public PluralSelector(int value) {
            this.value = value;
        }

        @Override
        public String decorate(String translated) {
            return PluralOptions.parse(translated).select(value)
                    .or(OptionalUtils.<String>throwException(String.format("Unable to select option for %d from '%s'", value, translated)));
        }
    }

    private Supplier<Locale> getLocaleSupplier() {
        return getRenderContext().environment().renderConfiguration().currentLocaleSupplier();
    }

    private MessageResolver getMessageResolver() {
        return getRenderContext()
                .environment().messageResolver();
    }

    private ValueConfiguration getValueConfiguration() {
        return getRenderContext().environment().valueConfiguration();
    }

    private Supplier<String> defaultMessage(final String key, final MessageDecorator messageDecorator) {
        return new Supplier<String>() {
            @Override
            public String get() {
                return messageDecorator.decorate(key);
            }
        };
    }

    private Collection<ReplacementMessageDecorator.Replacement> toReplacementCollection(Map<Object, Object> replacements, ValueConfiguration valueConfiguration) {
        Collection<ReplacementMessageDecorator.Replacement> result = new ArrayList<>();
        for (Map.Entry<Object, Object> entry : replacements.entrySet()) {
            if (entry.getKey() != null) {
                String key = entry.getKey().toString();
                String stringValue = JtwigValueFactory.value(entry.getValue(), valueConfiguration).asString();
                result.add(new ReplacementMessageDecorator.Replacement(key, stringValue));
            }
        }
        return result;
    }

    protected RenderContext getRenderContext() {
        return RenderContextHolder.get();
    }
}
