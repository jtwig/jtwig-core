package org.jtwig.parser.parboiled.expression;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimaps;
import org.jtwig.model.expression.BinaryOperationExpression;
import org.jtwig.model.expression.operation.BinaryOperator;
import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.base.PositionTrackerParser;
import org.jtwig.parser.parboiled.base.SpacingParser;
import org.jtwig.parser.parboiled.expression.operator.BinaryOperatorParser;
import org.parboiled.Rule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;

public class BinaryOperationExpressionParser extends ExpressionParser<BinaryOperationExpression> {
    public BinaryOperationExpressionParser(ParserContext context) {
        super(BinaryOperationExpressionParser.class, context);
    }

    @Override
    public Rule ExpressionRule() {
        Rule initialExpression = parserContext().parser(PrimaryExpressionParser.class).ExpressionRule();
        ImmutableListMultimap<Integer, BinaryOperator> index = Multimaps.index(asList(BinaryOperator.values()), new Function<BinaryOperator, Integer>() {
            @Override
            public Integer apply(BinaryOperator input) {
                return input.precedence();
            }
        });

        List<Integer> integers = new ArrayList<Integer>(index.keySet());
        Collections.sort(integers);
        for (Integer integer : integers) {
            ImmutableList<BinaryOperator> binaryOperators = index.get(integer);
            initialExpression = BinaryOperation(initialExpression, binaryOperators.toArray(new BinaryOperator[binaryOperators.size()]));
        }
        return initialExpression;
    }

    Rule BinaryOperation(Rule expressionRule, BinaryOperator[] operator) {
        return Sequence(
                expressionRule,
                parserContext().parser(SpacingParser.class).Spacing(),
                ZeroOrMore(
                        parserContext().parser(PositionTrackerParser.class).PushPosition(),
                        parserContext().parser(BinaryOperatorParser.class).BinaryOperator(operator),
                        parserContext().parser(SpacingParser.class).Spacing(),
                        expressionRule,
                        push(new BinaryOperationExpression(
                                parserContext().parser(PositionTrackerParser.class).pop(2),
                                pop(2),
                                parserContext().parser(BinaryOperatorParser.class).pop(1),
                                pop()
                        ))
                )

        );
    }
}
