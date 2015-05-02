package org.jtwig.parser.parboiled.expression.operator;

import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import org.jtwig.model.expression.operation.UnaryOperator;
import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.base.BasicParser;
import org.parboiled.Rule;

import java.util.HashMap;
import java.util.Map;

public class UnaryOperatorParser extends BasicParser<UnaryOperator> {
    final Map<UnaryOperator, Rule> operatorRule;

    public UnaryOperatorParser(ParserContext context) {
        super(UnaryOperatorParser.class, context);
        this.operatorRule = new HashMap<UnaryOperator, Rule>() {{
            put(UnaryOperator.NEGATIVE, operator("-", UnaryOperator.NEGATIVE));
            put(UnaryOperator.NOT, operator("!", UnaryOperator.NOT));
        }};
    }

    public Rule UnaryOperator (final UnaryOperator... operators) {
        Rule[] rules = new Rule[operators.length];
        for (int i = 0; i < rules.length; i++) {
            final int finalI = i;
            rules[i] = Optional.fromNullable(operatorRule.get(operators[i]))
                .or(new Supplier<Rule>() {
                    @Override
                    public Rule get() {
                        throw new IllegalArgumentException(String.format("Operator %s parsing not implemented", operators[finalI]));
                    }
                });
        }
        return FirstOf(rules);
    }

    Rule operator(String symbol, UnaryOperator operator) {
        return Sequence(
                String(symbol),
                push(operator)
        );
    }
}
