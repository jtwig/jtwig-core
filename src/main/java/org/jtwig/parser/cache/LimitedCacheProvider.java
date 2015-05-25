package org.jtwig.parser.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.jtwig.model.tree.Node;
import org.jtwig.resource.Resource;

public class LimitedCacheProvider implements CacheProvider {
    private final long cacheSize;

    public LimitedCacheProvider(long cacheSize) {
        this.cacheSize = cacheSize;
    }

    @Override
    public Cache<Resource, Node> cache() {
        return CacheBuilder.newBuilder()
                .maximumSize(cacheSize)
                .build();
    }
}
