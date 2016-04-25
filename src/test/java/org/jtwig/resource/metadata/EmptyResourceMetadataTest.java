package org.jtwig.resource.metadata;

import com.google.common.base.Optional;
import org.jtwig.resource.exceptions.ResourceException;
import org.junit.Test;

import java.net.URL;
import java.nio.charset.Charset;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class EmptyResourceMetadataTest {
    private EmptyResourceMetadata underTest = EmptyResourceMetadata.instance();

    @Test
    public void exists() throws Exception {
        boolean result = underTest.exists();

        assertThat(result, is(false));
    }

    @Test(expected = ResourceException.class)
    public void load() throws Exception {
        underTest.load();
    }

    @Test
    public void getCharset() throws Exception {
        Optional<Charset> result = underTest.getCharset();

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void toUrl() throws Exception {
        Optional<URL> result = underTest.toUrl();

        assertThat(result.isPresent(), is(false));
    }
}