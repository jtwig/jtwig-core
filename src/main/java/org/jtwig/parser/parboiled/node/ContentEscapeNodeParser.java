package org.jtwig.parser.parboiled.node;

import com.google.common.base.Optional;
import org.jtwig.model.tree.ContentEscapeNode;
import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.base.*;
import org.jtwig.parser.parboiled.expression.StringExpressionParser;
import org.parboiled.Rule;
import org.parboiled.annotations.Label;

import static org.parboiled.Parboiled.createParser;

public class ContentEscapeNodeParser extends NodeParser<ContentEscapeNode> {
    public ContentEscapeNodeParser(ParserContext context) {
        super(ContentEscapeNodeParser.class, context);
        createParser(EscapeModeParser.class, context);
    }

    @Override
    @Label("Auto Escape")
    public Rule NodeRule() {
        PositionTrackerParser positionTrackerParser = parserContext().parser(PositionTrackerParser.class);
        LimitsParser limitsParser = parserContext().parser(LimitsParser.class);
        LexicParser lexicParser = parserContext().parser(LexicParser.class);
        EscapeModeParser escapeModeParser = parserContext().parser(EscapeModeParser.class);
        SpacingParser spacingParser = parserContext().parser(SpacingParser.class);
        CompositeNodeParser nodeParser = parserContext().parser(CompositeNodeParser.class);
        return Sequence(
                positionTrackerParser.PushPosition(),

                // Open Tag
                Sequence(
                        limitsParser.startCode(),
                        spacingParser.Spacing(),
                        lexicParser.Keyword("contentescape"),
                        spacingParser.Spacing(),
                        FirstOf(
                                Sequence(
                                        escapeModeParser.EscapeMode(),
                                        spacingParser.Spacing()
                                ),
                                escapeModeParser.push(null)
                        ),
                        Mandatory(limitsParser.endCode(), "Missing code island end")
                ),

                nodeParser.NodeRule(),

                // End
                Mandatory(Sequence(
                        limitsParser.startCode(),
                        spacingParser.Spacing(),
                        lexicParser.Keyword("endcontentescape"),
                        spacingParser.Spacing(),
                        Mandatory(limitsParser.endCode(), "Missing code island end")
                ), "Missing endcontentescape tag"),

                push(new ContentEscapeNode(positionTrackerParser.pop(2), nodeParser.pop(), Optional.fromNullable(escapeModeParser.pop())))
        );
    }

    public static class EscapeModeParser extends BasicParser<String> {
        public EscapeModeParser(ParserContext context) {
            super(EscapeModeParser.class, context);
        }

        public Rule EscapeMode() {
            StringExpressionParser parser = parserContext().parser(StringExpressionParser.class);
            return FirstOf(
                    Sequence(
                            parser.ExpressionRule(),
                            push(parser.pop().getConstantValue().toString().toUpperCase())
                    ),
                    Sequence(
                            String("false"),
                            push("none")
                    ),
                    push(null)
            );
        }
    }
}
