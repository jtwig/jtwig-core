package org.jtwig.resource;

import org.junit.Test;

import java.net.URL;
import java.nio.charset.Charset;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class UrlResourceTest {
    @Test
    public void integration() throws Exception {
        Resource underTest1 = UrlResource.canonical(Charset.defaultCharset(), new URL("file:///tmp/example.txt"));
        Resource underTest2 = UrlResource.canonical(Charset.defaultCharset(), new URL("file:///tmp/../tmp/example.txt"));

        assertThat(underTest1.equals(underTest2), is(true));
    }
}