package org.jtwig.render.environment;

import org.jtwig.context.model.EscapeMode;

import java.nio.charset.Charset;

public class RenderEnvironment {
    private final boolean strictMode;
    private final Charset defaultOutputCharset;
    private final EscapeMode initialEscapeMode;

    public RenderEnvironment(boolean strictMode, Charset defaultOutputCharset, EscapeMode initialEscapeMode) {
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
