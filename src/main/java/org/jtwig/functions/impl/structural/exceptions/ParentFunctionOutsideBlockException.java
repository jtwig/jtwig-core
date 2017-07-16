package org.jtwig.functions.impl.structural.exceptions;

public class ParentFunctionOutsideBlockException extends IllegalStateException {
    public ParentFunctionOutsideBlockException() {
        super("Call to parent() when not in a block.");
    }
}
