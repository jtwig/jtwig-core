package org.jtwig.parser.parboiled.node;

import org.jtwig.model.tree.FlushNode;
import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.base.*;
import org.jtwig.parser.parboiled.model.Keyword;
import org.parboiled.Rule;

public class FlushNodeParser extends NodeParser<FlushNode> {
    public FlushNodeParser(ParserContext context) {
        super(FlushNodeParser.class, context);
    }

    @Override
    public Rule NodeRule() {
        PositionTrackerParser positionTrackerParser = parserContext().parser(PositionTrackerParser.class);
        SpacingParser spacingParser = parserContext().parser(SpacingParser.class);
        LimitsParser limitsParser = parserContext().parser(LimitsParser.class);
        LexicParser lexicParser = parserContext().parser(LexicParser.class);
        return Sequence(
                positionTrackerParser.PushPosition(),
                limitsParser.startCode(),
                spacingParser.Spacing(),
                lexicParser.Keyword(Keyword.FLUSH),
                spacingParser.Spacing(),
                Mandatory(limitsParser.endCode(), "Missing code island end"),

                push(new FlushNode(positionTrackerParser.pop()))
        );
    }
}
