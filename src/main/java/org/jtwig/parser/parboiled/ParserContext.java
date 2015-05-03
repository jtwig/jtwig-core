package org.jtwig.parser.parboiled;

import org.jtwig.parser.config.ParserConfiguration;
import org.jtwig.parser.config.ParserConfigurationBuilder;
import org.jtwig.parser.parboiled.base.*;
import org.jtwig.parser.parboiled.expression.*;
import org.jtwig.parser.parboiled.expression.operator.BinaryOperatorParser;
import org.jtwig.parser.parboiled.expression.operator.UnaryOperatorParser;
import org.jtwig.parser.parboiled.node.*;
import org.parboiled.BaseParser;

import java.util.*;

import static org.parboiled.Parboiled.createParser;

public class ParserContext {
    public static ParserContext instance (ParserConfiguration configuration, Collection<Class<? extends AddonParser>> addOnParsers) {
        ParserContext context = new ParserContext(configuration, addOnParsers);

        createParser(BooleanParser.class, context);
        createParser(PositionTrackerParser.class, context);
        createParser(SpacingParser.class, context);
        createParser(LexicParser.class, context);
        createParser(LimitsParser.class, context);
        createParser(CommentParser.class, context);

        createParser(UnaryOperatorParser.class, context);
        createParser(BinaryOperatorParser.class, context);

        createParser(EnumerationListExpressionParser.class, context);
        createParser(ComprehensionListExpressionParser.class, context);
        createParser(MapExpressionParser.class, context);
        createParser(StringExpressionParser.class, context);
        createParser(NumberExpressionParser.class, context);
        createParser(BooleanExpressionParser.class, context);
        createParser(ConstantExpressionParser.class, context);
        createParser(VariableExpressionParser.class, context);
        createParser(FunctionExpressionParser.class, context);
        createParser(UnaryOperationExpressionParser.class, context);
        createParser(BinaryOperationExpressionParser.class, context);
        createParser(TernaryOperationExpressionParser.class, context);
        createParser(PrimaryExpressionParser.class, context);
        createParser(AnyExpressionParser.class, context);

        createParser(SetNodeParser.class, context);
        createParser(IncludeNodeParser.class, context);
        createParser(BlockNodeParser.class, context);
        createParser(IfNodeParser.class, context);
        createParser(ForLoopNodeParser.class, context);
        createParser(ImportNodeParser.class, context);
        createParser(TextNodeParser.class, context);
        createParser(MacroNodeParser.class, context);
        createParser(ExtendsNodeParser.class, context);
        createParser(EmbedNodeParser.class, context);
        createParser(OutputNodeParser.class, context);
        createParser(AutoEscapeNodeParser.class, context);

        for (Class<? extends AddonParser> addOnParser : addOnParsers) {
            createParser((Class) addOnParser, context);
        }
        createParser(CompositeNodeParser.class, context);


        createParser(DocumentParser.class, context);

        return context;
    }

    public static ParserContext instance () {
        return instance(ParserConfigurationBuilder.parserConfiguration().build(), new ArrayList<Class<? extends AddonParser>>());
    }

    private final ParserConfiguration parserConfiguration;
    private final Map<Class, BaseParser> parsers;
    private final Collection<Class<? extends AddonParser>> addOnParsers;

    public ParserContext(ParserConfiguration parserConfiguration, Collection<Class<? extends AddonParser>> addOnParsers) {
        this.parserConfiguration = parserConfiguration;
        this.parsers = new HashMap<>();
        this.addOnParsers = addOnParsers;
    }
    public ParserContext(ParserConfiguration parserConfiguration) {
        this.parserConfiguration = parserConfiguration;
        this.parsers = new HashMap<>();
        this.addOnParsers = new ArrayList<>();
    }

    public <T extends BaseParser> ParserContext register (Class type, T parser) {
        this.parsers.put(type, parser);
        return this;
    }

    public Collection<Class<? extends AddonParser>> getAddOnParsers() {
        return addOnParsers;
    }

    public <T extends BasicParser> T parser (Class<T> type) {
        return (T) parsers.get(type);
    }

    public ParserConfiguration parserConfiguration() {
        return parserConfiguration;
    }

    public Collection<BaseParser> parsers() {
        return parsers.values();
    }
}
