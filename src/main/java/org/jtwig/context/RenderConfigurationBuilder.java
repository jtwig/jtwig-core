package org.jtwig.context;

import org.apache.commons.lang3.builder.Builder;
import org.jtwig.context.model.EscapeMode;

import java.math.MathContext;
import java.nio.charset.Charset;

public class RenderConfigurationBuilder<B extends RenderConfigurationBuilder> implements Builder<RenderConfiguration> {
    private boolean strictMode;
    private Charset outputCharset;
    private MathContext mathContext;
    private EscapeMode initialEscapeMode;

    public RenderConfigurationBuilder () {}
    public RenderConfigurationBuilder (RenderConfiguration prototype) {
        this
                .withInitialEscapeMode(prototype.initialEscapeMode())
                .withMathContext(prototype.mathContext())
                .withOutputCharset(prototype.outputCharset())
                .withStrictMode(prototype.strictMode())
        ;
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

    protected B self () {
        return (B) this;
    }

    @Override
    public RenderConfiguration build() {
        return new RenderConfiguration(strictMode, outputCharset, mathContext, initialEscapeMode);
    }
}
