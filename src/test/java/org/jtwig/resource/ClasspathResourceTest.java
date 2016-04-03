package org.jtwig.resource;

import org.jtwig.resource.classpath.ClasspathResourceLoader;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class ClasspathResourceTest {

    private final ClasspathResourceLoader resourceLoader = mock(ClasspathResourceLoader.class);

    @Test
    public void equalSameInstance() throws Exception {
        ClasspathResource underTest = new ClasspathResource("path", resourceLoader);

        assertThat(underTest.equals(underTest), is(true));
    }

    @Test
    public void equalNull() throws Exception {
        ClasspathResource underTest = new ClasspathResource("path", resourceLoader);

        assertThat(underTest.equals(null), is(false));
    }

    @Test
    public void equalOtherType() throws Exception {
        ClasspathResource underTest = new ClasspathResource("path", resourceLoader);

        assertThat(underTest.equals(new Object()), is(false));
    }

    @Test
    public void equalSameType() throws Exception {
        ClasspathResource underTest = new ClasspathResource("path", resourceLoader);

        assertThat(underTest.equals(new ClasspathResource("path", resourceLoader)), is(true));
        assertThat(underTest.equals(new ClasspathResource("path1", resourceLoader)), is(false));
    }
}