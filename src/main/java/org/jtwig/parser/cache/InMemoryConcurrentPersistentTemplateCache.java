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
    private final ConcurrentMap<ResourceReference, Future<Node>> cache;
    private final Function<Future<Node>, Node> retriever = new RetrieveFuture<>();

    public InMemoryConcurrentPersistentTemplateCache () {
        this(101, Integer.MAX_VALUE);
    }
    public InMemoryConcurrentPersistentTemplateCache(int initialCapacity, int maxValue) {
        this.cache = new ConcurrentLinkedHashMap.Builder<ResourceReference, Future<Node>>()
                .initialCapacity(initialCapacity)
                .maximumWeightedCapacity(maxValue)
                .build();
    }

    @Override
    public Node get(JtwigParser parser, Environment environment, ResourceReference resource) {
        Optional<Future<Node>> optional = Optional.fromNullable(cache.get(resource));
        if (optional.isPresent()) {
            return retriever.apply(optional.get());
        } else {
            SettableFuture<Node> future = SettableFuture.create();
            Future<Node> result = cache.putIfAbsent(resource, future);
            if (result == null) {
                Node node = parser.parse(environment, resource);
                future.set(node);
                return node;
            } else {
                return retriever.apply(result);
            }
        }
    }
}
