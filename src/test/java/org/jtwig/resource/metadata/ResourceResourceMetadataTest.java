package org.jtwig.resource.metadata;

import com.google.common.base.Optional;
import org.jtwig.resource.loader.ResourceLoader;
import org.jtwig.resource.reference.ResourceReference;
import org.junit.Test;

import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ResourceResourceMetadataTest {
    private final ResourceLoader resourceLoader = mock(ResourceLoader.class);
    private final ResourceReference resourceReference = mock(ResourceReference.class);
    private ResourceResourceMetadata underTest = new ResourceResourceMetadata(resourceLoader, resourceReference);

    @Test
    public void existsTrue() throws Exception {
        String path = "path";
        when(resourceReference.getPath()).thenReturn(path);
        when(resourceLoader.exists(path)).thenReturn(true);

        boolean result = underTest.exists();

        assertThat(result, is(true));
    }

    @Test
    public void existsFalse() throws Exception {
        String path = "path";
        when(resourceReference.getPath()).thenReturn(path);
        when(resourceLoader.exists(path)).thenReturn(false);

        boolean result = underTest.exists();

        assertThat(result, is(false));
    }

    @Test
    public void load() throws Exception {
        String path = "path";
        InputStream inputStream = mock(InputStream.class);
        when(resourceReference.getPath()).thenReturn(path);
        when(resourceLoader.load(path)).thenReturn(inputStream);

        InputStream result = underTest.load();

        assertSame(inputStream, result);
    }

    @Test
    public void getCharset() throws Exception {
        String path = "path";
        Optional<Charset> expected = Optional.of(Charset.defaultCharset());

        when(resourceReference.getPath()).thenReturn(path);
        when(resourceLoader.getCharset(path)).thenReturn(expected);

        Optional<Charset> result = underTest.getCharset();

        assertSame(expected, result);
    }

    @Test
    public void toUrl() throws Exception {
        String path = "path";
        Optional<URL> expected = Optional.of(new URL("file:/"));

        when(resourceReference.getPath()).thenReturn(path);
        when(resourceLoader.toUrl(path)).thenReturn(expected);

        Optional<URL> result = underTest.toUrl();

        assertSame(expected, result);
    }
}