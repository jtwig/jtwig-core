package org.jtwig.functions.impl;

import com.google.common.base.Supplier;
import org.jtwig.context.RenderContext;
import org.jtwig.context.RenderContextHolder;
import org.jtwig.functions.annotations.JtwigFunction;
import org.jtwig.functions.annotations.Parameter;
import org.jtwig.i18n.MessageResolver;
import org.jtwig.i18n.decorate.MessageDecorator;
import org.jtwig.i18n.decorate.ReplacementMessageDecorator;
import org.jtwig.value.JtwigValueFactory;
import org.jtwig.value.configuration.ValueConfiguration;

import java.util.*;

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
        String key = message.trim();
        MessageDecorator messageDecorator = new ReplacementMessageDecorator(toReplacementCollection(replacements, getValueConfiguration()));
        return getMessageResolver()
                .resolve(getLocaleSupplier().get(), key, messageDecorator)
                .or(defaultMessage(key, messageDecorator));
    }

    @JtwigFunction(value = "translate", aliases = {"trans", "message"})
    public String translate (@Parameter("message") final String message) {
        String key = message.trim();
        MessageDecorator messageDecorator = new ReplacementMessageDecorator(Collections.<ReplacementMessageDecorator.Replacement>emptyList());
        return getMessageResolver()
                .resolve(getLocaleSupplier().get(), key, messageDecorator)
                .or(defaultMessage(key, messageDecorator));
    }

    @JtwigFunction(value = "translate", aliases = {"trans", "message"})
    public String translate (@Parameter("message") final String message, @Parameter("locale") Locale locale) {
        String key = message.trim();
        MessageDecorator messageDecorator = new ReplacementMessageDecorator(Collections.<ReplacementMessageDecorator.Replacement>emptyList());
        return getMessageResolver()
                .resolve(locale, key, messageDecorator)
                .or(defaultMessage(key, messageDecorator));
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
