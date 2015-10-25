package org.jtwig.context;

import org.jtwig.context.model.EscapeMode;

import java.nio.charset.Charset;

public class RenderConfiguration {
    private final boolean strictMode;
    private final Charset outputCharset;
    private final EscapeMode initialEscapeMode;

    public RenderConfiguration(boolean strictMode, Charset outputCharset, EscapeMode initialEscapeMode) {
        this.strictMode = strictMode;
        this.outputCharset = outputCharset;
        this.initialEscapeMode = initialEscapeMode;
    }

    public boolean getStrictMode() {
        return strictMode;
    }

    public Charset getOutputCharset() {
        return outputCharset;
    }

    public EscapeMode getInitialEscapeMode() {
        return initialEscapeMode;
    }
}
