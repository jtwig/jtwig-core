package org.jtwig.functions.impl;

import com.google.common.base.Optional;
import org.jtwig.exceptions.CalculationException;
import org.jtwig.reflection.model.Value;
import org.jtwig.reflection.model.java.JavaClass;
import org.jtwig.util.ClasspathFinder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ConstantFunctionTest {
    private final ClasspathFinder classpathFinder = mock(ClasspathFinder.class);
    private ConstantFunction underTest = new ConstantFunction(classpathFinder);

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void constantWhenInvalidConstant() throws Exception {
        expectedException.expect(CalculationException.class);
        expectedException.expectMessage(containsString("Invalid constant specified 'blah'"));

        underTest.constant("blah");
    }

    @Test
    public void constantWhenClassNotExists() throws Exception {
        when(classpathFinder.load("com.test.Test")).thenReturn(Optional.<JavaClass>absent());

        expectedException.expect(CalculationException.class);
        expectedException.expectMessage(containsString("Class com.test.Test not found"));

        underTest.constant("com.test.Test.HELLO");
    }

    @Test
    public void constantWhenConstantNotFound() throws Exception {
        JavaClass javaClass = mock(JavaClass.class);
        when(classpathFinder.load("com.test.Test")).thenReturn(Optional.<JavaClass>of(javaClass));
        when(javaClass.constant("HELLO")).thenReturn(Optional.<Value>absent());

        expectedException.expect(CalculationException.class);
        expectedException.expectMessage(containsString("Class com.test.Test does not expose constant HELLO"));

        underTest.constant("com.test.Test.HELLO");
    }

    @Test
    public void constantWhenOk() throws Exception {
        String value = "hello";
        JavaClass javaClass = mock(JavaClass.class);
        when(classpathFinder.load("com.test.Test")).thenReturn(Optional.<JavaClass>of(javaClass));
        when(javaClass.constant("HELLO")).thenReturn(Optional.<Value>of(new Value(value)));

        Object result = underTest.constant("com.test.Test.HELLO");

        assertEquals(result, value);
    }

    @Test
    public void constantEquals() throws Exception {
        JavaClass javaClass = mock(JavaClass.class);
        when(classpathFinder.load("com.test.Test")).thenReturn(Optional.<JavaClass>of(javaClass));
        when(javaClass.constant("HELLO")).thenReturn(Optional.<Value>of(new Value("hello")));

        boolean result = underTest.constantEquals("hello", "com.test.Test.HELLO");

        assertThat(result, is(true));
    }
}