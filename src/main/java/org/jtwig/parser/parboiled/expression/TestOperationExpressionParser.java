package org.jtwig.parser.parboiled.expression;

import org.jtwig.model.expression.TestOperationExpression;
import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.base.LexicParser;
import org.jtwig.parser.parboiled.base.PositionTrackerParser;
import org.jtwig.parser.parboiled.base.SpacingParser;
import org.jtwig.parser.parboiled.expression.test.AnyTestExpressionParser;
import org.jtwig.parser.parboiled.model.Keyword;
import org.parboiled.Rule;
import org.parboiled.annotations.Label;

public class TestOperationExpressionParser extends ExpressionParser<TestOperationExpression> {
    public TestOperationExpressionParser(ParserContext context) {
        super(TestOperationExpressionParser.class, context);
    }

    @Override
    @Label("Test Operation")
    public Rule ExpressionRule() {
        SpacingParser spacingParser = parserContext().parser(SpacingParser.class);
        PrimaryExpressionParser anyExpressionParser = parserContext().parser(PrimaryExpressionParser.class);
        AnyTestExpressionParser testExpressionParser = parserContext().parser(AnyTestExpressionParser.class);
        PositionTrackerParser positionTrackerParser = parserContext().parser(PositionTrackerParser.class);
        return Sequence(
                positionTrackerParser.PushPosition(),
                anyExpressionParser.ExpressionRule(),
                spacingParser.Spacing(),
                parserContext().parser(LexicParser.class).Keyword(Keyword.IS),
                spacingParser.Spacing(),
                testExpressionParser.Test(),

                push(new TestOperationExpression(positionTrackerParser.pop(2), anyExpressionParser.pop(1), testExpressionParser.pop()))
        );
    }
}
