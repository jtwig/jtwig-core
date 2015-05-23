package org.jtwig.content.json;

import org.apache.commons.lang3.builder.Builder;

import java.util.ArrayList;
import java.util.Collection;

public class JsonMapperProviderConfigurationBuilder implements Builder<JsonMapperProviderConfiguration> {
    private final Collection<JsonMapperProvider> providers = new ArrayList<>();

    public JsonMapperProviderConfigurationBuilder () {}
    public JsonMapperProviderConfigurationBuilder (JsonMapperProviderConfiguration prototype) {
        providers.addAll(prototype.getJsonMapperProviders());
    }

    @Override
    public JsonMapperProviderConfiguration build() {
        return new JsonMapperProviderConfiguration(providers);
    }
}
