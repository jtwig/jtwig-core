package org.jtwig.resource;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.InputStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class StringResourceTest {
    private StringResource underTest = new StringResource("one");

    @Test
    public void content() throws Exception {
        InputStream content = underTest.getContent();

        assertThat(IOUtils.toString(content), is("one"));
    }
    @Test
    public void equalsSameReferenceTest() throws Exception {
        StringResource underTest = new StringResource("one");

        assertTrue(underTest.equals(underTest));
    }

    @Test
    public void equalsToNull() throws Exception {
        StringResource underTest = new StringResource("one");

        assertFalse(underTest.equals(null));
    }

    @Test
    public void equalsDifferentType() throws Exception {
        StringResource underTest = new StringResource("one");

        assertFalse(underTest.equals(new Object()));
    }

    @Test
    public void equalsDifferentInstanceFalse() throws Exception {
        StringResource underTest = new StringResource("one");
        StringResource other = new StringResource("one1");

        assertFalse(underTest.equals(other));
    }

    @Test
    public void equalsDifferentInstanceTrue() throws Exception {
        StringResource underTest = new StringResource("one");
        StringResource other = new StringResource("one");

        assertTrue(underTest.equals(other));
    }
}