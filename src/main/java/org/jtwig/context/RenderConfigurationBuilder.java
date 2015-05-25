package org.jtwig.context;

import com.google.common.base.Supplier;
import org.apache.commons.lang3.builder.Builder;
import org.jtwig.content.spaces.SpaceRemover;
import org.jtwig.context.model.EscapeMode;
import org.jtwig.i18n.locale.LocaleResolver;

import java.math.MathContext;
import java.nio.charset.Charset;
import java.util.Locale;

public class RenderConfigurationBuilder<B extends RenderConfigurationBuilder> implements Builder<RenderConfiguration> {
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

    public B withSpaceRemover(SpaceRemover spaceRemover) {
        this.spaceRemover = spaceRemover;
        return self();
    }

    public B withStrictMode(boolean strictMode) {
        this.strictMode = strictMode;
        return self();
    }

    public B withOutputCharset(Charset outputCharset) {
        this.outputCharset = outputCharset;
        return self();
    }

    public B withMathContext(MathContext mathContext) {
        this.mathContext = mathContext;
        return self();
    }

    public B withInitialEscapeMode(EscapeMode initialEscapeMode) {
        this.initialEscapeMode = initialEscapeMode;
        return self();
    }

    public B withLocaleSupplier(Supplier<Locale> localeSupplier) {
        this.localeSupplier = localeSupplier;
        return self();
    }

    public B withLocaleResolver(LocaleResolver localeResolver) {
        this.localeResolver = localeResolver;
        return self();
    }

    protected B self () {
        return (B) this;
    }

    @Override
    public RenderConfiguration build() {
        return new RenderConfiguration(spaceRemover, strictMode, outputCharset, mathContext, initialEscapeMode, localeSupplier, localeResolver);
    }
}
