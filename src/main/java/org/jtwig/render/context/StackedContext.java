package org.jtwig.render.context;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class StackedContext<T> {
    public static <T> StackedContext<T> context (T initial) {
        return StackedContext.<T>emptyContext().start(initial);
    }
    public static <T> StackedContext<T> emptyContext () {
        return new StackedContext<>(new Stack<T>());
    }

    private final Stack<T> contexts;
    private final List<Runnable> endTasks = new ArrayList<>();

    public StackedContext(Stack<T> contexts) {
        this.contexts = contexts;
    }

    public T getCurrent () {
        return contexts.peek();
    }

    public boolean hasCurrent () {
        return !contexts.isEmpty();
    }

    public StackedContext<T> end () {
        T element = contexts.pop();
        if (element instanceof OnEndTrigger) {
            ((OnEndTrigger) element).end();
        }
        return this;
    }

    public StackedContext<T> start (T context) {
        contexts.add(context);
        return this;
    }

    public void set(T context) {
        end();
        start(context);
    }
}
