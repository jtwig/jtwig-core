package org.jtwig.escape.config;

import org.jtwig.escape.EscapeEngine;

import java.util.Map;

public class EscapeEngineConfiguration {
    private final String initialEngine;
    private final String defaultEngine;
    private final Map<String, EscapeEngine> escapeEngineMap;

    public EscapeEngineConfiguration(String initialEngine, String defaultEngine, Map<String, EscapeEngine> escapeEngineMap) {
        this.initialEngine = initialEngine;
        this.defaultEngine = defaultEngine;
        this.escapeEngineMap = escapeEngineMap;
    }

    public String getInitialEngine() {
        return initialEngine;
    }

    public String getDefaultEngine() {
        return defaultEngine;
    }

    public Map<String, EscapeEngine> getEscapeEngineMap() {
        return escapeEngineMap;
    }
}
