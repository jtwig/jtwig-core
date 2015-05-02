package org.jtwig.parser.parboiled.expression;

import org.jtwig.model.expression.Expression;
import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.base.SpacingParser;
import org.parboiled.Rule;

public class PrimaryExpressionParser extends ExpressionParser<Expression> {
    public PrimaryExpressionParser(ParserContext context) {
        super(PrimaryExpressionParser.class, context);
    }

    @Override
    public Rule ExpressionRule() {
        return FirstOf(
                parserContext().parser(UnaryOperationExpressionParser.class).ExpressionRule(),
                ParenthesisRule(),
                parserContext().parser(ConstantExpressionParser.class).ExpressionRule(),
                parserContext().parser(FunctionExpressionParser.class).ExpressionRule(),
                parserContext().parser(VariableExpressionParser.class).ExpressionRule()
        );
    }

    Rule ParenthesisRule() {
        return Sequence(
                "(",
                parserContext().parser(SpacingParser.class).Spacing(),
                parserContext().parser(AnyExpressionParser.class).ExpressionRule(),
                parserContext().parser(SpacingParser.class).Spacing(),
                ")"
        );
    }
}
