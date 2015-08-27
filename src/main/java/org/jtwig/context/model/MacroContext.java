package org.jtwig.context.model;

import com.google.common.base.Optional;

import org.jtwig.context.RenderContext;

import java.io.ByteArrayOutputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class MacroContext {
    private final Map<String, Macro> macros;

    public MacroContext(Map<String, Macro> macros) {
        this.macros = macros;
    }

    public Optional<Macro> resolve(String name) {
        return Optional.fromNullable(macros.get(name));
    }
}
