package org.jtwig.environment;

import org.jtwig.context.model.EscapeMode;
import org.jtwig.functions.JtwigFunction;
import org.jtwig.model.expression.lists.EnumerationListStrategy;
import org.jtwig.model.expression.operation.binary.BinaryOperator;
import org.jtwig.model.expression.operation.unary.UnaryOperator;
import org.jtwig.parser.JtwigParserConfiguration;
import org.jtwig.parser.addon.AddonParserProvider;
import org.jtwig.parser.cache.NoTemplateCacheProvider;
import org.jtwig.parser.cache.TemplateCacheProvider;
import org.jtwig.property.PropertyResolver;
import org.jtwig.resource.resolver.ResourceResolver;
import org.jtwig.value.JtwigType;
import org.jtwig.value.converter.Converter;
import org.jtwig.value.extract.Extractor;
import org.jtwig.value.extract.map.selection.MapSelectionExtractor;
import org.jtwig.value.relational.RelationalComparator;
import org.junit.Test;

import java.math.MathContext;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Collections;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class EnvironmentConfigurationBuilderTest {

    private final UnaryOperator unaryOperator = mock(UnaryOperator.class);
    private final BinaryOperator binaryOperator = mock(BinaryOperator.class);
    private final AddonParserProvider addonParserProvider = mock(AddonParserProvider.class);

    @Test
    public void parserConfiguration() throws Exception {
        TemplateCacheProvider templateCacheProvider = new NoTemplateCacheProvider();

        EnvironmentConfiguration result = new EnvironmentConfigurationBuilder().parser()
                .syntax()
                .withStartCode("{%").withEndCode("%}")
                .withStartOutput("{{").withEndOutput("}}")
                .withStartComment("{#").withEndComment("#}")
                .and()
                .withAddonParserProvider(customAddonParser())
                .withBinaryOperator(customBinaryOperator())
                .withUnaryOperator(customUnaryOperator())
                .withCacheProvider(templateCacheProvider)
                .and()
                .build();

        JtwigParserConfiguration parser = result.getJtwigParserConfiguration();
        assertThat(parser.getSyntaxConfiguration().getStartCode(), is("{%"));
        assertThat(parser.getSyntaxConfiguration().getEndCode(), is("%}"));
        assertThat(parser.getSyntaxConfiguration().getStartOutput(), is("{{"));
        assertThat(parser.getSyntaxConfiguration().getEndOutput(), is("}}"));
        assertThat(parser.getSyntaxConfiguration().getStartComment(), is("{#"));
        assertThat(parser.getSyntaxConfiguration().getEndComment(), is("#}"));
        assertThat(parser.getAddonParserProviders(), hasItem(addonParserProvider));
        assertThat(parser.getBinaryOperators(), hasItem(binaryOperator));
        assertThat(parser.getUnaryOperators(), hasItem(unaryOperator));
        assertThat(parser.getTemplateCacheProvider(), is(templateCacheProvider));
    }

    @Test
    public void functions() throws Exception {
        JtwigFunction jtwigFunction = mock(JtwigFunction.class);

        EnvironmentConfiguration result = new EnvironmentConfigurationBuilder().functions()
                .withFunction(jtwigFunction)
                .and()
                .build();

        assertThat(result.getFunctionResolverConfiguration().getFunctions(), hasItem(jtwigFunction));
    }

    @Test
    public void resources() throws Exception {
        ResourceResolver resourceResolver = mock(ResourceResolver.class);

        EnvironmentConfiguration result = new EnvironmentConfigurationBuilder().resources()
                .withResourceResolver(resourceResolver)
                .and()
                .build();

        assertThat(result.getResourceResolverConfiguration().getResourceResolvers(), hasItem(resourceResolver));
    }

    @Test
    public void renderConfig() throws Exception {
        EscapeMode initialEscapeMode = EscapeMode.NONE;
        boolean strictMode = false;
        Charset outputCharset = Charset.defaultCharset();

        EnvironmentConfiguration result = new EnvironmentConfigurationBuilder()
                .render()
                    .withStrictMode(strictMode)
                    .withInitialEscapeMode(initialEscapeMode)
                    .withOutputCharset(outputCharset)
                .and().build();

        assertThat(result.getRenderConfiguration().getInitialEscapeMode(), is(initialEscapeMode));
        assertThat(result.getRenderConfiguration().getOutputCharset(), is(outputCharset));
        assertThat(result.getRenderConfiguration().getStrictMode(), is(strictMode));
    }

    @Test
    public void propertyResolverConfiguration() throws Exception {
        PropertyResolver propertyResolver = mock(PropertyResolver.class);

        EnvironmentConfiguration result = new EnvironmentConfigurationBuilder()
                .propertyResolver()
                    .withPropertyResolver(propertyResolver)
                .and().build();

        assertThat(result.getPropertyResolverConfiguration().getPropertyResolvers(), hasItem(propertyResolver));
    }

    @Test
    public void enumerationConfig() throws Exception {
        EnumerationListStrategy enumerationListStrategy = mock(EnumerationListStrategy.class);

        EnvironmentConfiguration result = new EnvironmentConfigurationBuilder()
                .listEnumeration()
                    .withEnumerationListStrategy(enumerationListStrategy)
                .and().build();

        assertThat(result.getEnumerationListConfiguration().getStrategies(), hasItem(enumerationListStrategy));
    }

    @Test
    public void valueConfig() throws Exception {
        Collection<Converter> converters = Collections.emptyList();

        RelationalComparator identicalComparator = mock(RelationalComparator.class);
        RelationalComparator equalComparator = mock(RelationalComparator.class);
        RelationalComparator lowerComparator = mock(RelationalComparator.class);
        RelationalComparator greaterComparator = mock(RelationalComparator.class);
        MapSelectionExtractor mapSelectionExtractor = mock(MapSelectionExtractor.class);
        Extractor<JtwigType> typeExtractor = mock(Extractor.class);

        EnvironmentConfiguration result = new EnvironmentConfigurationBuilder()
                .value()
                    .withMathContext(MathContext.DECIMAL128)
                    .withConverters(converters)
                    .withEqualComparator(equalComparator)
                    .withLowerComparator(lowerComparator)
                    .withGreaterComparator(greaterComparator)
                    .withIdenticalComparator(identicalComparator)
                    .withMapSelectionExtractor(mapSelectionExtractor)
                    .withTypeExtractor(typeExtractor)
                .and().build();

        assertThat(result.getValueConfiguration().getMathContext(), is(MathContext.DECIMAL128));
        assertThat(result.getValueConfiguration().getConverter(), is(converters));
        assertThat(result.getValueConfiguration().getEqualComparator(), is(equalComparator));
        assertThat(result.getValueConfiguration().getGreaterComparator(), is(greaterComparator));
        assertThat(result.getValueConfiguration().getLowerComparator(), is(lowerComparator));
        assertThat(result.getValueConfiguration().getMapSelectionExtractor(), is(mapSelectionExtractor));
        assertThat(result.getValueConfiguration().getIdenticalComparator(), is(identicalComparator));
        assertThat(result.getValueConfiguration().getTypeExtractor(), is(typeExtractor));

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