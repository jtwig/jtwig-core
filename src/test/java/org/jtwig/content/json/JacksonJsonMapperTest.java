package org.jtwig.content.json;

import org.codehaus.jackson.map.ObjectMapper;
import org.jtwig.exceptions.CalculationException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JacksonJsonMapperTest {
    private final ObjectMapper objectMapper = mock(ObjectMapper.class);
    private JacksonJsonMapper underTest = new JacksonJsonMapper(objectMapper);

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void applyWhenOk() throws Exception {
        Object input = new Object();
        String expected = "hello";
        when(objectMapper.writeValueAsString(input)).thenReturn(expected);

        String result = underTest.apply(input);

        assertThat(result, is(expected));
    }

    @Test
    public void applyWhenKo() throws Exception {
        Object input = new Object();
        when(objectMapper.writeValueAsString(input)).thenThrow(IOException.class);

        expectedException.expect(CalculationException.class);
        expectedException.expectMessage(containsString("Unable to obtain json from object"));

        underTest.apply(input);
    }
}