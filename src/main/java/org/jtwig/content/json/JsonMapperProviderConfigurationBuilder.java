package org.jtwig.content.json;

import org.apache.commons.lang3.builder.Builder;

import java.util.ArrayList;
import java.util.Collection;

public class JsonMapperProviderConfigurationBuilder<B extends JsonMapperProviderConfigurationBuilder> implements Builder<JsonMapperProviderConfiguration> {
    private final Collection<JsonMapperProvider> providers = new ArrayList<>();

    public JsonMapperProviderConfigurationBuilder () {}
    public JsonMapperProviderConfigurationBuilder (JsonMapperProviderConfiguration prototype) {
        providers.addAll(prototype.getJsonMapperProviders());
    }

    public B withJsonMapperProvider (JsonMapperProvider mapperProvider) {
        this.providers.add(mapperProvider);
        return self();
    }

    protected B self () {
        return (B) this;
    }

    @Override
    public JsonMapperProviderConfiguration build() {
        return new JsonMapperProviderConfiguration(providers);
    }
}
