package org.jtwig.render;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class StringBuilderRenderResultTest {
    private StringBuilderRenderResult underTest = new StringBuilderRenderResult();

    @Test
    public void appendAndContent() throws Exception {
        underTest.append("one");

        String result = underTest.content();

        assertThat(result, is("one"));
    }

    @Test
    public void flush() throws Exception {
        underTest.flush();

        // no effect
    }
}