package org.jtwig.content.json;

import java.util.Collection;

public class JsonMapperProviderConfiguration {
    private final Collection<JsonMapperProvider> jsonMapperProviders;

    public JsonMapperProviderConfiguration(Collection<JsonMapperProvider> jsonMapperProviders) {
        this.jsonMapperProviders = jsonMapperProviders;
    }

    public Collection<JsonMapperProvider> getJsonMapperProviders() {
        return jsonMapperProviders;
    }

}
