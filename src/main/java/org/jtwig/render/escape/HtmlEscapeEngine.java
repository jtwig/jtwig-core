package org.jtwig.render.escape;

import org.apache.commons.lang3.StringEscapeUtils;

public class HtmlEscapeEngine implements EscapeEngine {
    private static final HtmlEscapeEngine instance = new HtmlEscapeEngine();

    public static HtmlEscapeEngine instance () {
        return instance;
    }

    private HtmlEscapeEngine () {}

    @Override
    public String escape(String input) {
        return StringEscapeUtils.escapeHtml4(input);
    }
}
