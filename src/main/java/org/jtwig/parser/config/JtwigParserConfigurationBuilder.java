package org.jtwig.parser.config;

import com.google.common.base.Optional;
import org.apache.commons.lang3.builder.Builder;
import org.jtwig.parser.addon.AddonParserProvider;
import org.jtwig.parser.cache.TemplateCache;
import org.jtwig.render.expression.calculator.operation.binary.BinaryOperator;
import org.jtwig.render.expression.calculator.operation.unary.UnaryOperator;
import org.jtwig.util.builder.ListBuilder;

public class JtwigParserConfigurationBuilder<B extends JtwigParserConfigurationBuilder> implements Builder<JtwigParserConfiguration> {
    private AndSyntaxConfigurationBuilder<B> syntaxConfiguration;
    private final ListBuilder<B, AddonParserProvider> addonParserProviders;
    private final ListBuilder<B, UnaryOperator> unaryOperators;
    private final ListBuilder<B, BinaryOperator> binaryOperators;
    private Optional<TemplateCache> templateCache;

    public JtwigParserConfigurationBuilder () {
        this.syntaxConfiguration  = new AndSyntaxConfigurationBuilder<>(self());
        this.addonParserProviders = new ListBuilder<>(self());
        this.unaryOperators = new ListBuilder<>(self());
        this.binaryOperators = new ListBuilder<>(self());
    }
    public JtwigParserConfigurationBuilder (JtwigParserConfiguration prototype) {
        this.syntaxConfiguration  = new AndSyntaxConfigurationBuilder<>(self(), prototype.getSyntaxConfiguration());
        this.addonParserProviders = new ListBuilder<>(self(), prototype.getAddonParserProviders());
        this.unaryOperators = new ListBuilder<>(self(), prototype.getUnaryOperators());
        this.binaryOperators = new ListBuilder<>(self(), prototype.getBinaryOperators());
        this.templateCache = prototype.getTemplateCache();
    }

    public AndSyntaxConfigurationBuilder<B> syntax () {
        return syntaxConfiguration;
    }



    public B withTemplateCache(TemplateCache templateCache) {
        this.templateCache = Optional.fromNullable(templateCache);
        return self();
    }
    public B withoutTemplateCache() {
        this.templateCache = Optional.absent();
        return self();
    }

    public ListBuilder<B, AddonParserProvider> addonParserProviders() {
        return addonParserProviders;
    }

    public ListBuilder<B, UnaryOperator> unaryOperators() {
        return unaryOperators;
    }

    public ListBuilder<B, BinaryOperator> binaryOperators() {
        return binaryOperators;
    }

    protected B self () {
        return (B) this;
    }

    @Override
    public JtwigParserConfiguration build() {
        return new JtwigParserConfiguration(syntaxConfiguration.build(), addonParserProviders.build(), unaryOperators.build(), binaryOperators.build(), templateCache);
    }
}
