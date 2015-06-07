package org.jtwig.parser;

import org.apache.commons.lang3.builder.Builder;
import org.jtwig.model.expression.operation.binary.BinaryOperator;
import org.jtwig.model.expression.operation.unary.UnaryOperator;
import org.jtwig.parser.addon.AddonParserProvider;
import org.jtwig.parser.cache.CacheProvider;
import org.jtwig.parser.cache.NoCacheProvider;
import org.jtwig.parser.config.SyntaxConfiguration;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;

public class JtwigParserConfigurationBuilder<B extends JtwigParserConfigurationBuilder> implements Builder<JtwigParserConfiguration> {
    private SyntaxConfiguration syntaxConfiguration;
    private Charset inputCharset;
    private Collection<AddonParserProvider> addonParserProviders = new ArrayList<>();
    private Collection<UnaryOperator> unaryOperators = new ArrayList<>();
    private Collection<BinaryOperator> binaryOperators = new ArrayList<>();
    private CacheProvider cacheProvider = new NoCacheProvider();

    public JtwigParserConfigurationBuilder () {}
    public JtwigParserConfigurationBuilder (JtwigParserConfiguration prototype) {
        this
                .withAddonParserProviders(prototype.getAddonParserProviders())
                .withInputCharset(prototype.getInputCharset())
                .withSyntaxConfiguration(prototype.getSyntaxConfiguration())
                .withCacheProvider(prototype.getCacheProvider())
                .withBinaryOperators(prototype.getBinaryOperators())
                .withUnaryOperators(prototype.getUnaryOperators())
        ;
    }

    public B withSyntaxConfiguration(SyntaxConfiguration syntaxConfiguration) {
        this.syntaxConfiguration = syntaxConfiguration;
        return self();
    }

    public B withInputCharset(Charset inputCharset) {
        this.inputCharset = inputCharset;
        return self();
    }

    public B withAddonParserProviders(Collection<AddonParserProvider> addonParserProviders) {
        this.addonParserProviders.addAll(addonParserProviders);
        return self();
    }

    public B withAddOnParser(AddonParserProvider addonParserProvider) {
        this.addonParserProviders.add(addonParserProvider);
        return self();
    }

    public B withCacheProvider(CacheProvider cacheProvider) {
        this.cacheProvider = cacheProvider;
        return self();
    }

    public B withAddonParserProvider(AddonParserProvider parser) {
        this.addonParserProviders.add(parser);
        return self();
    }

    public B withUnaryOperator(UnaryOperator unaryOperator) {
        this.unaryOperators.add(unaryOperator);
        return self();
    }

    public B withBinaryOperator(BinaryOperator binaryOperator) {
        this.binaryOperators.add(binaryOperator);
        return self();
    }

    public B withUnaryOperators(Collection<UnaryOperator> unaryOperator) {
        this.unaryOperators.addAll(unaryOperator);
        return self();
    }

    public B withBinaryOperators(Collection<BinaryOperator> binaryOperator) {
        this.binaryOperators.addAll(binaryOperator);
        return self();
    }

    protected B self () {
        return (B) this;
    }

    @Override
    public JtwigParserConfiguration build() {
        return new JtwigParserConfiguration(syntaxConfiguration, addonParserProviders, unaryOperators, binaryOperators, inputCharset, cacheProvider);
    }
}
