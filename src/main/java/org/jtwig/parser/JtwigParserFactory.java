package org.jtwig.parser;

import com.google.common.base.Optional;
import org.jtwig.parser.cache.TemplateCache;
import org.jtwig.parser.config.JtwigParserConfiguration;
import org.jtwig.parser.parboiled.ParboiledJtwigParser;

public class JtwigParserFactory {
    public JtwigParser create (JtwigParserConfiguration configuration) {
        ParboiledJtwigParser parboiledJtwigParser = new ParboiledJtwigParser(configuration);
        Optional<TemplateCache> templateCache = configuration.getTemplateCache();
        if (templateCache.isPresent()) {
            return new CachedJtwigParser(templateCache.get(), parboiledJtwigParser);
        } else {
            return parboiledJtwigParser;
        }
    }

}
