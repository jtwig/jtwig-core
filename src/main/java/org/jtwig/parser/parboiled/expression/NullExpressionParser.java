package org.jtwig.parser.parboiled.expression;

import org.jtwig.model.expression.ConstantExpression;
import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.base.LexicParser;
import org.jtwig.parser.parboiled.base.PositionTrackerParser;
import org.jtwig.parser.parboiled.model.Keyword;
import org.parboiled.Rule;

public class NullExpressionParser extends ExpressionParser<ConstantExpression> {
    public NullExpressionParser(ParserContext context) {
        super(NullExpressionParser.class, context);
    }

    @Override
    public Rule ExpressionRule() {
        PositionTrackerParser positionTrackerParser = parserContext().parser(PositionTrackerParser.class);
        return Sequence(
                positionTrackerParser.PushPosition(),
                parserContext().parser(LexicParser.class).Keyword(Keyword.NULL),
                push(new ConstantExpression(positionTrackerParser.pop(), null))
        );
    }
}
