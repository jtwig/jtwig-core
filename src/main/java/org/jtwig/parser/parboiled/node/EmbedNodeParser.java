package org.jtwig.parser.parboiled.node;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.MapExpression;
import org.jtwig.model.tree.EmbedNode;
import org.jtwig.model.tree.Node;
import org.jtwig.model.tree.include.IncludeConfiguration;
import org.jtwig.parser.ParseException;
import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.base.*;
import org.jtwig.parser.parboiled.expression.AnyExpressionParser;
import org.jtwig.parser.parboiled.model.Keyword;
import org.jtwig.util.ErrorMessageFormatter;
import org.parboiled.Rule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import static org.parboiled.Parboiled.createParser;

public class EmbedNodeParser extends NodeParser<EmbedNode> {
    public EmbedNodeParser(ParserContext context) {
        super(EmbedNodeParser.class, context);
        createParser(DefinitionsParser.class, context, endEmbed());
    }

    @Override
    public Rule NodeRule() {
        PositionTrackerParser positionTrackerParser = parserContext().parser(PositionTrackerParser.class);
        LimitsParser limitsParser = parserContext().parser(LimitsParser.class);
        SpacingParser spacingParser = parserContext().parser(SpacingParser.class);
        LexicParser lexicParser = parserContext().parser(LexicParser.class);
        AnyExpressionParser anyExpressionParser = parserContext().parser(AnyExpressionParser.class);
        DefinitionsParser definitionsParser = parserContext().parser(DefinitionsParser.class);
        BooleanParser booleanParser = parserContext().parser(BooleanParser.class);
        return Sequence(
                positionTrackerParser.PushPosition(),

                // Start
                Sequence(
                        limitsParser.startCode(), spacingParser.Spacing(),
                        lexicParser.Keyword(Keyword.EMBED), spacingParser.Spacing(),
                        Mandatory(anyExpressionParser.ExpressionRule(), "Embed construct missing path expression"),
                        spacingParser.Spacing(),
                        FirstOf(
                                Sequence(
                                        String("ignore"),
                                        spacingParser.Spacing(),
                                        Mandatory(String("missing"), "Did you mean 'ignore missing'?"),
                                        spacingParser.Spacing(),
                                        booleanParser.push(true)
                                ),
                                booleanParser.push(false)

                        ),
                        FirstOf(
                                Sequence(
                                        String("with"), spacingParser.Spacing(),
                                        anyExpressionParser.ExpressionRule(),
                                        spacingParser.Spacing()
                                ),
                                anyExpressionParser.push(new MapExpression(positionTrackerParser.currentPosition(), new HashMap<String, Expression>()))
                        ),
                        FirstOf(
                                Sequence(
                                        String("only"), spacingParser.Spacing(),
                                        booleanParser.push(false)
                                ),
                                booleanParser.push(true)
                        ),
                        Mandatory(limitsParser.endCode(), "Code island not closed")
                ),

                spacingParser.Spacing(),
                definitionsParser.Definitions(),
                spacingParser.Spacing(),

                // Stop
                Mandatory(endEmbed(), "Missing endembed tag"),

                push(new EmbedNode(
                        positionTrackerParser.pop(5),
                        definitionsParser.pop(),
                        new IncludeConfiguration(
                                anyExpressionParser.pop(3),
                                anyExpressionParser.pop(1),
                                booleanParser.pop(),
                                booleanParser.pop())
                ))
        );
    }

    public Rule endEmbed() {
        LimitsParser limitsParser = parserContext().parser(LimitsParser.class);
        SpacingParser spacingParser = parserContext().parser(SpacingParser.class);
        LexicParser lexicParser = parserContext().parser(LexicParser.class);
        return Sequence(
                spacingParser.Spacing(),
                limitsParser.startCode(), spacingParser.Spacing(),
                lexicParser.Keyword(Keyword.END_EMBED), spacingParser.Spacing(),
                limitsParser.endCode()
        );
    }

    public static class DefinitionsParser extends BasicParser<Collection<Node>> {
        final Rule endEmbed;

        public DefinitionsParser(ParserContext context, Rule endEmbed) {
            super(DefinitionsParser.class, context);
            this.endEmbed = endEmbed;
        }

        Rule Definitions() {
            BlockNodeParser blockNodeParser = parserContext().parser(BlockNodeParser.class);
            return Sequence(
                    push(new ArrayList<Node>()),

                    ZeroOrMore(
                            FirstOf(
                                    Sequence(
                                            blockNodeParser.NodeRule(),
                                            parserContext().parser(SpacingParser.class).Spacing(),
                                            peek(1).add(blockNodeParser.pop())
                                    ),
                                    Sequence(
                                            parserContext().parser(CommentParser.class).Comment(),
                                            parserContext().parser(SpacingParser.class).Spacing()
                                    ),
                                    invalidConstruct()
                            )
                    )
            );
        }


        public Rule invalidConstruct() {
            return Sequence(
                    TestNot(endEmbed),
                    throwException("Embed construct can only contain block elements. You might be missing the endembed tag.")
            );
        }

        public boolean throwException(String message) {
            throw new ParseException(ErrorMessageFormatter.errorMessage(parserContext().parser(PositionTrackerParser.class).currentPosition(), message));
        }
    }
}
