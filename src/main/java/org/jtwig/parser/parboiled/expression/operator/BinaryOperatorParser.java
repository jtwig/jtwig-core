package org.jtwig.parser.parboiled.expression.operator;

import org.jtwig.model.expression.operation.binary.BinaryOperator;
import org.jtwig.model.expression.operation.binary.calculators.BinaryOperationCalculator;
import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.base.BasicParser;
import org.jtwig.parser.parboiled.base.LexicParser;
import org.jtwig.parser.parboiled.base.SpacingParser;
import org.parboiled.Rule;

import java.util.List;

public class BinaryOperatorParser extends BasicParser<BinaryOperationCalculator> {
    public BinaryOperatorParser(ParserContext context) {
        super(BinaryOperatorParser.class, context);
    }

    Rule operator(BinaryOperator operator) {
        if (endsWithNonSymbol(operator.symbol())) {
            return Sequence(
                    String(operator.symbol()),
                    TestNot(parserContext().parser(LexicParser.class).LetterOrDigit()),
                    push(operator.calculator())
            );
        } else {
            return Sequence(
                    String(operator.symbol()),
                    push(operator.calculator())
            );
        }
    }

    public Rule BinaryOperator(List<BinaryOperator> operators) {
        Rule[] rules = new Rule[operators.size()];
        for (int i = 0; i < operators.size(); i++) {
            rules[i] = operator(operators.get(i));
        }
        return FirstOf(rules);
    }


    boolean endsWithNonSymbol(String symbol) {
        return symbol.matches("[a-zA-Z_]$");
    }
}
