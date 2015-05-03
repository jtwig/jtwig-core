package org.jtwig.context.model;

import java.util.Stack;

public class EscapeModeContext {
    private final Stack<EscapeMode> escapeModeStack;

    public EscapeModeContext(Stack<EscapeMode> escapeModeStack) {
        this.escapeModeStack = escapeModeStack;
    }

    public EscapeMode currentEscapeMode () {
        return escapeModeStack.peek();
    }

    public EscapeModeContext startEscapeMode (EscapeMode escapeMode) {
        this.escapeModeStack.push(escapeMode);
        return this;
    }

    public EscapeModeContext stop () {
        this.escapeModeStack.pop();
        return this;
    }
}
