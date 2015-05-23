package org.jtwig.content.locale;

import com.google.common.base.Supplier;

import java.util.Locale;

public class StaticLocaleSupplier implements Supplier<Locale> {
    private final Locale locale;

    public StaticLocaleSupplier(Locale locale) {
        this.locale = locale;
    }

    @Override
    public Locale get() {
        return locale;
    }
}
