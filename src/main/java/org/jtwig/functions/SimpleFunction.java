package org.jtwig.functions;

public interface SimpleFunction {
    String name ();
    Object execute (Object... arguments);
}
