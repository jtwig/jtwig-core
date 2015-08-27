package org.jtwig.context;

import org.jtwig.context.model.EscapeMode;

import java.math.MathContext;
import java.nio.charset.Charset;

public class RenderConfiguration {
    private final boolean strictMode;
    private final Charset outputCharset;
    private final MathContext mathContext;
    private final EscapeMode initialEscapeMode;

    public RenderConfiguration(boolean strictMode, Charset outputCharset, MathContext mathContext, EscapeMode initialEscapeMode) {
        this.strictMode = strictMode;
        this.outputCharset = outputCharset;
        this.mathContext = mathContext;
        this.initialEscapeMode = initialEscapeMode;
    }

    public boolean strictMode() {
        return strictMode;
    }

    public Charset outputCharset() {
        return outputCharset;
    }

    public MathContext mathContext() {
        return mathContext;
    }

    public EscapeMode initialEscapeMode() {
        return initialEscapeMode;
    }
}
