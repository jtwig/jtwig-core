package org.jtwig.render.context;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class Context<T> {
    public static <T> Context<T> create (T item) {
        return new Context<>(item, new ArrayList<Runnable>());
    }

    private final AtomicReference<T> item;
    private final List<Runnable> endTasks;

    private Context(T item, List<Runnable> endTasks) {
        this.item = new AtomicReference<>(item);
        this.endTasks = endTasks;
    }

    public T getItem() {
        return item.get();
    }

    public void onEnd (Runnable run) {
        endTasks.add(run);
    }

    public void end () {
        for (Runnable task : endTasks) {
            task.run();
        }
    }

    public void set(T item) {
        this.item.set(item);
    }
}
