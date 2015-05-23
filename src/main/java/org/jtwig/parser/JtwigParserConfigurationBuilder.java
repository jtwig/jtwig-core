package org.jtwig.parser;

import org.apache.commons.lang3.builder.Builder;
import org.jtwig.parser.addon.AddonParserProvider;
import org.jtwig.parser.config.SyntaxConfiguration;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;

public class JtwigParserConfigurationBuilder implements Builder<JtwigParserConfiguration> {
    private SyntaxConfiguration syntaxConfiguration;
    private Charset inputCharset;
    private Collection<AddonParserProvider> addonParserProviders = new ArrayList<>();

    public JtwigParserConfigurationBuilder () {}
    public JtwigParserConfigurationBuilder (JtwigParserConfiguration prototype) {
        this
                .withAddonParserProviders(prototype.getAddonParserProviders())
                .withInputCharset(prototype.getInputCharset())
                .withSyntaxConfiguration(prototype.getSyntaxConfiguration())
        ;
    }

    public JtwigParserConfigurationBuilder withSyntaxConfiguration(SyntaxConfiguration syntaxConfiguration) {
        this.syntaxConfiguration = syntaxConfiguration;
        return this;
    }

    public JtwigParserConfigurationBuilder withInputCharset(Charset inputCharset) {
        this.inputCharset = inputCharset;
        return this;
    }

    public JtwigParserConfigurationBuilder withAddonParserProviders(Collection<AddonParserProvider> addonParserProviders) {
        this.addonParserProviders.addAll(addonParserProviders);
        return this;
    }

    @Override
    public JtwigParserConfiguration build() {
        return new JtwigParserConfiguration(syntaxConfiguration, addonParserProviders, inputCharset);
    }
}
