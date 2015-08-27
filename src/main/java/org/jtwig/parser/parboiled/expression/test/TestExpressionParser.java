package org.jtwig.parser.parboiled.expression.test;

import org.jtwig.model.expression.test.TestExpression;
import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.base.BasicParser;
import org.parboiled.Rule;

public abstract class TestExpressionParser<T extends TestExpression> extends BasicParser<T> {
    public TestExpressionParser(Class<? extends BasicParser> type, ParserContext context) {
        super(type, context);
    }

    public abstract Rule Test ();
}
