package org.jtwig.parser.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.jtwig.model.tree.Node;
import org.jtwig.resource.Resource;

public class LimitedTemplateCacheProvider implements TemplateCacheProvider {
    private final long cacheSize;

    public LimitedTemplateCacheProvider(long cacheSize) {
        this.cacheSize = cacheSize;
    }

    @Override
    public Cache<Resource, Node> cache() {
        return CacheBuilder.newBuilder()
                .maximumSize(cacheSize)
                .build();
    }
}
