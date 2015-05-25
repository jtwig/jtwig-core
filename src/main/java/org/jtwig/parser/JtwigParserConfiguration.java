package org.jtwig.parser;

import org.jtwig.parser.addon.AddonParserProvider;
import org.jtwig.parser.cache.CacheProvider;
import org.jtwig.parser.config.SyntaxConfiguration;

import java.nio.charset.Charset;
import java.util.Collection;

public class JtwigParserConfiguration {
    private final SyntaxConfiguration syntaxConfiguration;
    private final Collection<AddonParserProvider> addonParserProviders;
    private final Charset inputCharset;
    private final CacheProvider cacheProvider;

    public JtwigParserConfiguration(SyntaxConfiguration syntaxConfiguration, Collection<AddonParserProvider> addonParserProviders, Charset inputCharset, CacheProvider cacheProvider) {
        this.syntaxConfiguration = syntaxConfiguration;
        this.addonParserProviders = addonParserProviders;
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
}
