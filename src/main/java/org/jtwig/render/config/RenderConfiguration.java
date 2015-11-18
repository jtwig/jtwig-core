package org.jtwig.render.config;

import org.jtwig.context.model.EscapeMode;

import java.nio.charset.Charset;

public class RenderConfiguration {
    private final boolean strictMode;
    private final Charset defaultOutputCharset;
    private final EscapeMode initialEscapeMode;

    public RenderConfiguration(boolean strictMode, Charset defaultOutputCharset, EscapeMode initialEscapeMode) {
        this.strictMode = strictMode;
        this.defaultOutputCharset = defaultOutputCharset;
        this.initialEscapeMode = initialEscapeMode;
    }

    public boolean getStrictMode() {
        return strictMode;
    }

    public Charset getDefaultOutputCharset() {
        return defaultOutputCharset;
    }

    public EscapeMode getInitialEscapeMode() {
        return initialEscapeMode;
    }
}
