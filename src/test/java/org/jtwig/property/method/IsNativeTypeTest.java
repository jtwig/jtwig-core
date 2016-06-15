package org.jtwig.property.method;

import org.jtwig.property.method.argument.IsNativeType;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class IsNativeTypeTest {
    private IsNativeType underTest = IsNativeType.instance();

    @Test
    public void isNative() throws Exception {
        for (Class nativeType : asList(Integer.TYPE, Float.TYPE, Double.TYPE, Long.TYPE, Boolean.TYPE, Character.TYPE)) {
            assertTrue(underTest.isNative(nativeType));
        }

        for (Class nonNativeType : asList(String.class, Boolean.class)) {
            assertFalse(underTest.isNative(nonNativeType));
        }
    }
}