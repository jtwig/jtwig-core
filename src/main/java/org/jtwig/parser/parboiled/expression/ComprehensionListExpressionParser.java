package org.jtwig.parser.parboiled.expression;

import org.jtwig.model.expression.ComprehensionListExpression;
import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.base.PositionTrackerParser;
import org.jtwig.parser.parboiled.base.SpacingParser;
import org.parboiled.Rule;
import org.parboiled.annotations.Label;

public class ComprehensionListExpressionParser extends ExpressionParser<ComprehensionListExpression> {
    public ComprehensionListExpressionParser(ParserContext context) {
        super(ComprehensionListExpressionParser.class, context);
    }

    @Override
    @Label("Comprehension List")
    public Rule ExpressionRule() {
        PositionTrackerParser positionTrackerParser = parserContext().parser(PositionTrackerParser.class);
        SpacingParser spacingParser = parserContext().parser(SpacingParser.class);
        AnyExpressionParser parser = parserContext().parser(AnyExpressionParser.class);
        return Sequence(
                positionTrackerParser.PushPosition(),
                String("["),
                spacingParser.Spacing(),
                parser.ExpressionRule(),
                spacingParser.Spacing(),
                String(".."),
                spacingParser.Spacing(),
                parser.ExpressionRule(),
                spacingParser.Spacing(),
                Mandatory(String("]"), "Expecting end bracket"),
                push(new ComprehensionListExpression(positionTrackerParser.pop(2), parser.pop(1), parser.pop()))
        );
    }
}
