package org.jtwig.parser;

import org.jtwig.parser.addon.AddonParserProvider;
import org.jtwig.parser.cache.NoCacheProvider;
import org.jtwig.parser.config.SyntaxConfigurationBuilder;

import java.nio.charset.Charset;
import java.util.Collections;

public class DefaultJtwigParserConfiguration extends JtwigParserConfiguration {

    public DefaultJtwigParserConfiguration() {
        super(SyntaxConfigurationBuilder.syntaxConfiguration().build(), Collections.<AddonParserProvider>emptyList(), Charset.defaultCharset(), new NoCacheProvider());
    }
}
