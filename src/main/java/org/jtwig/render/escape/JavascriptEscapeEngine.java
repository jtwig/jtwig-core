package org.jtwig.render.escape;

import org.apache.commons.lang3.StringEscapeUtils;

public class JavascriptEscapeEngine implements EscapeEngine {
    private static final JavascriptEscapeEngine instance = new JavascriptEscapeEngine();

    public static JavascriptEscapeEngine instance () {
        return instance;
    }

    private JavascriptEscapeEngine () {}

    @Override
    public String escape(String input) {
        return StringEscapeUtils.escapeEcmaScript(input);
    }
}
