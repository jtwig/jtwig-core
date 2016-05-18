package org.jtwig.parser.parboiled.expression.test;

import org.jtwig.model.expression.test.NotTestExpression;
import org.jtwig.model.expression.test.TestExpression;
import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.base.BasicParser;
import org.jtwig.parser.parboiled.base.LexicParser;
import org.jtwig.parser.parboiled.base.SpacingParser;
import org.jtwig.parser.parboiled.model.Keyword;
import org.parboiled.Rule;

import java.util.List;

import static org.parboiled.Parboiled.createParser;

public class AnyTestExpressionParser extends TestExpressionParser<TestExpression> {
    private final List<Class<? extends TestExpressionParser>> testExpressionParsers;

    public AnyTestExpressionParser(ParserContext context, List<Class<? extends TestExpressionParser>> testExpressionParsers) {
        super(AnyTestExpressionParser.class, context);
        this.testExpressionParsers = testExpressionParsers;
        createParser(NotParser.class, context);
    }

    @Override
    public Rule Test() {
        NotParser notParser = parserContext().parser(NotParser.class);
        TestExpressionParser[] parsers = new TestExpressionParser[testExpressionParsers.size()];
        for (int i = 0; i < parsers.length; i++) {
            parsers[i] = parserContext().parser(testExpressionParsers.get(i));
        }
        return Sequence(
                notParser.Rule(),
                FirstOf(rulesFor(parsers, notParser))
        );
    }

    Rule[] rulesFor(TestExpressionParser[] parsers, NotParser notParser) {
        Rule[] rules = new Rule[parsers.length];
        for (int i = 0; i < parsers.length; i++) {
            rules[i] = ruleFor(parsers[i], notParser);
        }
        return rules;
    }

    Rule ruleFor(TestExpressionParser parser, NotParser notParser) {
        return Sequence(
                parser.Test(),
                push(NotTestExpression.create(notParser.pop(1), (TestExpression) parser.pop()))
        );
    }

    public static class NotParser extends BasicParser<Boolean> {
        public NotParser(ParserContext context) {
            super(NotParser.class, context);
        }

        public Rule Rule () {
            return FirstOf(
                    Sequence(
                            parserContext().parser(LexicParser.class).Keyword(Keyword.NOT),
                            parserContext().parser(SpacingParser.class).Spacing(),
                            push(true)
                    ),
                    push(false)
            );
        }
    }
}
