package org.jtwig.render.escape;

import com.google.common.base.Optional;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class EscapeEngineSelector {
    public static EscapeEngineSelector newInstance(Map<String, EscapeEngine> escapeEngineMap) {
        Map<String, EscapeEngine> map = new HashMap<>();
        for (Map.Entry<String, EscapeEngine> entry : escapeEngineMap.entrySet()) {
            map.put(entry.getKey().toLowerCase(), entry.getValue());
        }
        return new EscapeEngineSelector(map);
    }

    private final Map<String, EscapeEngine> escapeEngineMap;

    private EscapeEngineSelector(Map<String, EscapeEngine> escapeEngineMap) {
        this.escapeEngineMap = escapeEngineMap;
    }

    public Optional<EscapeEngine> escapeEngineFor (String name) {
        return Optional.fromNullable(escapeEngineMap.get(StringUtils.lowerCase(name)));
    }

    public Collection<String> availableEscapeEngines() {
        return escapeEngineMap.keySet();
    }
}
