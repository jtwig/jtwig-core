package org.jtwig.functions;

import java.util.Collection;
import java.util.Collections;

public abstract class SimpleJtwigFunction implements JtwigFunction {
    @Override
    public Collection<String> aliases() {
        return Collections.emptyList();
    }
}
