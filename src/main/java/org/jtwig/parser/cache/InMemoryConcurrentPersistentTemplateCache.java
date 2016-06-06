package org.jtwig.parser.cache;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.util.concurrent.SettableFuture;
import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap;
import org.jtwig.environment.Environment;
import org.jtwig.model.tree.Node;
import org.jtwig.parser.JtwigParser;
import org.jtwig.resource.reference.ResourceReference;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Future;

public class InMemoryConcurrentPersistentTemplateCache implements TemplateCache {
    private final ConcurrentMap<ResourceReference, Future<Result>> cache;
    private final Function<Future<Result>, Result> retriever = new RetrieveFuture<>();

    public InMemoryConcurrentPersistentTemplateCache () {
        this(101, Integer.MAX_VALUE);
    }
    public InMemoryConcurrentPersistentTemplateCache(int initialCapacity, int maxValue) {
        this.cache = new ConcurrentLinkedHashMap.Builder<ResourceReference, Future<Result>>()
                .initialCapacity(initialCapacity)
                .maximumWeightedCapacity(maxValue)
                .build();
    }

    @Override
    public Node get(JtwigParser parser, Environment environment, ResourceReference resource) {
        Optional<Future<Result>> optional = Optional.fromNullable(cache.get(resource));
        if (optional.isPresent()) {
            return retriever.apply(optional.get()).get();
        } else {
            SettableFuture<Result> future = SettableFuture.create();
            Future<Result> result = cache.putIfAbsent(resource, future);
            if (result == null) {
                try {
                    Node node = parser.parse(environment, resource);
                    future.set(new Result(Optional.of(node), Optional.<RuntimeException>absent()));
                    return node;
                } catch (RuntimeException e) {
                    cache.remove(resource);
                    future.set(new Result(Optional.<Node>absent(), Optional.of(e)));
                    throw e;
                }
            } else {
                return retriever.apply(result).get();
            }
        }
    }

    public static class Result {
        private final Optional<Node> node;
        private final Optional<RuntimeException> exception;


        public Result(Optional<Node> node, Optional<RuntimeException> exception) {
            this.node = node;
            this.exception = exception;
        }

        public Node get () {
            if (!node.isPresent()) {
                throw exception.get();
            } else {
                return node.get();
            }
        }
    }
}
