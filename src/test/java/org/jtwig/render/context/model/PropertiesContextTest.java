package org.jtwig.render.context.model;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class PropertiesContextTest {

    @Test
    public void newContext() throws Exception {
        PropertiesContext result = PropertiesContext.newContext();

        assertFalse(result.has("something"));
    }

    @Test
    public void get() throws Exception {
        String value = "value";
        HashMap<String, Object> map = new HashMap<>();
        map.put("one", value);
        PropertiesContext underTest = new PropertiesContext(map);

        String result = underTest.get("one");

        assertSame(value, result);
    }

    @Test
    public void set() throws Exception {
        String value = "value";
        HashMap<String, Object> map = new HashMap<>();
        PropertiesContext underTest = new PropertiesContext(map);

        underTest.set("one", value);

        assertSame(value, map.get("one"));
    }

    @Test
    public void hasWhenExists() throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        map.put("one", "value");
        PropertiesContext underTest = new PropertiesContext(map);

        assertTrue(underTest.has("one"));
        assertFalse(underTest.has("two"));
    }
}