package org.jtwig.parser.parboiled;

import org.jtwig.parser.addon.AddonParserProvider;
import org.jtwig.parser.config.SyntaxConfiguration;
import org.jtwig.parser.config.SyntaxConfigurationBuilder;
import org.jtwig.parser.parboiled.base.*;
import org.jtwig.parser.parboiled.expression.*;
import org.jtwig.parser.parboiled.expression.operator.BinaryOperatorParser;
import org.jtwig.parser.parboiled.expression.operator.UnaryOperatorParser;
import org.jtwig.parser.parboiled.expression.test.*;
import org.jtwig.parser.parboiled.node.*;
import org.jtwig.resource.Resource;
import org.parboiled.BaseParser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.parboiled.Parboiled.createParser;

public class ParserContext {
    public static ParserContext instance (Resource resource, SyntaxConfiguration configuration, Collection<AddonParserProvider> addOnParsers) {
        ParserContext context = new ParserContext(resource, configuration, addOnParsers);

        createParser(BooleanParser.class, context);
        createParser(PositionTrackerParser.class, context);
        createParser(SpacingParser.class, context);
        createParser(LexicParser.class, context, extraKeywords(addOnParsers));
        createParser(LimitsParser.class, context);
        createParser(CommentParser.class, context);

        createParser(SameAsTestExpressionParser.class, context);
        createParser(DivisibleByTestExpressionParser.class, context);
        createParser(NullTestExpressionParser.class, context);
        createParser(FunctionTestExpressionParser.class, context);
        createParser(AnyTestExpressionParser.class, context);
        createParser(MapSelectionExpressionParser.class, context);

        createParser(UnaryOperatorParser.class, context);
        createParser(BinaryOperatorParser.class, context);

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
        createParser(BinaryOperationExpressionParser.class, context);
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
        createParser(ImportNodeParser.class, context);
        createParser(TextNodeParser.class, context);
        createParser(MacroNodeParser.class, context);
        createParser(ExtendsNodeParser.class, context);
        createParser(EmbedNodeParser.class, context);
        createParser(OutputNodeParser.class, context);
        createParser(AutoEscapeNodeParser.class, context);
        createParser(VerbatimNodeParser.class, context);
        createParser(SpacelessNodeParser.class, context);
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

    public static ParserContext instance (Resource resource) {
        return instance(resource, SyntaxConfigurationBuilder.syntaxConfiguration().build(), new ArrayList<AddonParserProvider>());
    }

    private final Resource resource;
    private final SyntaxConfiguration syntaxConfiguration;
    private final Map<Class, BaseParser> parsers;
    private final Collection<AddonParserProvider> addOnParsers;

    public ParserContext(Resource resource, SyntaxConfiguration syntaxConfiguration, Collection<AddonParserProvider> addOnParsers) {
        this.resource = resource;
        this.syntaxConfiguration = syntaxConfiguration;
        this.parsers = new HashMap<>();
        this.addOnParsers = addOnParsers;
    }
    public ParserContext(Resource resource, SyntaxConfiguration syntaxConfiguration) {
        this.resource = resource;
        this.syntaxConfiguration = syntaxConfiguration;
        this.parsers = new HashMap<>();
        this.addOnParsers = new ArrayList<>();
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
        return syntaxConfiguration;
    }

    public Collection<BaseParser> parsers() {
        return parsers.values();
    }

    public Resource resource() {
        return resource;
    }
}
