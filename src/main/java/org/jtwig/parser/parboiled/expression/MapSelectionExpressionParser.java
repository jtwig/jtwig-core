package org.jtwig.parser.parboiled.expression;

import org.jtwig.model.expression.MapSelectionExpression;
import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.base.PositionTrackerParser;
import org.jtwig.parser.parboiled.base.SpacingParser;
import org.parboiled.Rule;

public class MapSelectionExpressionParser extends ExpressionParser<MapSelectionExpression> {
    public MapSelectionExpressionParser(ParserContext context) {
        super(MapSelectionExpressionParser.class, context);
    }

    @Override
    public Rule ExpressionRule() {
        PrimaryExpressionParser anyExpressionParser = parserContext().parser(PrimaryExpressionParser.class);
        PositionTrackerParser positionTrackerParser = parserContext().parser(PositionTrackerParser.class);
        SpacingParser spacingParser = parserContext().parser(SpacingParser.class);

        return Sequence(
                positionTrackerParser.PushPosition(),
                anyExpressionParser.ExpressionRule(),
                spacingParser.Spacing(),
                String("["), spacingParser.Spacing(),
                anyExpressionParser.ExpressionRule(),
                String("]"),

                push(new MapSelectionExpression(positionTrackerParser.pop(2), anyExpressionParser.pop(1), anyExpressionParser.pop()))
        );
    }
}
