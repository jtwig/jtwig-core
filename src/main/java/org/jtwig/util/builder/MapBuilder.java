package org.jtwig.util.builder;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.builder.Builder;
import org.jtwig.environment.and.AndBuilder;

import java.util.HashMap;
import java.util.Map;

public class MapBuilder<B, K, V> implements Builder<Map<K, V>>, AndBuilder<B> {
    private final B parentBuilder;
    private Map<K, V> value = new HashMap<>();

    public MapBuilder(B parentBuilder) {
        this.parentBuilder = parentBuilder;
    }
    public MapBuilder(B parentBuilder, Map<K, V> values) {
        this.parentBuilder = parentBuilder;
        this.value = new HashMap<>(values);
    }
    public MapBuilder<B, K, V> add (K key, V value) {
        this.value.put(key, value);
        return this;
    }

    public MapBuilder<B, K, V> add (Map<K, V> values) {
        this.value.putAll(values);
        return this;
    }

    public MapBuilder<B, K, V> set (Map<K, V> values) {
        this.value = new HashMap<>(values);
        return this;
    }

    public MapBuilder<B, K, V> filter (Predicate<Map.Entry<K, V>> predicate) {
        Map<K, V> newValue = new HashMap<>();
        for (Map.Entry<K, V> entry : this.value.entrySet()) {
            if (predicate.apply(entry)) {
                newValue.put(entry.getKey(), entry.getValue());
            }
        }
        this.value = newValue;
        return this;
    }

    @Override
    public Map<K, V> build() {
        return ImmutableMap.copyOf(this.value);
    }

    @Override
    public B and() {
        return parentBuilder;
    }
}
