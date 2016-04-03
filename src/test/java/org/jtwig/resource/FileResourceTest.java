package org.jtwig.resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FileResourceTest {

    public static final File FILE = new File(FileUtils.getTempDirectory(), "example.twig");
    public static final String CONTENT = "example";

    @Before
    public void setUp() throws Exception {
        FileUtils.write(FILE, CONTENT);
    }

    @After
    public void tearDown() throws Exception {
        FileUtils.forceDelete(FILE);
    }

    @Test
    public void content() throws Exception {
        FileResource underTest = new FileResource(Charset.defaultCharset(), FILE);

        String content = IOUtils.toString(underTest.getContent());

        assertThat(content, is(CONTENT));
    }

    @Test
    public void hashCodeOfRelative() throws Exception {
        File file = new File("two");
        FileResource fileResource = new FileResource(Charset.defaultCharset(), file);
        FileResource anotherResource = new FileResource(Charset.defaultCharset(), new File(file, "../two"));

        assertThat(fileResource.hashCode(), is(anotherResource.hashCode()));
    }

    @Test
    public void hashCodeOfRelativeIOException() throws Exception {
        File file = mock(File.class);
        FileResource fileResource = new FileResource(Charset.defaultCharset(), file);
        FileResource anotherResource = new FileResource(Charset.defaultCharset(), file);

        when(file.getCanonicalPath()).thenThrow(IOException.class);

        assertThat(fileResource.hashCode(), is(anotherResource.hashCode()));
    }

    @Test
    public void equalsSameInstance() throws Exception {
        File file = new File("two");
        FileResource underTest = new FileResource(Charset.defaultCharset(), file);

        assertTrue(underTest.equals(underTest));
    }

    @Test
    public void equalsNull() throws Exception {
        File file = new File("two");
        FileResource underTest = new FileResource(Charset.defaultCharset(), file);

        assertFalse(underTest.equals(null));
    }

    @Test
    public void equalsOtherType() throws Exception {
        File file = new File("two");
        FileResource underTest = new FileResource(Charset.defaultCharset(), file);

        assertFalse(underTest.equals(new Object()));
    }

    @Test
    public void equalsCanonicalPathException() throws Exception {
        File file = mock(File.class);
        FileResource fileResource = new FileResource(Charset.defaultCharset(), file);
        FileResource anotherResource = new FileResource(Charset.defaultCharset(), file);

        when(file.getCanonicalPath()).thenThrow(IOException.class);

        assertTrue(fileResource.equals(anotherResource));
    }

    @Test
    public void equalsCanonicalPathOk() throws Exception {
        File file = mock(File.class);
        FileResource fileResource = new FileResource(Charset.defaultCharset(), file);
        FileResource anotherResource = new FileResource(Charset.defaultCharset(), file);

        when(file.getCanonicalPath()).thenReturn("hello");

        assertTrue(fileResource.equals(anotherResource));
    }
}