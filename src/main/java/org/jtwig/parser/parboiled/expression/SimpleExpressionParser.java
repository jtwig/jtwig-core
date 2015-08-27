package org.jtwig.parser.parboiled.expression;

import org.jtwig.model.expression.Expression;
import org.jtwig.parser.parboiled.ParserContext;
import org.parboiled.Rule;

public class SimpleExpressionParser extends ExpressionParser<Expression> {
    public SimpleExpressionParser(ParserContext context) {
        super(SimpleExpressionParser.class, context);
    }

    @Override
    public Rule ExpressionRule() {
        return FirstOf(
                parserContext().parser(MapSelectionExpressionParser.class).ExpressionRule(),
                parserContext().parser(TernaryOperationExpressionParser.class).ExpressionRule(),
                parserContext().parser(BinaryOperationExpressionParser.class).ExpressionRule(),
                parserContext().parser(PrimaryExpressionParser.class).ExpressionRule()
        );
    }

}
