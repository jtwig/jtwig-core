package org.jtwig.parser;

import org.jtwig.model.expression.operation.binary.BinaryOperator;
import org.jtwig.model.expression.operation.unary.UnaryOperator;
import org.jtwig.parser.addon.AddonParserProvider;
import org.jtwig.parser.cache.CacheProvider;
import org.jtwig.parser.config.SyntaxConfiguration;

import java.nio.charset.Charset;
import java.util.Collection;

public class JtwigParserConfiguration {
    private final SyntaxConfiguration syntaxConfiguration;
    private final Collection<AddonParserProvider> addonParserProviders;
    private final Collection<UnaryOperator> unaryOperators;
    private final Collection<BinaryOperator> binaryOperators;
    private final Charset inputCharset;
    private final CacheProvider cacheProvider;

    public JtwigParserConfiguration(SyntaxConfiguration syntaxConfiguration, Collection<AddonParserProvider> addonParserProviders, Collection<UnaryOperator> unaryOperators, Collection<BinaryOperator> binaryOperators, Charset inputCharset, CacheProvider cacheProvider) {
        this.syntaxConfiguration = syntaxConfiguration;
        this.addonParserProviders = addonParserProviders;
        this.unaryOperators = unaryOperators;
        this.binaryOperators = binaryOperators;
        this.inputCharset = inputCharset;
        this.cacheProvider = cacheProvider;
    }

    public SyntaxConfiguration getSyntaxConfiguration() {
        return syntaxConfiguration;
    }

    public Collection<AddonParserProvider> getAddonParserProviders() {
        return addonParserProviders;
    }

    public Charset getInputCharset() {
        return inputCharset;
    }

    public CacheProvider getCacheProvider() {
        return cacheProvider;
    }

    public Collection<UnaryOperator> getUnaryOperators() {
        return unaryOperators;
    }

    public Collection<BinaryOperator> getBinaryOperators() {
        return binaryOperators;
    }
}
