package org.jtwig.resource.loader;

import com.google.common.base.Optional;
import org.jtwig.resource.exceptions.ResourceNotFoundException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CompositeResourceLoaderTest {
    private final ArrayList<ResourceLoader> loaders = new ArrayList<>();
    private final CompositeResourceLoader underTest = new CompositeResourceLoader(loaders);

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        loaders.clear();
    }

    @Test
    public void getCharsetUnhappy() throws Exception {
        Optional<Charset> result = underTest.getCharset("path");

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void loadUnhappy() throws Exception {
        expectedException.expect(ResourceNotFoundException.class);
        expectedException.expectMessage(String.format("Resource '%s' not found", "path"));

        underTest.load("path");
    }

    @Test
    public void existsUnhappy() throws Exception {
        boolean result = underTest.exists("path");

        assertThat(result, is(false));
    }

    @Test
    public void toUrlUnhappy() throws Exception {
        ResourceLoader resourceLoader = mock(ResourceLoader.class);

        loaders.add(resourceLoader);
        when(resourceLoader.toUrl("path")).thenReturn(Optional.<URL>absent());

        Optional<URL> result = underTest.toUrl("path");

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void toUrlHappy() throws Exception {
        ResourceLoader resourceLoader = mock(ResourceLoader.class);
        Optional<URL> expected = Optional.of(new URL("file:/"));

        loaders.add(resourceLoader);
        when(resourceLoader.exists("path")).thenReturn(true);
        when(resourceLoader.toUrl("path")).thenReturn(expected);

        Optional<URL> result = underTest.toUrl("path");

        assertThat(result, is(expected));
    }
}