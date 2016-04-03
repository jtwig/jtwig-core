package org.jtwig.util;

import org.jtwig.model.position.Position;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class UrlEncodingUtilsTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void encode() throws Exception {
        Position position = mock(Position.class);

        String result = UrlEncodingUtils.encode("&", "UTF-8", position);

        assertThat(result, is("%26"));
    }

    @Test
    public void encodeInvalid() throws Exception {
        Position position = mock(Position.class);
        String encoding = "blah";

        expectedException.expectMessage(containsString(String.format("Invalid encoding '%s'", encoding)));

        UrlEncodingUtils.encode("&", encoding, position);
    }
}