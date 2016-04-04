package org.jtwig.parser.cache;

import com.google.common.base.Function;
import org.jtwig.renderable.RenderException;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class RetrieveFuture<T> implements Function<Future<T>, T> {
    @Override
    public T apply(Future<T> input) {
        try {
            return input.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RenderException(e);
        }
    }
}
