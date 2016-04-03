package org.jtwig.environment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.HashMap;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertSame;

public class EnvironmentTest {
    private final HashMap<String, Object> parameters = new HashMap<>();
    private Environment underTest = new Environment(null, parameters, null, null, null, null, null, null);

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        parameters.clear();
    }

    @Test
    public void parameterNotPresent() throws Exception {
        String key = "key";

        expectedException.expectMessage(containsString("No property found with name 'key'"));

        underTest.parameter(key);
    }
    @Test
    public void parameterPresent() throws Exception {
        String key = "key";
        Object value = new Object();

        parameters.put(key, value);

        Object result = underTest.parameter(key);

        assertSame(value, result);
    }

    @Test
    public void parameterWithDefaultPresent() throws Exception {
        String key = "key";
        Object value = new Object();
        Object defaultValue = new Object();

        parameters.put(key, value);

        Object result = underTest.parameter(key, defaultValue);

        assertSame(value, result);
    }

    @Test
    public void parameterWithDefaultNotPresent() throws Exception {
        String key = "key";
        Object value = new Object();
        Object defaultValue = new Object();

        Object result = underTest.parameter(key, defaultValue);

        assertSame(defaultValue, result);
    }
}