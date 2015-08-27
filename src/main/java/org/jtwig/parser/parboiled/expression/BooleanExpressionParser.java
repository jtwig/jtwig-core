package org.jtwig.parser.parboiled.expression;

import org.jtwig.model.expression.ConstantExpression;
import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.base.LexicParser;
import org.jtwig.parser.parboiled.base.PositionTrackerParser;
import org.jtwig.parser.parboiled.model.Keyword;
import org.parboiled.Rule;

public class BooleanExpressionParser extends ExpressionParser<ConstantExpression> {
    public BooleanExpressionParser(ParserContext context) {
        super(BooleanExpressionParser.class, context);
    }

    @Override
    public Rule ExpressionRule() {
        return FirstOf(
                TrueRule(),
                FalseRule()
        );
    }


    public Rule TrueRule () {
        PositionTrackerParser positionTrackerParser = parserContext().parser(PositionTrackerParser.class);
        LexicParser lexicParser = parserContext().parser(LexicParser.class);
        return Sequence(
                positionTrackerParser.PushPosition(),
                lexicParser.Keyword(Keyword.TRUE),
                push(new ConstantExpression(positionTrackerParser.pop(), true))
        );
    }

    public Rule FalseRule () {
        PositionTrackerParser positionTrackerParser = parserContext().parser(PositionTrackerParser.class);
        LexicParser lexicParser = parserContext().parser(LexicParser.class);
        return Sequence(
                positionTrackerParser.PushPosition(),
                lexicParser.Keyword(Keyword.FALSE),
                push(new ConstantExpression(positionTrackerParser.pop(), false))
        );
    }
}
