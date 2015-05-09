package org.jtwig.parser.parboiled.expression.test;

import org.jtwig.model.expression.test.TestExpression;
import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.base.BasicParser;
import org.parboiled.Rule;

public class AnyTestExpressionParser extends TestExpressionParser<TestExpression> {
    public AnyTestExpressionParser(ParserContext context) {
        super(AnyTestExpressionParser.class, context);
    }

    @Override
    public Rule Test() {
        return FirstOf(
                parserContext().parser(NullTestExpressionParser.class).Test(),
                parserContext().parser(DivisibleByTestExpressionParser.class).Test(),
                parserContext().parser(SameAsTestExpressionParser.class).Test(),
                parserContext().parser(FunctionTestExpressionParser.class).Test()
        );
    }
}
