package org.jtwig.parser;

import com.google.common.cache.Cache;
import org.jtwig.model.tree.Node;
import org.jtwig.resource.Resource;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class CachedJtwigParser implements JtwigParser {
    private final Cache<Resource, Node> cache;
    private final JtwigParser delegate;

    public CachedJtwigParser(Cache<Resource, Node> cache, JtwigParser delegate) {
        this.cache = cache;
        this.delegate = delegate;
    }

    @Override
    public Node parse(final Resource resource) {
        try {
            return cache.get(resource, valueLoader(resource));
        } catch (ExecutionException e) {
            throw new ParseException(String.format("Operation halted while parsing resource '%s'", resource), e);
        }
    }

    private Callable<Node> valueLoader(final Resource resource) {
        return new Callable<Node>() {
            @Override
            public Node call() throws Exception {
                return delegate.parse(resource);
            }
        };
    }
}
