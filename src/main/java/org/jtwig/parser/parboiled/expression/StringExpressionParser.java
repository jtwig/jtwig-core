package org.jtwig.parser.parboiled.expression;

import org.jtwig.model.expression.ConstantExpression;
import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.base.LexicParser;
import org.jtwig.parser.parboiled.base.PositionTrackerParser;
import org.parboiled.Rule;

public class StringExpressionParser extends ExpressionParser<ConstantExpression> {
    public StringExpressionParser(ParserContext context) {
        super(StringExpressionParser.class, context);
    }

    @Override
    public Rule ExpressionRule() {
        PositionTrackerParser positionTrackerParser = parserContext().parser(PositionTrackerParser.class);
        LexicParser lexicParser = parserContext().parser(LexicParser.class);
        return Sequence(
                positionTrackerParser.PushPosition(),
                lexicParser.String(),
                swap(),
                push(new ConstantExpression(positionTrackerParser.pop(), lexicParser.pop()))
        );
    }
}
