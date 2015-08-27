package org.jtwig.parser.parboiled.expression;

import org.jtwig.model.expression.UnaryOperationExpression;
import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.base.PositionTrackerParser;
import org.jtwig.parser.parboiled.base.SpacingParser;
import org.jtwig.parser.parboiled.expression.operator.UnaryOperatorParser;
import org.parboiled.Rule;

public class UnaryOperationExpressionParser extends ExpressionParser<UnaryOperationExpression> {
    public UnaryOperationExpressionParser(ParserContext context) {
        super(UnaryOperationExpressionParser.class, context);
    }

    @Override
    public Rule ExpressionRule() {
        PositionTrackerParser positionTrackerParser = parserContext().parser(PositionTrackerParser.class);
        UnaryOperatorParser unaryOperatorParser = parserContext().parser(UnaryOperatorParser.class);
        SpacingParser spacingParser = parserContext().parser(SpacingParser.class);
        PrimaryExpressionParser primaryExpressionParser = parserContext().parser(PrimaryExpressionParser.class);
        return Sequence(
                positionTrackerParser.PushPosition(),
                unaryOperatorParser.UnaryOperator(),
                spacingParser.Spacing(),
                primaryExpressionParser.ExpressionRule(),
                push(new UnaryOperationExpression(
                        positionTrackerParser.pop(2),
                        unaryOperatorParser.pop(1),
                        pop()
                ))
        );
    }
}
