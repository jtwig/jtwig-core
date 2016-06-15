package org.jtwig.property.method.argument;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AssignableTypesTest {
    private final AssignableTypes underTest = new AssignableTypes(IsNativeType.instance());

    @Test
    public void nativeTypesAreAssignableFromBoxedTypes() throws Exception {
        Map<Class, Class> boxed = ImmutableMap.<Class, Class>builder()
                .put(Integer.TYPE, Integer.class)
                .put(Float.TYPE, Float.class)
                .put(Double.TYPE, Double.class)
                .put(Long.TYPE, Long.class)
                .put(Boolean.TYPE, Boolean.class)
                .put(Character.TYPE, Character.class)
                .build();

        for (Map.Entry<Class, Class> entry : boxed.entrySet()) {
            assertTrue(String.format("%s is assignable from %s", entry.getKey(), entry.getValue()), underTest.isAssignable(entry.getKey(), entry.getValue()));
        }
    }

    @Test
    public void boxedTypesAreAssignableFromNativeTypes() throws Exception {
        Map<Class, Class> boxed = ImmutableMap.<Class, Class>builder()
                .put(Integer.TYPE, Integer.class)
                .put(Float.TYPE, Float.class)
                .put(Double.TYPE, Double.class)
                .put(Long.TYPE, Long.class)
                .put(Boolean.TYPE, Boolean.class)
                .put(Character.TYPE, Character.class)
                .build();

        for (Map.Entry<Class, Class> entry : boxed.entrySet()) {
            assertTrue(String.format("%s is assignable from %s", entry.getValue(), entry.getKey()), underTest.isAssignable(entry.getValue(), entry.getKey()));
        }
    }

    @Test
    public void isAssignableUnhappy() throws Exception {
        assertFalse(underTest.isAssignable(String.class, Integer.class));
    }

    @Test
    public void isAssignableHappy() throws Exception {
        assertTrue(underTest.isAssignable(CharSequence.class, String.class));
    }
}