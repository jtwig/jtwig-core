package org.jtwig.render.context.model;

import java.util.HashMap;
import java.util.Map;

public class PropertiesContext {
    public static PropertiesContext newContext () {
        return new PropertiesContext(new HashMap<String, Object>());
    }

    private final Map<String, Object> properties;

    public PropertiesContext(Map<String, Object> properties) {
        this.properties = properties;
    }

    public void set (String key, Object value) {
        properties.put(key, value);
    }

    public <T> T get (String key) {
        return (T) properties.get(key);
    }

    public boolean has (String key) {
        return properties.containsKey(key);
    }
}
