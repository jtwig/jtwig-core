package org.jtwig.resource.loader;

import com.google.common.base.Optional;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.jtwig.resource.exceptions.ResourceNotFoundException;
import org.junit.Test;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class FileResourceLoaderTest {
    private FileResourceLoader underTest = FileResourceLoader.instance();

    @Test
    public void charset() throws Exception {
        Optional<Charset> result = underTest.getCharset("path");

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void existsTrue() throws Exception {
        URL resource = getClass().getClassLoader().getResource("example/classpath-error.twig");

        boolean result = underTest.exists(resource.getFile());

        assertThat(result, is(true));
    }

    @Test
    public void existsFalse() throws Exception {
        boolean result = underTest.exists("blah");

        assertThat(result, is(false));
    }

    @Test
    public void toUrl() throws Exception {
        URL resource = getClass().getClassLoader().getResource("example/classpath-error.twig");

        Optional<URL> result = underTest.toUrl(resource.getFile());

        assertThat(result.get(), is(resource));
    }

    @Test
    public void toUrlInexistent() throws Exception {
        Optional<URL> result = underTest.toUrl("blah");

        assertThat(result.isPresent(), is(true));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void load() throws Exception {
        underTest.load("blah");
    }

    @Test
    public void loadExists() throws Exception {
        URL resource = getClass().getClassLoader().getResource("example/classpath-error.twig");
        String expected = FileUtils.readFileToString(new File(resource.getFile()));

        InputStream result = underTest.load(resource.getFile());

        assertThat(IOUtils.toString(resource), is(expected));
    }
}