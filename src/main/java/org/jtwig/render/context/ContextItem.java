package org.jtwig.render.context;

import java.util.ArrayList;
import java.util.List;

public class ContextItem<T> implements OnEndTrigger {
    private final T item;
    private final List<Runnable> endTasks = new ArrayList<>();

    public ContextItem(T item) {
        this.item = item;
    }

    public T getItem() {
        return item;
    }

    public void onEnd (Runnable runnable) {
        endTasks.add(runnable);
    }

    @Override
    public void end() {
        for (Runnable endTask : endTasks) {
            endTask.run();
        }
    }
}
