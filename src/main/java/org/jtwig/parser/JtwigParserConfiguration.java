package org.jtwig.parser;

import org.jtwig.model.expression.operation.binary.BinaryOperator;
import org.jtwig.model.expression.operation.unary.UnaryOperator;
import org.jtwig.parser.addon.AddonParserProvider;
import org.jtwig.parser.cache.TemplateCacheProvider;
import org.jtwig.parser.config.SyntaxConfiguration;

import java.util.Collection;

public class JtwigParserConfiguration {
    private final SyntaxConfiguration syntaxConfiguration;
    private final Collection<AddonParserProvider> addonParserProviders;
    private final Collection<UnaryOperator> unaryOperators;
    private final Collection<BinaryOperator> binaryOperators;
    private final TemplateCacheProvider templateCacheProvider;

    public JtwigParserConfiguration(SyntaxConfiguration syntaxConfiguration, Collection<AddonParserProvider> addonParserProviders, Collection<UnaryOperator> unaryOperators, Collection<BinaryOperator> binaryOperators, TemplateCacheProvider templateCacheProvider) {
        this.syntaxConfiguration = syntaxConfiguration;
        this.addonParserProviders = addonParserProviders;
        this.unaryOperators = unaryOperators;
        this.binaryOperators = binaryOperators;
        this.templateCacheProvider = templateCacheProvider;
    }

    public SyntaxConfiguration getSyntaxConfiguration() {
        return syntaxConfiguration;
    }

    public Collection<AddonParserProvider> getAddonParserProviders() {
        return addonParserProviders;
    }

    public TemplateCacheProvider getTemplateCacheProvider() {
        return templateCacheProvider;
    }

    public Collection<UnaryOperator> getUnaryOperators() {
        return unaryOperators;
    }

    public Collection<BinaryOperator> getBinaryOperators() {
        return binaryOperators;
    }
}
