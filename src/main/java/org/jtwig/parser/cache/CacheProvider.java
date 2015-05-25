package org.jtwig.parser.cache;

import com.google.common.cache.Cache;
import org.jtwig.model.tree.Node;
import org.jtwig.resource.Resource;

public interface CacheProvider {
    public Cache<Resource, Node> cache ();
}
