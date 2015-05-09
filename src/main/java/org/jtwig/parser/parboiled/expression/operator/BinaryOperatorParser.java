package org.jtwig.parser.parboiled.expression.operator;

import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import org.jtwig.model.expression.operation.BinaryOperator;
import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.base.BasicParser;
import org.parboiled.Rule;

import java.util.HashMap;
import java.util.Map;

public class BinaryOperatorParser extends BasicParser<BinaryOperator> {
    final Map<BinaryOperator, Rule> operatorRules;


    public BinaryOperatorParser(ParserContext context) {
        super(BinaryOperatorParser.class, context);
        operatorRules = new HashMap<BinaryOperator, Rule>() {{
            put(BinaryOperator.AND, operator(BinaryOperator.AND, "and"));
            put(BinaryOperator.OR, operator(BinaryOperator.OR, "or"));

            put(BinaryOperator.DIFFERENT, operator(BinaryOperator.DIFFERENT, "!="));
            put(BinaryOperator.EQUIVALENT, operator(BinaryOperator.EQUIVALENT, "=="));
            put(BinaryOperator.LESS_OR_EQUAL, operator(BinaryOperator.LESS_OR_EQUAL, "<="));
            put(BinaryOperator.LESS, operator(BinaryOperator.LESS, "<"));
            put(BinaryOperator.GREATER_OR_EQUAL, operator(BinaryOperator.GREATER_OR_EQUAL, ">="));
            put(BinaryOperator.GREATER, operator(BinaryOperator.GREATER, ">"));

            put(BinaryOperator.INT_DIVIDE, operator(BinaryOperator.INT_DIVIDE, "//"));
            put(BinaryOperator.DIVIDE, operator(BinaryOperator.DIVIDE, "/"));
            put(BinaryOperator.INT_MULTIPLY, operator(BinaryOperator.INT_MULTIPLY, "**"));
            put(BinaryOperator.MULTIPLY, operator(BinaryOperator.MULTIPLY, "*"));
            put(BinaryOperator.MOD, operator(BinaryOperator.MOD, "%"));
            put(BinaryOperator.SUBTRACT, operator(BinaryOperator.SUBTRACT, "-"));
            put(BinaryOperator.SUM, operator(BinaryOperator.SUM, "+"));

            put(BinaryOperator.SELECTION, operator(BinaryOperator.SELECTION, Sequence(".", TestNot("."))));
            put(BinaryOperator.COMPOSITION, operator(BinaryOperator.COMPOSITION, "|"));
            put(BinaryOperator.IN, operator(BinaryOperator.IN, "in"));
            put(BinaryOperator.CONCAT, operator(BinaryOperator.CONCAT, "~"));
        }};
    }

    Rule operator(BinaryOperator operator, String symbol) {
        return Sequence(
                String(symbol),
                push(operator)
        );
    }

    Rule operator(BinaryOperator operator, Rule rule) {
        return Sequence(
                rule,
                push(operator)
        );
    }

    public Rule BinaryOperator(final BinaryOperator... operator) {
        Rule[] rules = new Rule[operator.length];
        for (int i = 0; i < operator.length; i++) {
            final int finalI = i;
            rules[i] = Optional.fromNullable(operatorRules.get(operator[i]))
                .or(new Supplier<Rule>() {
                    @Override
                    public Rule get() {
                        throw new IllegalArgumentException(String.format("Operator %s parsing not implemented", operator[finalI]));
                    }
                });
        }
        return FirstOf(rules);
    }
}
