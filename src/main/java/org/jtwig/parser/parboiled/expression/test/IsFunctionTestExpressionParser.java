package org.jtwig.parser.parboiled.expression.test;

import org.jtwig.model.expression.test.IsFunctionTestExpression;
import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.base.SpacingParser;
import org.parboiled.Rule;

public class IsFunctionTestExpressionParser extends TestExpressionParser<IsFunctionTestExpression> {
    public IsFunctionTestExpressionParser(ParserContext context) {
        super(IsFunctionTestExpressionParser.class, context);
    }

    @Override
    public Rule Test() {
        SpacingParser spacingParser = parserContext().parser(SpacingParser.class);

        return Sequence(
                String("function"),
                spacingParser.Spacing(),
                push(new IsFunctionTestExpression())
        );
    }
}
