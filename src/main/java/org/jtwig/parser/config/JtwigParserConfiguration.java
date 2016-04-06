package org.jtwig.parser.config;

import com.google.common.base.Optional;
import org.jtwig.parser.addon.AddonParserProvider;
import org.jtwig.parser.cache.TemplateCache;
import org.jtwig.render.expression.calculator.operation.binary.BinaryOperator;
import org.jtwig.render.expression.calculator.operation.unary.UnaryOperator;

import java.util.Collection;

public class JtwigParserConfiguration {
    private final SyntaxConfiguration syntaxConfiguration;
    private final Collection<AddonParserProvider> addonParserProviders;
    private final Collection<UnaryOperator> unaryOperators;
    private final Collection<BinaryOperator> binaryOperators;
    private final Optional<TemplateCache> templateCache;

    public JtwigParserConfiguration(SyntaxConfiguration syntaxConfiguration, Collection<AddonParserProvider> addonParserProviders, Collection<UnaryOperator> unaryOperators, Collection<BinaryOperator> binaryOperators, Optional<TemplateCache> templateCache) {
        this.syntaxConfiguration = syntaxConfiguration;
        this.addonParserProviders = addonParserProviders;
        this.unaryOperators = unaryOperators;
        this.binaryOperators = binaryOperators;
        this.templateCache = templateCache;
    }

    public SyntaxConfiguration getSyntaxConfiguration() {
        return syntaxConfiguration;
    }

    public Collection<AddonParserProvider> getAddonParserProviders() {
        return addonParserProviders;
    }

    public Optional<TemplateCache> getTemplateCache() {
        return templateCache;
    }

    public Collection<UnaryOperator> getUnaryOperators() {
        return unaryOperators;
    }

    public Collection<BinaryOperator> getBinaryOperators() {
        return binaryOperators;
    }
}
