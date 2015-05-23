package org.jtwig.context;

import com.google.common.base.Supplier;
import org.apache.commons.lang3.builder.Builder;
import org.jtwig.content.spaces.SpaceRemover;
import org.jtwig.context.model.EscapeMode;
import org.jtwig.i18n.locale.LocaleResolver;

import java.math.MathContext;
import java.nio.charset.Charset;
import java.util.Locale;

public class RenderConfigurationBuilder implements Builder<RenderConfiguration> {
    private SpaceRemover spaceRemover;
    private boolean strictMode;
    private Charset outputCharset;
    private MathContext mathContext;
    private EscapeMode initialEscapeMode;
    private Supplier<Locale> localeSupplier;
    private LocaleResolver localeResolver;

    public RenderConfigurationBuilder () {}
    public RenderConfigurationBuilder (RenderConfiguration prototype) {
        this
                .withInitialEscapeMode(prototype.initialEscapeMode())
                .withMathContext(prototype.mathContext())
                .withOutputCharset(prototype.outputCharset())
                .withSpaceRemover(prototype.spaceRemover())
                .withStrictMode(prototype.strictMode())
                .withLocaleSupplier(prototype.currentLocaleSupplier())
                .withLocaleResolver(prototype.localeResolver())
        ;
    }

    public RenderConfigurationBuilder withSpaceRemover(SpaceRemover spaceRemover) {
        this.spaceRemover = spaceRemover;
        return this;
    }

    public RenderConfigurationBuilder withStrictMode(boolean strictMode) {
        this.strictMode = strictMode;
        return this;
    }

    public RenderConfigurationBuilder withOutputCharset(Charset outputCharset) {
        this.outputCharset = outputCharset;
        return this;
    }

    public RenderConfigurationBuilder withMathContext(MathContext mathContext) {
        this.mathContext = mathContext;
        return this;
    }

    public RenderConfigurationBuilder withInitialEscapeMode(EscapeMode initialEscapeMode) {
        this.initialEscapeMode = initialEscapeMode;
        return this;
    }

    public RenderConfigurationBuilder withLocaleSupplier(Supplier<Locale> localeSupplier) {
        this.localeSupplier = localeSupplier;
        return this;
    }

    public RenderConfigurationBuilder withLocaleResolver(LocaleResolver localeResolver) {
        this.localeResolver = localeResolver;
        return this;
    }

    @Override
    public RenderConfiguration build() {
        return new RenderConfiguration(spaceRemover, strictMode, outputCharset, mathContext, initialEscapeMode, localeSupplier, localeResolver);
    }
}
