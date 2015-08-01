package org.jtwig.parser.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheStats;
import com.google.common.collect.ImmutableMap;
import org.jtwig.model.tree.Node;
import org.jtwig.resource.Resource;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;

public class NoTemplateCacheProvider implements TemplateCacheProvider {
    @Override
    public Cache<Resource, Node> cache() {
        return new NoCache();
    }

    public static class NoCache implements Cache<Resource, Node> {

        @Override
        public Node getIfPresent(Object key) {
            return null;
        }

        @Override
        public Node get(Resource key, Callable<? extends Node> valueLoader) throws ExecutionException {
            try {
                return valueLoader.call();
            } catch (Exception e) {
                throw new ExecutionException(e);
            }
        }

        @Override
        public ImmutableMap<Resource, Node> getAllPresent(Iterable<?> keys) {
            return ImmutableMap.of();
        }

        @Override
        public void put(Resource key, Node value) {

        }

        @Override
        public void putAll(Map<? extends Resource, ? extends Node> m) {

        }

        @Override
        public void invalidate(Object key) {

        }

        @Override
        public void invalidateAll(Iterable<?> keys) {

        }

        @Override
        public void invalidateAll() {

        }

        @Override
        public long size() {
            return 0;
        }

        @Override
        public CacheStats stats() {
            return null;
        }

        @Override
        public ConcurrentMap<Resource, Node> asMap() {
            return null;
        }

        @Override
        public void cleanUp() {

        }
    }
}
