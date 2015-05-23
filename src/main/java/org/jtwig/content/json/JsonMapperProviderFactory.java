package org.jtwig.content.json;

public class JsonMapperProviderFactory {
    public JsonMapperProvider create (JsonMapperProviderConfiguration configuration) {
        return new CompositeJsonMapperProvider(configuration.getJsonMapperProviders());
    }
}
