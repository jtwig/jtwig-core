package org.jtwig.escape.environment;

import org.jtwig.escape.EscapeEngine;
import org.jtwig.escape.EscapeEngineSelector;

public class EscapeEnvironment {
    private final EscapeEngine initialEscapeEngine;
    private final String defaultEscapeEngine;
    private final EscapeEngineSelector escapeEngineSelector;

    public EscapeEnvironment(EscapeEngine initialEscapeEngine, String defaultEscapeEngine, EscapeEngineSelector escapeEngineSelector) {
        this.initialEscapeEngine = initialEscapeEngine;
        this.defaultEscapeEngine = defaultEscapeEngine;
        this.escapeEngineSelector = escapeEngineSelector;
    }

    public EscapeEngine getInitialEscapeEngine() {
        return initialEscapeEngine;
    }

    public String getDefaultEscapeEngine() {
        return defaultEscapeEngine;
    }

    public EscapeEngineSelector getEscapeEngineSelector() {
        return escapeEngineSelector;
    }
}
