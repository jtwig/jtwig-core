package org.jtwig.functions.impl.structural.exceptions;

public class ParentFunctionWithoutExtending extends IllegalStateException {
    public ParentFunctionWithoutExtending() {
        super("Call to parent() in a template that does not extend another template.");
    }
}
