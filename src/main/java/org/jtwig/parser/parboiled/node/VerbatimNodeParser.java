package org.jtwig.parser.parboiled.node;

import org.jtwig.model.tree.VerbatimNode;
import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.base.*;
import org.jtwig.parser.parboiled.model.Keyword;
import org.parboiled.Rule;

import static org.parboiled.Parboiled.createParser;

public class VerbatimNodeParser extends NodeParser<VerbatimNode> {
    public VerbatimNodeParser(ParserContext context) {
        super(VerbatimNodeParser.class, context);
        createParser(VerbatimContentParser.class, context);
    }

    @Override
    public Rule NodeRule() {
        PositionTrackerParser positionTrackerParser = parserContext().parser(PositionTrackerParser.class);
        LimitsParser limitsParser = parserContext().parser(LimitsParser.class);
        SpacingParser spacingParser = parserContext().parser(SpacingParser.class);
        LexicParser lexicParser = parserContext().parser(LexicParser.class);
        VerbatimContentParser verbatimContentParser = parserContext().parser(VerbatimContentParser.class);
        return Sequence(
                positionTrackerParser.PushPosition(),

                // start
                Sequence(
                        limitsParser.startCode(),
                        spacingParser.Spacing(),
                        lexicParser.Keyword(Keyword.VERBATIM),
                        spacingParser.Spacing(),
                        Mandatory(limitsParser.endCode(), "Missing code island end")
                ),

                // content
                verbatimContentParser.Content(endVerbatim(limitsParser, spacingParser, lexicParser)),

                endVerbatim(limitsParser, spacingParser, lexicParser),

                push(new VerbatimNode(positionTrackerParser.pop(1), verbatimContentParser.pop()))
        );
    }

    Rule endVerbatim(LimitsParser limitsParser, SpacingParser spacingParser, LexicParser lexicParser) {
        return Sequence(
                limitsParser.startCode(),
                spacingParser.Spacing(),
                lexicParser.Keyword(Keyword.END_VERBATIM),
                spacingParser.Spacing(),
                limitsParser.endCode()
        );
    }

    public static class VerbatimContentParser extends BasicParser<String> {
        public VerbatimContentParser(ParserContext context) {
            super(VerbatimContentParser.class, context);
        }

        public Rule Content(Rule end) {
            return Sequence(
                    ZeroOrMore(
                            TestNot(end),
                            ANY
                    ),
                    push(match())
            );
        }
    }
}
