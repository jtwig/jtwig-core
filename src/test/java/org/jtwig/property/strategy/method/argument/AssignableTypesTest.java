package org.jtwig.property.strategy.method.argument;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class AssignableTypesTest {
    private AssignableTypes underTest = new AssignableTypes(IsNativeType.instance());

    @Test
    public void isAssignable() throws Exception {
        assertTrue(underTest.isAssignable(Integer.TYPE, Integer.TYPE));
    }

}