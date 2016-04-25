package org.jtwig.resource.loader;

import com.google.common.base.Optional;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class StringResourceLoaderTest {
    private StringResourceLoader underTest = StringResourceLoader.instance();

    @Test
    public void charset() throws Exception {
        Optional<Charset> result = underTest.getCharset("path");

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void exists() throws Exception {
        boolean result = underTest.exists("path");

        assertThat(result, is(true));
    }

    @Test
    public void load() throws Exception {
        InputStream result = underTest.load("path");

        assertThat(IOUtils.toString(result), is("path"));
    }

    @Test
    public void toUrl() throws Exception {
        Optional<URL> result = underTest.toUrl("path");

        assertThat(result.isPresent(), is(false));
    }
}