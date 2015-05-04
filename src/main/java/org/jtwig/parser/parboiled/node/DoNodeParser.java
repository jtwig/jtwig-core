package org.jtwig.parser.parboiled.node;

import org.jtwig.model.tree.DoNode;
import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.base.LexicParser;
import org.jtwig.parser.parboiled.base.LimitsParser;
import org.jtwig.parser.parboiled.base.PositionTrackerParser;
import org.jtwig.parser.parboiled.base.SpacingParser;
import org.jtwig.parser.parboiled.expression.AnyExpressionParser;
import org.jtwig.parser.parboiled.model.Keyword;
import org.parboiled.Rule;

public class DoNodeParser extends NodeParser<DoNode> {
    public DoNodeParser(ParserContext context) {
        super(DoNodeParser.class, context);
    }

    @Override
    public Rule NodeRule() {
        PositionTrackerParser positionTrackerParser = parserContext().parser(PositionTrackerParser.class);
        LimitsParser limitsParser = parserContext().parser(LimitsParser.class);
        SpacingParser spacingParser = parserContext().parser(SpacingParser.class);
        AnyExpressionParser anyExpressionParser = parserContext().parser(AnyExpressionParser.class);
        LexicParser lexicParser = parserContext().parser(LexicParser.class);
        return Sequence(
                positionTrackerParser.PushPosition(),
                limitsParser.startCode(),
                spacingParser.Spacing(),
                lexicParser.Keyword(Keyword.DO),
                spacingParser.Spacing(),
                Mandatory(anyExpressionParser.ExpressionRule(), "Invalid or missing do expression"),
                spacingParser.Spacing(),
                Mandatory(limitsParser.endCode(), "Missing code island end"),

                push(new DoNode(positionTrackerParser.pop(1), anyExpressionParser.pop()))
        );
    }
}
