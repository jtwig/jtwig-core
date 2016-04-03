package org.jtwig.render.context.model;

import com.google.common.base.Function;
import com.google.common.base.Optional;
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
    }),
    JS(new Function<String, String>() {
        @Override
        public String apply(String input) {
            return StringEscapeUtils.escapeEcmaScript(input);
        }
    }),
    JAVASCRIPT(new Function<String, String>() {
        @Override
        public String apply(String input) {
            return StringEscapeUtils.escapeEcmaScript(input);
        }
    })
    ;

    public static Optional<EscapeMode> fromString (String value) {
        for (EscapeMode escapeMode : EscapeMode.values()) {
            if (escapeMode.toString().equals(value)) {
                return Optional.of(escapeMode);
            }
        }
        return Optional.absent();
    }

    private final Function<String, String> escapingFunction;

    EscapeMode(Function<String, String> escapingFunction) {
        this.escapingFunction = escapingFunction;
    }

    public String escape(String content) {
        return escapingFunction.apply(content);
    }
}
