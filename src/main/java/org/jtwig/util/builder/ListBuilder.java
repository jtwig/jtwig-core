package org.jtwig.util.builder;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.builder.Builder;
import org.jtwig.environment.and.AndBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ListBuilder<B, T> implements Builder<List<T>>, AndBuilder<B> {
    private final B parent;
    private List<T> list = new ArrayList<>();

    public ListBuilder(B parent) {
        this.parent = parent;
    }
    public ListBuilder(B parent, Collection<? extends T> list) {
        this.parent = parent;
        this.list = new ArrayList<>(list);
    }


    public ListBuilder<B, T> set (List<? extends T> items) {
        this.list = new ArrayList<>(items);
        return this;
    }

    public ListBuilder<B, T> add (T item) {
        this.list.add(item);
        return this;
    }

    public ListBuilder<B, T> add (List<? extends T> items) {
        this.list.addAll(items);
        return this;
    }

    public ListBuilder<B, T> filter (Predicate<T> predicate) {
        List<T> newList = new ArrayList<>();
        for (T item : this.list) {
            if (predicate.apply(item)) {
                newList.add(item);
            }
        }
        this.list = newList;
        return this;
    }

    @Override
    public B and() {
        return parent;
    }

    @Override
    public List<T> build() {
        return ImmutableList.copyOf(list);
    }
}
