package org.jtwig.parser.parboiled.base;

import org.jtwig.parser.config.SyntaxConfiguration;
import org.jtwig.parser.parboiled.ParserContext;
import org.parboiled.Rule;

public class SpacingParser extends BasicParser<Object> {
    public SpacingParser(ParserContext context) {
        super(SpacingParser.class, context);
    }

    public Rule Spacing () {
        SyntaxConfiguration syntaxConfiguration = parserContext().syntaxConfiguration();
        return ZeroOrMore(FirstOf(
                OneOrMore(AnyOf(" \t\r\n\f").label("Whitespace")),
                Sequence(syntaxConfiguration.getStartComment(), ZeroOrMore(TestNot(syntaxConfiguration.getEndComment()), ANY), syntaxConfiguration.getEndComment())
        ));
    }

    public Rule Mandatory() {
        return Sequence(
                AnyOf(" \t\r\n\f"),
                Spacing()
        );
    }
}
