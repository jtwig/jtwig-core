package org.jtwig.resource.loader;

import com.google.common.base.Optional;
import org.jtwig.resource.exceptions.ResourceNotFoundException;
import org.junit.Test;

import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClasspathResourceLoaderTest {
    private final ClassLoader classLoader = mock(ClassLoader.class);
    private ClasspathResourceLoader underTest = new ClasspathResourceLoader(classLoader);

    @Test
    public void existsWithoutRoot() throws Exception {
        URL value = new URL("file:/");

        when(classLoader.getResource("path")).thenReturn(value);

        boolean result = underTest.exists("path");

        assertThat(result, is(true));
    }

    @Test
    public void existsPathWithRoot() throws Exception {
        URL value = new URL("file:/");

        when(classLoader.getResource("path")).thenReturn(value);

        boolean result = underTest.exists("/path");

        assertThat(result, is(true));
    }

    @Test
    public void existsNotFound() throws Exception {
        when(classLoader.getResource("path")).thenReturn(null);

        boolean result = underTest.exists("/path");

        assertThat(result, is(false));
    }

    @Test
    public void loadWithoutRoot() throws Exception {
        InputStream inputStream = mock(InputStream.class);

        when(classLoader.getResourceAsStream("path")).thenReturn(inputStream);

        InputStream result = underTest.load("path");

        assertThat(result, is(inputStream));
    }

    @Test
    public void loadPathWithRoot() throws Exception {
        InputStream inputStream = mock(InputStream.class);

        when(classLoader.getResourceAsStream("path")).thenReturn(inputStream);

        InputStream result = underTest.load("/path");

        assertThat(result, is(inputStream));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void loadPathNotFound() throws Exception {
        when(classLoader.getResourceAsStream("path")).thenReturn(null);

        underTest.load("/path");
    }

    @Test
    public void charset() throws Exception {
        Optional<Charset> result = underTest.getCharset("path");

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void toUrl() throws Exception {
        when(classLoader.getResource("path")).thenReturn(null);

        Optional<URL> result = underTest.toUrl("path");

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void toUrlWithRootPath() throws Exception {
        when(classLoader.getResource("path")).thenReturn(null);

        Optional<URL> result = underTest.toUrl("/path");

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void toUrlFound() throws Exception {
        URL url = new URL("file:/");
        when(classLoader.getResource("path")).thenReturn(url);

        Optional<URL> result = underTest.toUrl("/path");

        assertThat(result.get(), is(url));
    }
}