package org.jtwig.parser.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheStats;
import com.google.common.collect.ImmutableMap;
import org.jtwig.model.tree.Node;
import org.jtwig.resource.Resource;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;

public class PersistentTemplateCacheProvider implements TemplateCacheProvider {
    @Override
    public Cache<Resource, Node> cache() {
        return new MemoryCache();
    }

    public static class MemoryCache implements Cache<Resource, Node> {
        private final Map<Resource, Node> map = new HashMap<>();

        @Override
        public Node getIfPresent(Object key) {
            return map.get(key);
        }

        @Override
        public Node get(Resource key, Callable<? extends Node> valueLoader) throws ExecutionException {
            try {
                if (!map.containsKey(key)) {
                    map.put(key, valueLoader.call());
                }
                return map.get(key);
            } catch (Exception e) {
                throw new ExecutionException(e);
            }
        }

        @Override
        public ImmutableMap<Resource, Node> getAllPresent(Iterable<?> keys) {
            ImmutableMap.Builder<Resource, Node> builder = ImmutableMap.builder();
            for (Object key : keys) {
                if (map.containsKey(key)) {
                    builder.put((Resource)key, map.get(key));
                }
            }
            return builder.build();
        }

        @Override
        public void put(Resource key, Node value) {
            map.put(key, value);
        }

        @Override
        public void putAll(Map<? extends Resource, ? extends Node> m) {
            map.putAll(m);
        }

        @Override
        public void invalidate(Object key) {
            map.remove(key);
        }

        @Override
        public void invalidateAll(Iterable<?> keys) {
            for (Object key : keys) {
                map.remove(key);
            }
        }

        @Override
        public void invalidateAll() {
            map.clear();
        }

        @Override
        public long size() {
            return map.size();
        }

        @Override
        public CacheStats stats() {
            return null;
        }

        @Override
        public ConcurrentMap<Resource, Node> asMap() {
            return new ConcurrentHashMap<>(map);
        }

        @Override
        public void cleanUp() {
            map.clear();
        }
    }
}
