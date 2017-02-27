package org.jtwig.environment;

import org.jtwig.escape.EscapeEngine;
import org.jtwig.extension.Extension;
import org.jtwig.functions.JtwigFunction;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.test.TestExpression;
import org.jtwig.model.position.Position;
import org.jtwig.model.tree.Node;
import org.jtwig.parser.addon.AddonParserProvider;
import org.jtwig.parser.config.JtwigParserConfiguration;
import org.jtwig.property.selection.cache.SelectionPropertyResolverCache;
import org.jtwig.property.strategy.PropertyResolverStrategy;
import org.jtwig.render.expression.calculator.ExpressionCalculator;
import org.jtwig.render.expression.calculator.enumerated.EnumerationListStrategy;
import org.jtwig.render.expression.calculator.operation.binary.BinaryOperator;
import org.jtwig.render.expression.calculator.operation.binary.calculators.BinaryOperationCalculator;
import org.jtwig.render.expression.calculator.operation.unary.UnaryOperator;
import org.jtwig.render.expression.calculator.operation.unary.calculators.UnaryOperationCalculator;
import org.jtwig.render.expression.test.calculator.TestExpressionCalculator;
import org.jtwig.render.listeners.RenderListener;
import org.jtwig.render.listeners.RenderStage;
import org.jtwig.render.listeners.StagedRenderListener;
import org.jtwig.render.node.renderer.NodeRender;
import org.jtwig.resource.loader.ResourceLoader;
import org.jtwig.resource.loader.TypedResourceLoader;
import org.jtwig.resource.reference.DefaultResourceReferenceExtractor;
import org.jtwig.resource.resolver.RelativeResourceResolver;
import org.jtwig.value.WrappedCollection;
import org.jtwig.value.compare.ValueComparator;
import org.jtwig.value.convert.Converter;
import org.jtwig.value.convert.string.StringConverter;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.nio.charset.Charset;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.jtwig.support.MatcherUtils.theSameBean;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class EnvironmentConfigurationBuilderTest {

    private final UnaryOperator unaryOperator = mock(UnaryOperator.class);
    private final BinaryOperator binaryOperator = mock(BinaryOperator.class);
    private final AddonParserProvider addonParserProvider = mock(AddonParserProvider.class);

    @Test
    public void cloneConstructor() throws Exception {
        EnvironmentConfiguration prototype = new DefaultEnvironmentConfiguration();

        EnvironmentConfiguration result = new EnvironmentConfigurationBuilder(prototype)
                .build();

        assertThat(result, theSameBean(prototype));
    }

    @Test
    public void emptyConstructor() throws Exception {
        EnvironmentConfiguration configuration = new EnvironmentConfigurationBuilder().build();

        assertTrue(configuration.getExtensions().isEmpty());
    }

    @Test
    public void parserConfiguration() throws Exception {
        EnvironmentConfiguration configuration = EnvironmentConfigurationBuilder
                .configuration()
                .parser()
                .syntax()
                .withStartCode("{%").withEndCode("%}")
                .withStartOutput("{{").withEndOutput("}}")
                .withStartComment("{#").withEndComment("#}")
                .and()
                .addonParserProviders().add(customAddonParser()).and()
                .binaryOperators().add(customBinaryOperator()).and()
                .unaryOperators().add(customUnaryOperator()).and()
                .withoutTemplateCache()
                .and()
                .build();

        JtwigParserConfiguration parser = configuration.getJtwigParserConfiguration();
        assertThat(parser.getSyntaxConfiguration().getStartCode(), is("{%"));
        assertThat(parser.getSyntaxConfiguration().getEndCode(), is("%}"));
        assertThat(parser.getSyntaxConfiguration().getStartOutput(), is("{{"));
        assertThat(parser.getSyntaxConfiguration().getEndOutput(), is("}}"));
        assertThat(parser.getSyntaxConfiguration().getStartComment(), is("{#"));
        assertThat(parser.getSyntaxConfiguration().getEndComment(), is("#}"));
        assertThat(parser.getAddonParserProviders(), hasItem(addonParserProvider));
        assertThat(parser.getBinaryOperators(), hasItem(binaryOperator));
        assertThat(parser.getUnaryOperators(), hasItem(unaryOperator));
        assertThat(parser.getTemplateCache().isPresent(), is(false));
    }

    @Test
    public void functions() throws Exception {
        JtwigFunction jtwigFunction = mock(JtwigFunction.class);

        EnvironmentConfiguration configuration = EnvironmentConfigurationBuilder
                .configuration()
                .functions()
                .add(jtwigFunction)
                .and()
                .build();

        assertThat(configuration.getFunctions(), hasItem(jtwigFunction));
    }

    @Test
    public void resources() throws Exception {
        RelativeResourceResolver relativeResourceResolver = mock(RelativeResourceResolver.class);
        ResourceLoader resourceLoader = mock(ResourceLoader.class);
        TypedResourceLoader typedResourceLoader = new TypedResourceLoader("type", resourceLoader);
        DefaultResourceReferenceExtractor extractor = mock(DefaultResourceReferenceExtractor.class);

        EnvironmentConfiguration configuration = EnvironmentConfigurationBuilder
                .configuration()
                    .resources()
                        .resourceLoaders().add(typedResourceLoader).and()
                        .relativeResourceResolvers().add(relativeResourceResolver).and()
                        .absoluteResourceTypes().add("string").and()
                        .withDefaultInputCharset(Charset.forName("UTF-8"))
                        .withResourceReferenceExtractor(extractor)
                    .and()
                .build();

        assertThat(configuration.getResourceConfiguration().getRelativeResourceResolvers(), hasItem(relativeResourceResolver));
    }

    @Test
    public void renderConfig() throws Exception {
        boolean strictMode = false;
        Charset outputCharset = Charset.defaultCharset();
        NodeRender nodeRender = mock(NodeRender.class);
        ExpressionCalculator expCalculator = mock(ExpressionCalculator.class);
        BinaryOperationCalculator binOpCalc = mock(BinaryOperationCalculator.class);
        UnaryOperationCalculator unaryOpCalc = mock(UnaryOperationCalculator.class);
        TestExpressionCalculator testCalc = mock(TestExpressionCalculator.class);

        StagedRenderListener stagedRenderListener = new StagedRenderListener(RenderStage.POST_TEMPLATE_RENDER, mock(RenderListener.class));
        EnvironmentConfiguration result = EnvironmentConfigurationBuilder
                .configuration()
                .render()
                .withStrictMode(strictMode)
                .withOutputCharset(outputCharset)
                .nodeRenders().add(CustomNode.class, nodeRender).and()
                .expressionCalculators()
                .add(CustomExpression.class, expCalculator).and()
                .binaryExpressionCalculators()
                .add(CustomBinaryOperator.class, binOpCalc).and()
                .unaryExpressionCalculators()
                .add(CustomUnaryOperator.class, unaryOpCalc).and()
                .testExpressionCalculators()
                .add(CustomTestExpression.class, testCalc).and()
                .renderListeners()
                    .add(stagedRenderListener)
                .and()
                .and()
                .build();

        assertThat(result.getRenderConfiguration().getDefaultOutputCharset(), is(outputCharset));
        assertThat(result.getRenderConfiguration().getStrictMode(), is(strictMode));
        assertThat(result.getRenderConfiguration().getNodeRenders().get(CustomNode.class), is(nodeRender));
        assertThat(result.getRenderConfiguration().getExpressionCalculators().get(CustomExpression.class), is(expCalculator));
        assertThat(result.getRenderConfiguration().getBinaryExpressionCalculators().get(CustomBinaryOperator.class), is(binOpCalc));
        assertThat(result.getRenderConfiguration().getUnaryExpressionCalculators().get(CustomUnaryOperator.class), is(unaryOpCalc));
        assertThat(result.getRenderConfiguration().getTestExpressionCalculators().get(CustomTestExpression.class), is(testCalc));
        assertThat(result.getRenderConfiguration().getRenderListeners(), hasItem(stagedRenderListener));
    }

    public static class CustomNode extends Node {
        protected CustomNode(Position position) {
            super(position);
        }
    }

    public static class CustomExpression extends Expression {
        protected CustomExpression(Position position) {
            super(position);
        }
    }

    public static class CustomTestExpression extends TestExpression {

    }

    public static class CustomUnaryOperator implements UnaryOperator {
        @Override
        public int precedence() {
            return 0;
        }

        @Override
        public String symbol() {
            return null;
        }
    }

    public static class CustomBinaryOperator implements BinaryOperator {
        @Override
        public String symbol() {
            return null;
        }

        @Override
        public int precedence() {
            return 0;
        }
    }

    @Test
    public void propertyResolverConfiguration() throws Exception {
        SelectionPropertyResolverCache cache = mock(SelectionPropertyResolverCache.class);
        PropertyResolverStrategy propertyResolverStrategy = mock(PropertyResolverStrategy.class);

        EnvironmentConfiguration configuration = EnvironmentConfigurationBuilder
                .configuration()
                .propertyResolver()
                    .withCache(cache)
                    .propertyResolverStrategies()
                        .add(propertyResolverStrategy)
                    .and()
                .and()
                .build();

        assertThat(configuration.getPropertyResolverConfiguration().getCache(), is(cache));
        assertThat(configuration.getPropertyResolverConfiguration().getPropertyResolverStrategies(), hasItem(propertyResolverStrategy));
    }

    @Test
    public void enumerationConfig() throws Exception {
        EnumerationListStrategy enumerationListStrategy = mock(EnumerationListStrategy.class);

        EnvironmentConfiguration configuration = EnvironmentConfigurationBuilder
                .configuration()
                .enumerationStrategies()
                .add(enumerationListStrategy)
                .and()
                .build();

        assertThat(configuration.getEnumerationStrategies(), hasItem(enumerationListStrategy));
    }

    @Test
    public void escapeConfig() throws Exception {
        EscapeEngine customEscapeEngine = mock(EscapeEngine.class);

        EnvironmentConfiguration configuration = EnvironmentConfigurationBuilder
                .configuration()
                .escape()
                .withInitialEngine("none")
                .withDefaultEngine("custom")
                .engines()
                .add("custom", customEscapeEngine)
                .and()
                .and()
                .build();

        assertThat(configuration.getEscapeConfiguration().getDefaultEngine(), is("custom"));
        assertThat(configuration.getEscapeConfiguration().getInitialEngine(), is("none"));
        assertTrue(configuration.getEscapeConfiguration().getEscapeEngineMap().containsKey("custom"));
        assertTrue(configuration.getEscapeConfiguration().getEscapeEngineMap().containsKey("none"));
        assertTrue(configuration.getEscapeConfiguration().getEscapeEngineMap().containsKey("html"));
        assertTrue(configuration.getEscapeConfiguration().getEscapeEngineMap().containsKey("js"));
        assertTrue(configuration.getEscapeConfiguration().getEscapeEngineMap().containsKey("javascript"));
    }

    @Test
    public void valueConfig() throws Exception {
        ValueComparator valueComparator = mock(ValueComparator.class);
        StringConverter stringConverter = mock(StringConverter.class);
        Converter<BigDecimal> numberConverter = mock(Converter.class);
        Converter<Boolean> booleanConverter = mock(Converter.class);
        Converter<Character> charConverter = mock(Converter.class);
        Converter<WrappedCollection> collectionConverter = mock(Converter.class);
        MathContext mathContext = MathContext.DECIMAL128;
        RoundingMode roundingMode = RoundingMode.HALF_UP;

        EnvironmentConfiguration configuration = EnvironmentConfigurationBuilder
                .configuration()
                .value()
                .withMathContext(mathContext)
                .withRoundingMode(roundingMode)
                .withValueComparator(valueComparator)
                .withStringConverter(stringConverter)
                .numberConverters().add(numberConverter).and()
                .booleanConverters().add(booleanConverter).and()
                .charConverters().add(charConverter).and()
                .collectionConverters().add(collectionConverter).and()
                .and()
                .build();

        assertSame(configuration.getValueConfiguration().getMathContext(), mathContext);
        assertSame(configuration.getValueConfiguration().getRoundingMode(), roundingMode);
        assertSame(configuration.getValueConfiguration().getStringConverter(), stringConverter);
        assertSame(configuration.getValueConfiguration().getValueComparator(), valueComparator);
        assertThat(configuration.getValueConfiguration().getBooleanConverters(), hasItem(booleanConverter));
        assertThat(configuration.getValueConfiguration().getNumberConverters(), hasItem(numberConverter));
        assertThat(configuration.getValueConfiguration().getCharConverters(), hasItem(charConverter));
        assertThat(configuration.getValueConfiguration().getCollectionConverters(), hasItem(collectionConverter));
    }

    @Test
    public void extensionsConfig() throws Exception {
        CustomExtension customExtension1 = new CustomExtension();
        CustomExtension customExtension2 = new CustomExtension();

        EnvironmentConfiguration configuration = EnvironmentConfigurationBuilder
                .configuration()
                .extensions()
                .add(customExtension1)
                .add(customExtension2)
                .and()
                .build();


        assertThat(configuration.getExtensions(), hasItem(customExtension1));
        assertThat(configuration.getExtensions(), hasItem(customExtension2));
    }

    @Test
    public void parametersConfig() throws Exception {
        Object value1 = new Object();
        Object value2 = new Object();
        String parameter1 = "parameter1";
        String parameter2 = "parameter2";

        EnvironmentConfiguration configuration = EnvironmentConfigurationBuilder
                .configuration()
                .parameters()
                .add(parameter1, value1)
                .add(parameter2, value2)
                .and()
                .build();

        assertSame(configuration.getParameters().get(parameter1), value1);
        assertSame(configuration.getParameters().get(parameter2), value2);
    }

    public static class CustomExtension implements Extension {
        @Override
        public void configure(EnvironmentConfigurationBuilder configurationBuilder) {

        }
    }

    private UnaryOperator customUnaryOperator() {
        return unaryOperator;
    }

    private BinaryOperator customBinaryOperator() {
        return binaryOperator;
    }

    private AddonParserProvider customAddonParser() {
        return addonParserProvider;
    }
}