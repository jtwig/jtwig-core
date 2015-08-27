package org.jtwig.parser.parboiled.node;

import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.base.BasicParser;
import org.jtwig.parser.parboiled.base.LimitsParser;
import org.parboiled.Rule;

public class CommentParser extends BasicParser<Object> {
    public CommentParser(ParserContext context) {
        super(CommentParser.class, context);
    }

    public Rule Comment() {
        LimitsParser limitsParser = parserContext().parser(LimitsParser.class);
        return Sequence(
                limitsParser.startComment(),
                ZeroOrMore(
                        TestNot(limitsParser.endComment()),
                        ANY
                ),
                limitsParser.endComment()
        );
    }
}
