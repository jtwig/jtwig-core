package org.jtwig.parser.parboiled.expression;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimaps;
import org.jtwig.model.expression.BinaryOperationExpression;
import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.base.PositionTrackerParser;
import org.jtwig.parser.parboiled.base.SpacingParser;
import org.jtwig.parser.parboiled.expression.operator.BinaryOperatorParser;
import org.jtwig.render.expression.calculator.operation.binary.BinaryOperator;
import org.parboiled.Rule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class BinaryOperationExpressionParser extends ExpressionParser<BinaryOperationExpression> {
    final Collection<BinaryOperator> operators;

    public BinaryOperationExpressionParser(ParserContext context, Collection<BinaryOperator> operators) {
        super(BinaryOperationExpressionParser.class, context);
        this.operators = operators;
    }

    @Override
    public Rule ExpressionRule() {
        Rule initialExpression = parserContext().parser(PrimaryExpressionParser.class).ExpressionRule();
        ImmutableListMultimap<Integer, BinaryOperator> index = Multimaps.index(operators, precedence());

        List<Integer> integers = new ArrayList<>(index.keySet());
        Collections.sort(integers);
        for (Integer integer : integers) {
            initialExpression = BinaryOperation(initialExpression, index.get(integer));
        }
        return initialExpression;
    }

    Function<BinaryOperator, Integer> precedence() {
        return new Function<BinaryOperator, Integer>() {
            @Override
            public Integer apply(BinaryOperator input) {
                return input.precedence();
            }
        };
    }

    Rule BinaryOperation(Rule expressionRule, List<BinaryOperator> operator) {
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
