package org.jtwig.parser.parboiled.expression.operator;

import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.base.BasicParser;
import org.jtwig.parser.parboiled.base.LexicParser;
import org.jtwig.render.expression.calculator.operation.unary.UnaryOperator;
import org.parboiled.Rule;

import java.util.*;

public class UnaryOperatorParser extends BasicParser<UnaryOperator> {
    final List<UnaryOperator> operators;

    public UnaryOperatorParser(ParserContext context, Collection<UnaryOperator> operators) {
        super(UnaryOperatorParser.class, context);
        this.operators = new ArrayList<>(operators);
    }

    public Rule UnaryOperator() {
        Collections.sort(operators, new Comparator<UnaryOperator>() {
            @Override
            public int compare(UnaryOperator o1, UnaryOperator o2) {
                return new Integer(o1.precedence()).compareTo(o2.precedence());
            }
        });
        Rule[] rules = new Rule[operators.size()];

        for (int i = 0; i < operators.size(); i++) {
            rules[i] = UnaryOperator(operators.get(i));
        }

        return FirstOf(rules);
    }

    Rule UnaryOperator(UnaryOperator unaryOperator) {
        if (endsWithNonSymbol(unaryOperator.symbol())) {
            return Sequence(
                    String(unaryOperator.symbol()),
                    TestNot(parserContext().parser(LexicParser.class).LetterOrDigit()),
                    push(unaryOperator)
            );
        } else {
            return Sequence(
                    String(unaryOperator.symbol()),
                    push(unaryOperator)
            );
        }
    }

    boolean endsWithNonSymbol(String symbol) {
        return symbol.matches(".*[a-zA-Z0-9_\\$]$");
    }
}
