package org.jtwig.resource;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.InputStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class StringResourceTest {
    private StringResource underTest = new StringResource("one");

    @Test
    public void content() throws Exception {
        InputStream content = underTest.getContent();

        assertThat(IOUtils.toString(content), is("one"));
    }
}