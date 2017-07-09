package org.jtwig.parser.parboiled.expression;

import org.jtwig.model.expression.MapSelectionExpression;
import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.base.PositionTrackerParser;
import org.jtwig.parser.parboiled.base.SpacingParser;
import org.parboiled.Rule;
import org.parboiled.annotations.Label;

public class MapSelectionExpressionParser extends ExpressionParser<MapSelectionExpression> {
    public MapSelectionExpressionParser(ParserContext context) {
        super(MapSelectionExpressionParser.class, context);
    }

    @Override
    @Label("MapSelection Expression")
    public Rule ExpressionRule() {
        BinaryOrPrimaryExpressionParser binaryOrPrimaryExpressionParser = parserContext().parser(BinaryOrPrimaryExpressionParser.class);
        PositionTrackerParser positionTrackerParser = parserContext().parser(PositionTrackerParser.class);
        SpacingParser spacingParser = parserContext().parser(SpacingParser.class);

        return Sequence(
                positionTrackerParser.PushPosition(),
                binaryOrPrimaryExpressionParser.ExpressionRule(),
                spacingParser.Spacing(),
                String("["), spacingParser.Spacing(),
                binaryOrPrimaryExpressionParser.ExpressionRule(),
                spacingParser.Spacing(),
                String("]"),

                push(new MapSelectionExpression(positionTrackerParser.pop(2), binaryOrPrimaryExpressionParser.pop(1), binaryOrPrimaryExpressionParser.pop())),

                MapSelectionExpressionTrail()
        );
    }

    public Rule MapSelectionExpressionTrail() {
        BinaryOrPrimaryExpressionParser binaryOrPrimaryExpressionParser = parserContext().parser(BinaryOrPrimaryExpressionParser.class);
        PositionTrackerParser positionTrackerParser = parserContext().parser(PositionTrackerParser.class);
        SpacingParser spacingParser = parserContext().parser(SpacingParser.class);

        return ZeroOrMore(
                FirstOf(
                        Sequence(
                                positionTrackerParser.PushPosition(),
                                String("["), spacingParser.Spacing(),
                                binaryOrPrimaryExpressionParser.ExpressionRule(),
                                spacingParser.Spacing(),
                                String("]"),

                                push(new MapSelectionExpression(positionTrackerParser.pop(1), binaryOrPrimaryExpressionParser.pop(1), binaryOrPrimaryExpressionParser.pop()))
                        ),
                        parserContext().parser(BinaryOperationSuffixExpressionParser.class).ExpressionRule()
                )
        );
    }
}
