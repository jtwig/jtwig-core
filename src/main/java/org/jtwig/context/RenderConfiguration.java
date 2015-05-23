package org.jtwig.context;

import com.google.common.base.Supplier;
import org.jtwig.content.spaces.SpaceRemover;
import org.jtwig.context.model.EscapeMode;

import java.math.MathContext;
import java.nio.charset.Charset;
import java.util.Locale;

public class RenderConfiguration {
    private final SpaceRemover spaceRemover;
    private final boolean strictMode;
    private final Charset outputCharset;
    private final MathContext mathContext;
    private final EscapeMode initialEscapeMode;
    private final Supplier<Locale> currentLocaleSupplier;

    public RenderConfiguration(SpaceRemover spaceRemover, boolean strictMode, Charset outputCharset, MathContext mathContext, EscapeMode initialEscapeMode, Supplier<Locale> currentLocaleSupplier) {
        this.spaceRemover = spaceRemover;
        this.strictMode = strictMode;
        this.outputCharset = outputCharset;
        this.mathContext = mathContext;
        this.initialEscapeMode = initialEscapeMode;
        this.currentLocaleSupplier = currentLocaleSupplier;
    }

    public SpaceRemover spaceRemover() {
        return spaceRemover;
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

    public Supplier<Locale> currentLocaleSupplier() {
        return currentLocaleSupplier;
    }
}
