package org.jtwig.util;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ClasspathFinderTest {
    private ClasspathFinder underTest = new ClasspathFinder(getClass().getClassLoader());

    @Test
    public void existsWhenValid() throws Exception {
        boolean result = underTest.exists(getClass().getName());

        assertThat(result, is(true));
    }

    @Test
    public void existsWhenInvalid() throws Exception {
        boolean result = underTest.exists("blah");

        assertThat(result, is(false));
    }
}