package org.jtwig.context;

import com.google.common.base.Supplier;
import org.jtwig.content.spaces.HtmlSpaceRemover;
import org.jtwig.context.model.EscapeMode;
import org.jtwig.i18n.locale.JavaLocaleResolver;

import java.math.MathContext;
import java.nio.charset.Charset;
import java.util.Locale;

public class DefaultRenderConfiguration extends RenderConfiguration {
    public DefaultRenderConfiguration() {
        super(new HtmlSpaceRemover(), false, Charset.defaultCharset(), MathContext.DECIMAL128, EscapeMode.NONE, new Supplier<Locale>() {
            @Override
            public Locale get() {
                return Locale.getDefault();
            }
        }, new JavaLocaleResolver());
    }
}
