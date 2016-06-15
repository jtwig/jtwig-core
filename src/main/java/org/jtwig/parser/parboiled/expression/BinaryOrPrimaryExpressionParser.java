package org.jtwig.parser.parboiled.expression;

import org.jtwig.model.expression.Expression;
import org.jtwig.parser.parboiled.ParserContext;
import org.parboiled.Rule;

public class BinaryOrPrimaryExpressionParser extends ExpressionParser<Expression> {
    public BinaryOrPrimaryExpressionParser(ParserContext context) {
        super(BinaryOrPrimaryExpressionParser.class, context);
    }

    @Override
    public Rule ExpressionRule() {
        return FirstOf(
                parserContext().parser(BinaryOperationExpressionParser.class).ExpressionRule(),
                parserContext().parser(PrimaryExpressionParser.class).ExpressionRule()
        );
    }

}
