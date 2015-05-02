package org.jtwig.parser.parboiled.expression;

import org.jtwig.model.expression.TernaryOperationExpression;
import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.base.PositionTrackerParser;
import org.jtwig.parser.parboiled.base.SpacingParser;
import org.parboiled.Rule;

public class TernaryOperationExpressionParser extends ExpressionParser<TernaryOperationExpression> {
    public TernaryOperationExpressionParser(ParserContext context) {
        super(TernaryOperationExpressionParser.class, context);
    }

    @Override
    public Rule ExpressionRule() {
        SpacingParser spacingParser = parserContext().parser(SpacingParser.class);
        PositionTrackerParser positionTrackerParser = parserContext().parser(PositionTrackerParser.class);
        Rule initial = parserContext().parser(PrimaryExpressionParser.class).ExpressionRule();
        Rule recursive = parserContext().parser(AnyExpressionParser.class).ExpressionRule();
        return Sequence(
                positionTrackerParser.PushPosition(),
                initial, spacingParser.Spacing(),
                String("?"), spacingParser.Spacing(),
                recursive, spacingParser.Spacing(),
                String(":"), spacingParser.Spacing(),
                recursive,
                push(new TernaryOperationExpression(
                        positionTrackerParser.pop(3),
                        pop(2),
                        pop(1),
                        pop()
                ))
        );
    }
}
