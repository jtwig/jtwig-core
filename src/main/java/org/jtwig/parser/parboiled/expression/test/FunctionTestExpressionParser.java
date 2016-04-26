package org.jtwig.parser.parboiled.expression.test;

import org.jtwig.model.expression.test.FunctionTestExpression;
import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.expression.FunctionExpressionParser;
import org.jtwig.parser.parboiled.expression.VariableExpressionParser;
import org.parboiled.Rule;

public class FunctionTestExpressionParser extends TestExpressionParser<FunctionTestExpression> {
    public FunctionTestExpressionParser(ParserContext context) {
        super(FunctionTestExpressionParser.class, context);
    }

    @Override
    public Rule Test() {
        FunctionExpressionParser functionExpressionParser = parserContext().parser(FunctionExpressionParser.class);
        VariableExpressionParser variableExpressionParser = parserContext().parser(VariableExpressionParser.class);
        return FirstOf(
                Sequence(
                        functionExpressionParser.ExpressionRule(),
                        push(new FunctionTestExpression(functionExpressionParser.pop()))
                ),
                Sequence(
                        variableExpressionParser.ExpressionRule(),
                        push(new FunctionTestExpression(variableExpressionParser.pop()))
                )
        );
    }
}
