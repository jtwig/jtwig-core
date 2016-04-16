package org.jtwig.escape.config;

import org.apache.commons.lang3.builder.Builder;
import org.jtwig.escape.EscapeEngine;
import org.jtwig.util.builder.MapBuilder;

public class EscapeEngineConfigurationBuilder<B extends EscapeEngineConfigurationBuilder> implements Builder<EscapeEngineConfiguration> {
    private String initialEngine;
    private String defaultEngine;
    private MapBuilder<EscapeEngineConfigurationBuilder<B>, String, EscapeEngine> escapeEngineMap;

    public EscapeEngineConfigurationBuilder(EscapeEngineConfiguration prototype) {
        this.escapeEngineMap = new MapBuilder<>(this, prototype.getEscapeEngineMap());
        this.initialEngine = prototype.getInitialEngine();
        this.defaultEngine = prototype.getDefaultEngine();
    }

    public EscapeEngineConfigurationBuilder() {
        this.escapeEngineMap = new MapBuilder<>(this);
    }

    public B withInitialEngine(String initialEngine) {
        this.initialEngine = initialEngine;
        return self();
    }

    public B withDefaultEngine(String defaultEngine) {
        this.defaultEngine = defaultEngine;
        return self();
    }

    private B self() {
        return (B) this;
    }

    public MapBuilder<EscapeEngineConfigurationBuilder<B>, String, EscapeEngine> engines() {
        return escapeEngineMap;
    }

    @Override
    public EscapeEngineConfiguration build() {
        return new EscapeEngineConfiguration(initialEngine, defaultEngine, escapeEngineMap.build());
    }
}
