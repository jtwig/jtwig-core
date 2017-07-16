package org.jtwig.parser.parboiled.expression;

import org.jtwig.model.expression.BinaryOperationExpression;
import org.jtwig.parser.parboiled.ParserContext;
import org.parboiled.Rule;

public class BinaryOperationExpressionParser extends ExpressionParser<BinaryOperationExpression> {
    public BinaryOperationExpressionParser(ParserContext context) {
        super(BinaryOperationExpressionParser.class, context);
    }

    @Override
    public Rule ExpressionRule() {
        return Sequence(
                parserContext().parser(PrimaryExpressionParser.class).ExpressionRule(),
                parserContext().parser(BinaryOperationSuffixExpressionParser.class).ExpressionRule()
        );
    }
}
