package org.jtwig.render.config;

import org.jtwig.context.model.EscapeMode;

import java.nio.charset.Charset;

public class DefaultRenderConfiguration extends RenderConfiguration {
    public DefaultRenderConfiguration() {
        super(false, Charset.defaultCharset(), EscapeMode.NONE);
    }
}
