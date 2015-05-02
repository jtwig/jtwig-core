package org.jtwig.util;

import com.google.common.base.Optional;
import com.google.common.base.Supplier;

public class CompletableSupplier<T> implements Supplier<T> {
    private Optional<T> value = Optional.absent();

    public CompletableSupplier<T> complete(T value) {
        this.value = Optional.fromNullable(value);
        return this;
    }

    @Override
    public T get() {
        return value.get();
    }
}
