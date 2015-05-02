package org.jtwig.parser.parboiled.expression;

import org.jtwig.model.expression.ConstantExpression;
import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.base.LexicParser;
import org.jtwig.parser.parboiled.base.PositionTrackerParser;
import org.parboiled.Rule;

import java.math.BigDecimal;

public class NumberExpressionParser extends ExpressionParser<ConstantExpression> {
    public NumberExpressionParser(ParserContext context) {
        super(NumberExpressionParser.class, context);
    }

    @Override
    public Rule ExpressionRule() {
        return Sequence(
                parserContext().parser(PositionTrackerParser.class).PushPosition(),
                parserContext().parser(LexicParser.class).Number(),
                push(new ConstantExpression(parserContext().parser(PositionTrackerParser.class).pop(), new BigDecimal(parserContext().parser(LexicParser.class).match())))
        );
    }
}
