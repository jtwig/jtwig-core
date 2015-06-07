package org.jtwig.parser.parboiled.expression.operator;

import org.jtwig.model.expression.operation.unary.UnaryOperator;
import org.jtwig.model.expression.operation.unary.calculators.UnaryOperationCalculator;
import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.base.BasicParser;
import org.jtwig.parser.parboiled.base.LexicParser;
import org.jtwig.parser.parboiled.base.SpacingParser;
import org.parboiled.Rule;

import java.util.*;

public class UnaryOperatorParser extends BasicParser<UnaryOperationCalculator> {
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
                    push(unaryOperator.calculator())
            );
        } else {
            return Sequence(
                    String(unaryOperator.symbol()),
                    push(unaryOperator.calculator())
            );
        }
    }

    boolean endsWithNonSymbol(String symbol) {
        return symbol.matches("[a-zA-Z0-9_\\$]$");
    }
}
