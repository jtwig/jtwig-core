package org.jtwig.macro;

import com.google.common.base.Optional;

import java.util.Map;

public class ImportedMacros {
    private final Map<String, Macro> macros;

    public ImportedMacros(Map<String, Macro> macros) {
        this.macros = macros;
    }

    public Optional<Macro> resolve(String name) {
        return Optional.fromNullable(macros.get(name));
    }
}
