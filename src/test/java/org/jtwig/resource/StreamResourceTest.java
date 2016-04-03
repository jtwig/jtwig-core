package org.jtwig.resource;

import org.junit.Test;

import java.io.InputStream;
import java.nio.charset.Charset;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class StreamResourceTest {

    @Test
    public void equalsSameReferenceTest() throws Exception {
        Charset charset = mock(Charset.class);
        InputStream inputStream = mock(InputStream.class);
        StreamResource underTest = new StreamResource(charset, inputStream);

        assertTrue(underTest.equals(underTest));
    }

    @Test
    public void equalsToNull() throws Exception {
        Charset charset = mock(Charset.class);
        InputStream inputStream = mock(InputStream.class);
        StreamResource underTest = new StreamResource(charset, inputStream);

        assertFalse(underTest.equals(null));
    }

    @Test
    public void equalsDifferentType() throws Exception {
        Charset charset = mock(Charset.class);
        InputStream inputStream = mock(InputStream.class);
        StreamResource underTest = new StreamResource(charset, inputStream);

        assertFalse(underTest.equals(new Object()));
    }

    @Test
    public void equalsDifferentInstanceFalse() throws Exception {
        Charset charset = mock(Charset.class);
        InputStream inputStream = mock(InputStream.class);
        InputStream otherInputStream = mock(InputStream.class);
        StreamResource underTest = new StreamResource(charset, inputStream);
        StreamResource other = new StreamResource(charset, otherInputStream);

        assertFalse(underTest.equals(other));
    }

    @Test
    public void equalsDifferentInstanceTrue() throws Exception {
        Charset charset = mock(Charset.class);
        InputStream inputStream = mock(InputStream.class);
        StreamResource underTest = new StreamResource(charset, inputStream);
        StreamResource other = new StreamResource(charset, inputStream);

        assertTrue(underTest.equals(other));
    }

    @Test
    public void hashCodeTest() throws Exception {
        Charset charset = mock(Charset.class);
        InputStream inputStream = mock(InputStream.class);
        StreamResource underTest = new StreamResource(charset, inputStream);

        assertEquals(inputStream.hashCode(), underTest.hashCode());
    }
}