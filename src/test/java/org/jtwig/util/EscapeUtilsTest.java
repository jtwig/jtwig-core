package org.jtwig.util;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class EscapeUtilsTest {

    @Test
    public void escapeJtwig() throws Exception {
        String result = EscapeUtils.escapeJtwig("c:\\");

        assertThat(result, is("c:\\\\"));
    }
}