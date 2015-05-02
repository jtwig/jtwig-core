package org.jtwig.parser.parboiled.model;

public class LimitProperties {
    private final boolean whiteSpaceControl;

    public LimitProperties(boolean whiteSpaceControl) {
        this.whiteSpaceControl = whiteSpaceControl;
    }

    public boolean isWhiteSpaceControl() {
        return whiteSpaceControl;
    }
}
