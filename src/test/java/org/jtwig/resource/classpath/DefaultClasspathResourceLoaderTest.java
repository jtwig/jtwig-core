package org.jtwig.resource.classpath;

import org.junit.Test;

import java.io.InputStream;
import java.net.URL;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DefaultClasspathResourceLoaderTest {
    private DefaultClasspathResourceLoader underTest = new DefaultClasspathResourceLoader(DefaultClasspathResourceLoaderTest.class.getClassLoader());

    @Test
    public void existsWhenResourceExists() throws Exception {
        String existentResource = "/example/classpath-template.twig";

        boolean result = underTest.exists(existentResource);

        assertThat(result, is(true));
    }

    @Test
    public void httpUrl() throws Exception {
        ClassLoader classLoader = mock(ClassLoader.class);
        DefaultClasspathResourceLoader underTest = new DefaultClasspathResourceLoader(classLoader);
        String resourcePath = "name";

        when(classLoader.getResource(resourcePath)).thenReturn(new URL("http://asdasd"));

        assertFalse(underTest.exists(resourcePath));
    }

    @Test
    public void resourceNull() throws Exception {
        ClassLoader classLoader = mock(ClassLoader.class);
        DefaultClasspathResourceLoader underTest = new DefaultClasspathResourceLoader(classLoader);
        String resourcePath = "name";

        when(classLoader.getResource(resourcePath)).thenReturn(null);

        assertFalse(underTest.exists(resourcePath));
    }

    @Test
    public void resourceUrl() throws Exception {
        ClassLoader classLoader = mock(ClassLoader.class);
        DefaultClasspathResourceLoader underTest = new DefaultClasspathResourceLoader(classLoader);
        String resourcePath = "name";

        when(classLoader.getResource(resourcePath)).thenReturn(new URL("http://~^@asdasd"));

        assertFalse(underTest.exists(resourcePath));
    }

    @Test
    public void existsWhenResourceNotExists() throws Exception {
        String existentResource = "example/nonexistent";

        boolean result = underTest.exists(existentResource);

        assertThat(result, is(false));
    }

    @Test
    public void loadWhenResourceExists() throws Exception {
        String existentResource = "/example/classpath-template.twig";

        InputStream result = underTest.load(existentResource);

        assertThat(result, notNullValue());
    }

    @Test
    public void loadWhenResourceNotExists() throws Exception {
        String existentResource = "/example/baf";

        InputStream result = underTest.load(existentResource);

        assertThat(result, nullValue());
    }

    @Test
    public void existsWeirdUrl() throws Exception {

        boolean result = underTest.exists("£213");

    }
}