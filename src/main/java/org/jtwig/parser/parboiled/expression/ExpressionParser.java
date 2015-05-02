package org.jtwig.parser.parboiled.expression;

import org.jtwig.model.expression.Expression;
import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.base.BasicParser;
import org.parboiled.Rule;

public abstract class ExpressionParser<T extends Expression> extends BasicParser<T> {
    public ExpressionParser(Class<? extends BasicParser> type, ParserContext context) {
        super(type, context);
    }

    public abstract Rule ExpressionRule ();
}
