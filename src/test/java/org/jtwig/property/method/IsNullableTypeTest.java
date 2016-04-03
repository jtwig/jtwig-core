package org.jtwig.property.method;

import org.junit.Test;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class IsNullableTypeTest {
    private IsNullableType underTest = new IsNullableType();

    @Test
    public void isNullable() throws Exception {
        for (Class nativeType : asList(Integer.TYPE, Float.TYPE, Double.TYPE, Long.TYPE, Boolean.TYPE, Character.TYPE)) {
            assertFalse(underTest.isNullable(nativeType));
        }

        for (Class nonNativeType : asList(String.class, Boolean.class)) {
            assertTrue(underTest.isNullable(nonNativeType));
        }
    }
}