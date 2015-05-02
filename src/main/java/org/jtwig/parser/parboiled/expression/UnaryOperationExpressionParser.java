package org.jtwig.parser.parboiled.expression;

import org.jtwig.model.expression.UnaryOperationExpression;
import org.jtwig.model.expression.operation.UnaryOperator;
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
        return Sequence(
                parserContext().parser(PositionTrackerParser.class).PushPosition(),
                parserContext().parser(UnaryOperatorParser.class).UnaryOperator(UnaryOperator.values()),
                parserContext().parser(SpacingParser.class).Spacing(),
                parserContext().parser(PrimaryExpressionParser.class).ExpressionRule(),
                push(new UnaryOperationExpression(
                        parserContext().parser(PositionTrackerParser.class).pop(2),
                        parserContext().parser(UnaryOperatorParser.class).pop(1),
                        pop()
                ))
        );
    }
}
