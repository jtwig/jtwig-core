package org.jtwig.context;

import org.jtwig.context.model.EscapeMode;

import java.math.MathContext;
import java.nio.charset.Charset;

public class DefaultRenderConfiguration extends RenderConfiguration {
    public DefaultRenderConfiguration() {
        super(false, Charset.defaultCharset(), MathContext.DECIMAL128, EscapeMode.NONE);
    }
}
