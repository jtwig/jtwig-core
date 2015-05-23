package org.jtwig.content.json;

import com.google.common.base.Function;

import java.util.Collection;

public class CompositeJsonMapperProvider implements JsonMapperProvider {
    private final Collection<JsonMapperProvider> jsonMapperFactories;

    public CompositeJsonMapperProvider(Collection<JsonMapperProvider> jsonMapperFactories) {
        this.jsonMapperFactories = jsonMapperFactories;
    }

    @Override
    public boolean isFound() {
        for (JsonMapperProvider jsonMapperProvider : jsonMapperFactories) {
            if (jsonMapperProvider.isFound()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Function<Object, String> jsonMapper() {
        for (JsonMapperProvider jsonMapperProvider : jsonMapperFactories) {
            if (jsonMapperProvider.isFound()) {
                return jsonMapperProvider.jsonMapper();
            }
        }
        return null;
    }
}
