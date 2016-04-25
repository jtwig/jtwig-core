package org.jtwig.resource.loader;

import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import org.jtwig.resource.exceptions.ResourceNotFoundException;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InMemoryResourceLoaderTest {
    private final HashMap<String, Supplier<InputStream>> resources = new HashMap<>();
    private InMemoryResourceLoader underTest = new InMemoryResourceLoader(resources);

    @Before
    public void setUp() throws Exception {
        resources.clear();
    }

    @Test
    public void charset() throws Exception {
        Optional<Charset> result = underTest.getCharset("path");

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void existsFalse() throws Exception {
        boolean result = underTest.exists("blah");

        assertThat(result, is(false));
    }

    @Test
    public void existsTrue() throws Exception {
        resources.put("a", mock(Supplier.class));

        boolean result = underTest.exists("a");

        assertThat(result, is(true));
    }

    @Test
    public void toUrl() throws Exception {
        Optional<URL> result = underTest.toUrl("a");

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void loadFound() throws Exception {
        InputStream inputStream = mock(InputStream.class);
        Supplier<InputStream> supplier = mock(Supplier.class);
        resources.put("a", supplier);

        when(supplier.get()).thenReturn(inputStream);

        InputStream result = underTest.load("a");

        assertThat(result, is(inputStream));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void loadNotFound() throws Exception {
        underTest.load("a");
    }
}