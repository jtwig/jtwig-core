package org.jtwig.parser;

import org.jtwig.environment.Environment;
import org.jtwig.model.tree.Node;
import org.jtwig.parser.cache.TemplateCache;
import org.jtwig.resource.reference.ResourceReference;

public class CachedJtwigParser implements JtwigParser {
    private final TemplateCache cache;
    private final JtwigParser jtwigParser;

    public CachedJtwigParser(TemplateCache cache, JtwigParser jtwigParser) {
        this.cache = cache;
        this.jtwigParser = jtwigParser;
    }

    @Override
    public Node parse(Environment environment, ResourceReference resource) {
        return cache.get(jtwigParser, environment, resource);
    }
}
