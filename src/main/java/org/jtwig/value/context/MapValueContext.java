package org.jtwig.value.context;

import org.jtwig.value.Undefined;

import java.util.HashMap;
import java.util.Map;

public class MapValueContext implements ValueContext {
    public static MapValueContext newContext () {
        return new MapValueContext(new HashMap<String, Object>());
    }
    public static MapValueContext newContext (Map<String, Object> map) {
        return new MapValueContext(new HashMap<>(map));
    }

    private final Map<String, Object> values;

    public MapValueContext(Map<String, Object> values) {
        this.values = values;
    }

    @Override
    public Object resolve(String key) {
        if (values.containsKey(key)) return values.get(key);
        else return Undefined.UNDEFINED;
    }

    @Override
    public ValueContext with(String key, Object value) {
        values.put(key, value);
        return this;
    }
}
