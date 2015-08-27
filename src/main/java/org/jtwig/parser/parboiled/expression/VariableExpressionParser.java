package org.jtwig.parser.parboiled.expression;

import org.jtwig.model.expression.VariableExpression;
import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.base.LexicParser;
import org.jtwig.parser.parboiled.base.PositionTrackerParser;
import org.parboiled.Rule;

public class VariableExpressionParser extends ExpressionParser<VariableExpression> {
    public VariableExpressionParser(ParserContext context) {
        super(VariableExpressionParser.class, context);
    }

    @Override
    public Rule ExpressionRule() {
        return Sequence(
                parserContext().parser(PositionTrackerParser.class).PushPosition(),
                parserContext().parser(LexicParser.class).Identifier(),
                push(new VariableExpression(parserContext().parser(PositionTrackerParser.class).pop(), parserContext().parser(LexicParser.class).match()))
        );
    }
}
