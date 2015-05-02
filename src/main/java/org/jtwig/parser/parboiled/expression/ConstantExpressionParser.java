package org.jtwig.parser.parboiled.expression;

import org.jtwig.model.expression.ConstantExpression;
import org.jtwig.parser.parboiled.ParserContext;
import org.parboiled.Rule;

public class ConstantExpressionParser extends ExpressionParser<ConstantExpression> {
    public ConstantExpressionParser(ParserContext context) {
        super(ConstantExpressionParser.class, context);
    }

    @Override
    public Rule ExpressionRule() {
        return FirstOf(
                parserContext().parser(BooleanExpressionParser.class).ExpressionRule(),
                parserContext().parser(NumberExpressionParser.class).ExpressionRule(),
                parserContext().parser(StringExpressionParser.class).ExpressionRule(),
                parserContext().parser(ComprehensionListExpressionParser.class).ExpressionRule(),
                parserContext().parser(EnumerationListExpressionParser.class).ExpressionRule(),
                parserContext().parser(MapExpressionParser.class).ExpressionRule()
        );
    }
}
