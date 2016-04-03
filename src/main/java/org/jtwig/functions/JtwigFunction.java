package org.jtwig.functions;

import java.util.Collection;

public interface JtwigFunction {
    String name ();
    Collection<String> aliases ();
    Object execute (FunctionRequest request);
}
