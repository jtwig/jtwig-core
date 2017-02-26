package org.jtwig.render.context;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class RenderContext {
    public static RenderContext create () {
        return new RenderContext(new HashMap<Class, Stack<Context<?>>>());
    }

    private final Map<Class, Stack<Context<?>>> contexts;

    public RenderContext(Map<Class, Stack<Context<?>>> contexts) {
        this.contexts = contexts;
    }

    public <T> RenderContext start (Class<T> type, T context) {
        if (!contexts.containsKey(type)) {
            contexts.put(type, new Stack<Context<?>>());
        }

        contexts.get(type).push(Context.create(context));
        return this;
    }

    public <T> T end (Class<T> type) {
        if (!contexts.containsKey(type)) {
            throw new IllegalStateException(String.format("There is no context for %s", type));
        }

        Context<?> context = contexts.get(type).pop();
        context.end();
        return type.cast(context.getItem());
    }

    private <T> Context<T> getCurrentContext (Class<T> type) {
        if (!contexts.containsKey(type)) {
            throw new IllegalStateException(String.format("There is no context for %s", type));
        }
        return (Context<T>) contexts.get(type).peek();
    }

    public <T> T getCurrent (Class<T> type) {
        return getCurrentContext(type).getItem();
    }

    public boolean hasCurrent (Class type) {
        return contexts.containsKey(type) && !contexts.get(type).isEmpty();
    }

    public void onEndCurrent (Class type, Runnable runnable) {
        getCurrentContext(type).onEnd(runnable);
    }

    public <T> void set(Class<T> type, T item) {
        getCurrentContext(type).set(item);
    }
}
