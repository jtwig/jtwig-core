package org.jtwig.configuration;

import org.jtwig.context.model.EscapeMode;

public class InitialEscapeModeParameter implements ConfigurationParameter<EscapeMode> {
    public static InitialEscapeModeParameter escapeMode() {
        return new InitialEscapeModeParameter();
    }

    private InitialEscapeModeParameter() {
    }

    @Override
    public EscapeMode defaultValue() {
        return EscapeMode.NONE;
    }

    @Override
    public String name() {
        return "escapeMode";
    }
}
