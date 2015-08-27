package org.jtwig.parser.parboiled.node;

import org.jtwig.model.tree.OutputNode;
import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.base.LimitsParser;
import org.jtwig.parser.parboiled.base.PositionTrackerParser;
import org.jtwig.parser.parboiled.base.SpacingParser;
import org.jtwig.parser.parboiled.expression.AnyExpressionParser;
import org.parboiled.Rule;

public class OutputNodeParser extends NodeParser<OutputNode> {
    public OutputNodeParser(ParserContext context) {
        super(OutputNodeParser.class, context);
    }

    @Override
    public Rule NodeRule() {
        PositionTrackerParser positionTrackerParser = parserContext().parser(PositionTrackerParser.class);
        LimitsParser limitsParser = parserContext().parser(LimitsParser.class);
        SpacingParser spacingParser = parserContext().parser(SpacingParser.class);
        AnyExpressionParser anyExpressionParser = parserContext().parser(AnyExpressionParser.class);
        return Sequence(
                positionTrackerParser.PushPosition(),

                limitsParser.startOutput(),
                spacingParser.Spacing(),
                Mandatory(anyExpressionParser.ExpressionRule(), "Missing or invalid output expression"),
                spacingParser.Spacing(),

                Mandatory(limitsParser.endOutput(), "Expecting end of output code island"),

                push(new OutputNode(
                        positionTrackerParser.pop(1),
                        anyExpressionParser.pop()
                ))
        );
    }
}
