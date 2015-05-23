package org.jtwig.parser.parboiled.model;

public enum Keyword {
    // MARKs
    INCLUDE("include"),
    SET("set"),
    BLOCK("block"),
    END_BLOCK("endblock"),
    IF("if"),
    END_IF("endif"),
    ELSE_IF("elseif"),
    ELSE("else"),
    FOR("for"),
    END_FOR("endfor"),
    IMPORT("import"),
    MACRO("macro"),
    END_MACRO("endmacro"),
    EXTENDS("extends"),
    EMBED("embed"),
    END_EMBED("endembed"),
    TRUE("true"),
    FALSE("false"),

    // Internal
    IN("in"),
    AS("as"),
    AUTO_ESCAPE("autoescape"),
    END_AUTO_ESCAPE("endautoescape"),
    DO("do"),
    FLUSH("flush"),
    VERBATIM("verbatim"),
    END_VERBATIM("endverbatim"),
    SPACELESS("spaceless"),
    END_SPACELESS("endspaceless"),
    FILTER("filter"),
    END_FILTER("endfilter"),
    NULL("null"),
    IS("is"),
    NOT("not"),
    WITH("with"),
    TRANSLATE("trans"),
    INTO("into"),
    END_TRANSLATE("endtrans")
    ;

    private final String symbol;

    Keyword(String symbol) {
        this.symbol = symbol;
    }


    @Override
    public String toString() {
        return symbol;
    }
}
