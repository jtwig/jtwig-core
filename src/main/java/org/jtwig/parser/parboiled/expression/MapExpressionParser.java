package org.jtwig.parser.parboiled.expression;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.MapExpression;
import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.base.BasicParser;
import org.jtwig.parser.parboiled.base.PositionTrackerParser;
import org.jtwig.parser.parboiled.base.SpacingParser;
import org.parboiled.Rule;

import java.util.HashMap;
import java.util.Map;

import static org.parboiled.Parboiled.createParser;

public class MapExpressionParser extends ExpressionParser<MapExpression> {
    public MapExpressionParser(ParserContext context) {
        super(MapExpressionParser.class, context);
        createParser(MapParser.class, context);
        createParser(StringOrIdentifierParser.class, context);
    }

    @Override
    public Rule ExpressionRule() {
        SpacingParser spacingParser = parserContext().parser(SpacingParser.class);
        MapParser mapParser = parserContext().parser(MapParser.class);
        PositionTrackerParser positionTrackerParser = parserContext().parser(PositionTrackerParser.class);
        return Sequence(
                positionTrackerParser.PushPosition(),
                String("{"),
                spacingParser.Spacing(),
                mapParser.Expression(),
                String("}"),
                push(new MapExpression(positionTrackerParser.pop(1), mapParser.pop()))
        );
    }

    public static class MapParser extends BasicParser<Map<String, Expression>> {
        public MapParser(ParserContext context) {
            super(MapParser.class, context);
        }

        public Rule Expression () {
            AnyExpressionParser parser = parserContext().parser(AnyExpressionParser.class);
            StringOrIdentifierParser stringOrIdentifierParser = parserContext().parser(StringOrIdentifierParser.class);
            SpacingParser spacingParser = parserContext().parser(SpacingParser.class);
            return Sequence(
                    push(new HashMap<String, Expression>()),
                    Optional(
                            stringOrIdentifierParser.Expression(),
                            spacingParser.Spacing(),
                            String(":"),
                            spacingParser.Spacing(),
                            parser.ExpressionRule(),
                            run(peek(2).put(stringOrIdentifierParser.pop(1), parser.pop())),
                            ZeroOrMore(
                                    spacingParser.Spacing(),
                                    String(","),
                                    spacingParser.Spacing(),
                                    stringOrIdentifierParser.Expression(),
                                    spacingParser.Spacing(),
                                    String(":"),
                                    spacingParser.Spacing(),
                                    parser.ExpressionRule(),
                                    run(peek(2).put(stringOrIdentifierParser.pop(1), parser.pop()))
                            )
                    )
            );
        }

        public boolean run (Object value) {
            return true;
        }
    }



    public static class StringOrIdentifierParser extends BasicParser<String> {
        public StringOrIdentifierParser(ParserContext context) {
            super(StringOrIdentifierParser.class, context);
        }

        public Rule Expression () {
            StringExpressionParser stringExpressionParser = parserContext().parser(StringExpressionParser.class);
            NumberExpressionParser numberExpressionParser = parserContext().parser(NumberExpressionParser.class);
            VariableExpressionParser variableExpressionParser = parserContext().parser(VariableExpressionParser.class);
            return FirstOf(
                    Sequence(
                            stringExpressionParser.ExpressionRule(),
                            push(stringExpressionParser.pop().getConstantValue().toString())
                    ),
                    Sequence(
                            numberExpressionParser.ExpressionRule(),
                            push(numberExpressionParser.pop().getConstantValue().toString())
                    ),
                    Sequence(
                            variableExpressionParser.ExpressionRule(),
                            push(variableExpressionParser.pop().getIdentifier())
                    )
            );
        }
    }
}
