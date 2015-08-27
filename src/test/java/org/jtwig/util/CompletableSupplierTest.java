package org.jtwig.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class CompletableSupplierTest {
    @Test(expected = IllegalStateException.class)
    public void getWhenIncomplete() throws Exception {
        new CompletableSupplier<>().get();
    }

    @Test
    public void getWhenComplete() throws Exception {
        Object value = new Object();

        Object result = new CompletableSupplier<>().complete(value).get();

        assertSame(value, result);
    }
}