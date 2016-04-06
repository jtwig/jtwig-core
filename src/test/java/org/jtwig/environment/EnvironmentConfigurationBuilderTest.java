package org.jtwig.environment;

import org.jtwig.functions.JtwigFunction;
import org.jtwig.parser.addon.AddonParserProvider;
import org.jtwig.parser.config.JtwigParserConfiguration;
import org.jtwig.property.PropertyResolver;
import org.jtwig.render.context.model.EscapeMode;
import org.jtwig.render.expression.calculator.enumerated.EnumerationListStrategy;
import org.jtwig.render.expression.calculator.operation.binary.BinaryOperator;
import org.jtwig.render.expression.calculator.operation.unary.UnaryOperator;
import org.jtwig.resource.resolver.ResourceResolver;
import org.junit.Test;

import java.nio.charset.Charset;

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
        EnvironmentConfiguration result = new EnvironmentConfigurationBuilder().parser()
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
        assertThat(parser.getTemplateCache().isPresent(), is(false));
    }

    @Test
    public void functions() throws Exception {
        JtwigFunction jtwigFunction = mock(JtwigFunction.class);

        EnvironmentConfiguration result = new EnvironmentConfigurationBuilder().functions()
                .add(jtwigFunction)
                .and()
                .build();

        assertThat(result.getFunctions(), hasItem(jtwigFunction));
    }

    @Test
    public void resources() throws Exception {
        ResourceResolver resourceResolver = mock(ResourceResolver.class);

        EnvironmentConfiguration result = new EnvironmentConfigurationBuilder().resources()
                .resourceResolvers().add(resourceResolver).and()
                .and()
                .build();

        assertThat(result.getResourceConfiguration().getResourceResolvers(), hasItem(resourceResolver));
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
        assertThat(result.getRenderConfiguration().getDefaultOutputCharset(), is(outputCharset));
        assertThat(result.getRenderConfiguration().getStrictMode(), is(strictMode));
    }

    @Test
    public void propertyResolverConfiguration() throws Exception {
        PropertyResolver propertyResolver = mock(PropertyResolver.class);

        EnvironmentConfiguration result = new EnvironmentConfigurationBuilder()
                .propertyResolver()
                    .add(propertyResolver)
                .and().build();

        assertThat(result.getPropertyResolvers(), hasItem(propertyResolver));
    }

    @Test
    public void enumerationConfig() throws Exception {
        EnumerationListStrategy enumerationListStrategy = mock(EnumerationListStrategy.class);

        EnvironmentConfiguration result = new EnvironmentConfigurationBuilder()
                .enumerationStrategies()
                    .add(enumerationListStrategy)
                .and().build();

        assertThat(result.getEnumerationStrategies(), hasItem(enumerationListStrategy));
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