package org.jtwig.parser.parboiled.node;

import org.jtwig.model.tree.ExtendsNode;
import org.jtwig.model.tree.Node;
import org.jtwig.parser.ParseException;
import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.base.*;
import org.jtwig.parser.parboiled.expression.AnyExpressionParser;
import org.jtwig.parser.parboiled.model.Keyword;
import org.jtwig.util.ErrorMessageFormatter;
import org.parboiled.Rule;

import java.util.ArrayList;
import java.util.Collection;

import static org.parboiled.Parboiled.createParser;

public class ExtendsNodeParser extends NodeParser<ExtendsNode> {
    public ExtendsNodeParser(ParserContext context) {
        super(ExtendsNodeParser.class, context);
        createParser(ExtendDefinitionsParser.class, context);
    }

    @Override
    public Rule NodeRule() {
        SpacingParser spacingParser = parserContext().parser(SpacingParser.class);
        LimitsParser limitsParser = parserContext().parser(LimitsParser.class);
        AnyExpressionParser anyExpressionParser = parserContext().parser(AnyExpressionParser.class);
        ExtendDefinitionsParser definitionsParser = parserContext().parser(ExtendDefinitionsParser.class);
        PositionTrackerParser positionTrackerParser = parserContext().parser(PositionTrackerParser.class);
        return Sequence(
                spacingParser.Spacing(),
                positionTrackerParser.PushPosition(),
                limitsParser.startCode(), spacingParser.Spacing(),
                parserContext().parser(LexicParser.class).Keyword(Keyword.EXTENDS),
                spacingParser.Spacing(),
                Mandatory(anyExpressionParser.ExpressionRule(), "Extends construct missing path expression"),
                spacingParser.Spacing(),
                Mandatory(limitsParser.endCode(), "Code island not closed"),
                spacingParser.Spacing(),

                definitionsParser.Definitions(),
                spacingParser.Spacing(),

                push(new ExtendsNode(
                        positionTrackerParser.pop(2),
                        anyExpressionParser.pop(1),
                        definitionsParser.pop()
                ))
        );
    }

    public static class ExtendDefinitionsParser extends BasicParser<Collection<Node>> {
        public ExtendDefinitionsParser(ParserContext context) {
            super(ExtendDefinitionsParser.class, context);
        }

        Rule Definitions () {
            OverrideBlockNodeParser blockNodeParser = parserContext().parser(OverrideBlockNodeParser.class);
            SetNodeParser setNodeParser = parserContext().parser(SetNodeParser.class);
            ImportNodeParser importNodeParser = parserContext().parser(ImportNodeParser.class);
            return Sequence(
                    push(new ArrayList<Node>()),
                    ZeroOrMore(
                            FirstOf(
                                    Sequence(
                                            blockNodeParser.NodeRule(),
                                            peek(1).add(blockNodeParser.pop())
                                    ),
                                    Sequence(
                                            setNodeParser.NodeRule(),
                                            peek(1).add(setNodeParser.pop())
                                    ),
                                    Sequence(
                                            importNodeParser.NodeRule(),
                                            peek(1).add(importNodeParser.pop())
                                    ),
                                    Sequence(
                                            parserContext().parser(CommentParser.class).Comment(),
                                            parserContext().parser(SpacingParser.class).Spacing()
                                    ),
                                    invalidConstruct()
                            ),
                            parserContext().parser(SpacingParser.class).Spacing()
                    )
            );
        }

        public Rule invalidConstruct() {
            return Sequence(
                    ANY,
                    throwException("Extends templates only allow you to specify either block or set constructs")
            );
        }

        public boolean throwException(String message) {
            throw new ParseException(ErrorMessageFormatter.errorMessage(parserContext().parser(PositionTrackerParser.class).currentPosition(), message));
        }
    }
}
