package org.jtwig.parser;

import com.google.common.base.Optional;
import org.apache.commons.lang3.builder.Builder;
import org.jtwig.parser.addon.AddonParserProvider;
import org.jtwig.parser.cache.TemplateCache;
import org.jtwig.parser.config.AndSyntaxConfigurationBuilder;
import org.jtwig.render.expression.calculator.operation.binary.BinaryOperator;
import org.jtwig.render.expression.calculator.operation.unary.UnaryOperator;

import java.util.ArrayList;
import java.util.Collection;

public class JtwigParserConfigurationBuilder<B extends JtwigParserConfigurationBuilder> implements Builder<JtwigParserConfiguration> {
    private AndSyntaxConfigurationBuilder<B> syntaxConfiguration;
    private Collection<AddonParserProvider> addonParserProviders = new ArrayList<>();
    private Collection<UnaryOperator> unaryOperators = new ArrayList<>();
    private Collection<BinaryOperator> binaryOperators = new ArrayList<>();
    private Optional<TemplateCache> templateCache;

    public JtwigParserConfigurationBuilder () {
        this.syntaxConfiguration  = new AndSyntaxConfigurationBuilder<>((B) this);
    }
    public JtwigParserConfigurationBuilder (JtwigParserConfiguration prototype) {
        this.syntaxConfiguration  = new AndSyntaxConfigurationBuilder<>((B) this, prototype.getSyntaxConfiguration());
        this
                .withAddonParserProviders(prototype.getAddonParserProviders())
                .withTemplateCache(prototype.getTemplateCache().orNull())
                .withBinaryOperators(prototype.getBinaryOperators())
                .withUnaryOperators(prototype.getUnaryOperators())
        ;
    }

    public AndSyntaxConfigurationBuilder<B> syntax () {
        return syntaxConfiguration;
    }

    public B withAddonParserProviders(Collection<AddonParserProvider> addonParserProviders) {
        this.addonParserProviders.addAll(addonParserProviders);
        return self();
    }

    public B withTemplateCache(TemplateCache templateCache) {
        this.templateCache = Optional.fromNullable(templateCache);
        return self();
    }
    public B withoutTemplateCache() {
        this.templateCache = Optional.absent();
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
        return new JtwigParserConfiguration(syntaxConfiguration.build(), addonParserProviders, unaryOperators, binaryOperators, templateCache);
    }
}
