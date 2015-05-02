package org.jtwig.configuration;

import java.math.MathContext;

public class MathContextParameter implements ConfigurationParameter<MathContext> {
    private static final MathContextParameter INSTANCE = new MathContextParameter();

    public static MathContextParameter mathContext () {
        return INSTANCE;
    }

    private MathContextParameter() {}

    @Override
    public MathContext defaultValue() {
        return MathContext.DECIMAL32;
    }

    @Override
    public String name() {
        return MathContext.class.getSimpleName();
    }
}
