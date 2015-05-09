package org.jtwig.parser.parboiled.expression.test;

import org.jtwig.model.expression.test.SameAsTestExpression;
import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.base.SpacingParser;
import org.jtwig.parser.parboiled.expression.AnyExpressionParser;
import org.parboiled.Rule;

public class SameAsTestExpressionParser extends TestExpressionParser<SameAsTestExpression> {
    public SameAsTestExpressionParser(ParserContext context) {
        super(SameAsTestExpressionParser.class, context);
    }

    @Override
    public Rule Test() {
        SpacingParser spacingParser = parserContext().parser(SpacingParser.class);
        AnyExpressionParser anyExpressionParser = parserContext().parser(AnyExpressionParser.class);
        return Sequence(
                String("same"),
                spacingParser.Spacing(),
                String("as"),
                spacingParser.Spacing(),
                String("("),
                spacingParser.Spacing(),
                anyExpressionParser.ExpressionRule(),
                spacingParser.Spacing(),
                String(")"),

                push(new SameAsTestExpression(anyExpressionParser.pop()))
        );
    }
}
