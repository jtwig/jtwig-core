package org.jtwig.value;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class WrappedCollection implements Iterable<Map.Entry<String, Object>> {
    public static WrappedCollection empty() {
        return new WrappedCollection();
    }

    public static WrappedCollection singleton (Object value) {
        WrappedCollection collection = new WrappedCollection();
        collection.add("0", value);
        return collection;
    }

    private final LinkedHashMap<String, Object> store;

    public WrappedCollection() {
        this.store = new LinkedHashMap<>();
    }

    public WrappedCollection add (String key, Object value) {
        store.put(key, value);
        return this;
    }

    @Override
    public Iterator<Map.Entry<String, Object>> iterator() {
        return store.entrySet().iterator();
    }

    public int size() {
        return store.size();
    }

    public Collection<String> keys() {
        return store.keySet();
    }

    public Object getValue(String key) {
        return store.get(key);
    }

    public Collection<Object> values() {
        return store.values();
    }
}
