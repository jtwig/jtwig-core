package org.jtwig.parser.parboiled;

import org.jtwig.parser.addon.AddonParserProvider;
import org.jtwig.parser.config.JtwigParserConfiguration;
import org.jtwig.parser.config.SyntaxConfiguration;
import org.jtwig.parser.parboiled.base.*;
import org.jtwig.parser.parboiled.expression.*;
import org.jtwig.parser.parboiled.expression.operator.BinaryOperatorParser;
import org.jtwig.parser.parboiled.expression.operator.UnaryOperatorParser;
import org.jtwig.parser.parboiled.expression.test.AnyTestExpressionParser;
import org.jtwig.parser.parboiled.expression.test.TestExpressionParser;
import org.jtwig.parser.parboiled.node.*;
import org.jtwig.render.expression.calculator.operation.binary.BinaryOperator;
import org.jtwig.render.expression.calculator.operation.unary.UnaryOperator;
import org.jtwig.resource.reference.ResourceReference;
import org.parboiled.BaseParser;

import java.util.*;

import static org.parboiled.Parboiled.createParser;

public class ParserContext {

    public static ParserContext instance (ResourceReference resource, JtwigParserConfiguration configuration,
                                          Collection<AddonParserProvider> addOnParsers,
                                          Collection<UnaryOperator> unaryOperators,
                                          Collection<BinaryOperator> binaryOperators,
                                          List<Class<? extends TestExpressionParser>> testExpressionParsers) {
        ParserContext context = new ParserContext(resource, configuration, addOnParsers);

        createParser(BooleanParser.class, context);
        createParser(PositionTrackerParser.class, context);
        createParser(SpacingParser.class, context);
        createParser(LexicParser.class, context, extraKeywords(addOnParsers));
        createParser(LimitsParser.class, context);
        createParser(CommentParser.class, context);

        for (Class<? extends TestExpressionParser> testExpressionParser : testExpressionParsers) {
            createParser(testExpressionParser, context);
        }

        createParser(AnyTestExpressionParser.class, context, testExpressionParsers);
        createParser(MapSelectionExpressionParser.class, context);

        createParser(UnaryOperatorParser.class, context, unaryOperators);
        createParser(BinaryOperatorParser.class, context);
        createParser(BinaryOrPrimaryExpressionParser.class, context);

        createParser(EnumerationListExpressionParser.class, context);
        createParser(ComprehensionListExpressionParser.class, context);
        createParser(MapExpressionParser.class, context);
        createParser(StringExpressionParser.class, context);
        createParser(NumberExpressionParser.class, context);
        createParser(BooleanExpressionParser.class, context);
        createParser(NullExpressionParser.class, context);
        createParser(ConstantExpressionParser.class, context);
        createParser(VariableExpressionParser.class, context);
        createParser(FunctionExpressionParser.class, context);
        createParser(TestOperationExpressionParser.class, context);
        createParser(UnaryOperationExpressionParser.class, context);
        createParser(BinaryOperationExpressionParser.class, context, binaryOperators);
        createParser(TernaryOperationExpressionParser.class, context);
        createParser(PrimaryExpressionParser.class, context);
        createParser(SimpleExpressionParser.class, context);
        createParser(AnyExpressionParser.class, context);

        createParser(SetNodeParser.class, context);
        createParser(DoNodeParser.class, context);
        createParser(FlushNodeParser.class, context);
        createParser(IncludeNodeParser.class, context);
        createParser(BlockNodeParser.class, context);
        createParser(OverrideBlockNodeParser.class, context);
        createParser(IfNodeParser.class, context);
        createParser(ForLoopNodeParser.class, context);
        createParser(ImportSelfNodeParser.class, context);
        createParser(ImportNodeParser.class, context);
        createParser(TextNodeParser.class, context);
        createParser(MacroNodeParser.class, context);
        createParser(ExtendsNodeParser.class, context);
        createParser(EmbedNodeParser.class, context);
        createParser(OutputNodeParser.class, context);
        createParser(AutoEscapeNodeParser.class, context);
        createParser(ContentEscapeNodeParser.class, context);
        createParser(VerbatimNodeParser.class, context);
        createParser(FilterNodeParser.class, context);

        for (AddonParserProvider provider : addOnParsers) {
            createParser((Class) provider.parser(), context);
        }

        createParser(CompositeNodeParser.class, context);


        createParser(DocumentParser.class, context);

        return context;
    }

    private static Collection<String> extraKeywords(Collection<AddonParserProvider> addOnParsers) {
        Collection<String> result = new ArrayList<>();
        for (AddonParserProvider provider : addOnParsers) {
            result.addAll(provider.keywords());
        }
        return result;
    }

    private final ResourceReference resource;
    private final JtwigParserConfiguration configuration;
    private final Map<Class, BaseParser> parsers;
    private final Collection<AddonParserProvider> addOnParsers;

    public ParserContext(ResourceReference resource, JtwigParserConfiguration configuration, Collection<AddonParserProvider> addOnParsers) {
        this.resource = resource;
        this.configuration = configuration;
        this.parsers = new HashMap<>();
        this.addOnParsers = addOnParsers;
    }

    public <T extends BaseParser> ParserContext register (Class type, T parser) {
        this.parsers.put(type, parser);
        return this;
    }

    public Collection<AddonParserProvider> getAddOnParsers() {
        return addOnParsers;
    }

    public <T extends BasicParser> T parser (Class<T> type) {
        return (T) parsers.get(type);
    }

    public SyntaxConfiguration syntaxConfiguration() {
        return configuration.getSyntaxConfiguration();
    }

    public JtwigParserConfiguration getConfiguration() {
        return configuration;
    }

    public ResourceReference resource() {
        return resource;
    }
}
