package org.jtwig.context.model;

import com.google.common.base.Function;
import org.apache.commons.lang3.StringEscapeUtils;

public enum EscapeMode {
    NONE(new Function<String, String>() {
        @Override
        public String apply(String input) {
            return input;
        }
    }),
    HTML(new Function<String, String>() {
        @Override
        public String apply(String input) {
            return StringEscapeUtils.escapeHtml4(input);
        }
    })
    ;

    private final Function<String, String> escapingFunction;

    EscapeMode(Function<String, String> escapingFunction) {
        this.escapingFunction = escapingFunction;
    }

    public String escape(String content) {
        return escapingFunction.apply(content);
    }
}
