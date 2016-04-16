package org.jtwig.escape;

public class NoneEscapeEngine implements EscapeEngine {
    private static final NoneEscapeEngine instance = new NoneEscapeEngine();

    public static NoneEscapeEngine instance () {
        return instance;
    }

    private NoneEscapeEngine () {}

    @Override
    public String escape(String input) {
        return input;
    }
}
