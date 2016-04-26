package org.jtwig.parser.parboiled.expression;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jtwig.model.expression.ConstantExpression;
import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.base.BasicParser;
import org.jtwig.parser.parboiled.base.PositionTrackerParser;
import org.parboiled.Rule;

import static org.parboiled.Parboiled.createParser;

public class StringExpressionParser extends ExpressionParser<ConstantExpression> {
    public StringExpressionParser(ParserContext context) {
        super(StringExpressionParser.class, context);
        createParser(StringParser.class, context);
    }

    @Override
    public Rule ExpressionRule() {
        PositionTrackerParser positionTrackerParser = parserContext().parser(PositionTrackerParser.class);
        StringParser stringParser = parserContext().parser(StringParser.class);
        return Sequence(
                positionTrackerParser.PushPosition(),
                stringParser.StringRule(),
                swap(),
                push(new ConstantExpression(positionTrackerParser.pop(), stringParser.pop().toString()))
        );
    }

    public static class StringParser extends BasicParser<StringBuilder> {
        public StringParser(ParserContext context) {
            super(StringParser.class, context);
        }

        public Rule StringRule() {
            return Sequence(
                    push(new StringBuilder()),
                    FirstOf(
                            StringWith('\''),
                            StringWith('"')
                    )
            );
        }

        Rule StringWith(char start) {
            return Sequence(
                    String(start),
                    ZeroOrMore(
                            FirstOf(
                                    Escape(),
                                    Others(start)
                            )
                    ),
                    String(start)
            );
        }

        Rule Others(char start) {
            return Sequence(
                    Sequence(TestNot(AnyOf(new char[]{'\n', '\r', '\\', start})), ANY),
                    run(peek().append(match()))
            );
        }

        Rule Escape() {
            return Sequence(Sequence(
                            '\\',
                            ANY
                    ),
                    run(peek().append(StringEscapeUtils.unescapeJava(match())))
            );
        }

        boolean run (Object object) {
            return true;
        }
    }
}
