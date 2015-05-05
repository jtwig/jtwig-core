package org.jtwig.functions.impl;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.*;

public class MapFunctionsTest {
    private MapFunctions underTest = new MapFunctions();

    @Test
    public void first() throws Exception {
        assertEquals(underTest.first(new TreeMap<String, Object>() {{
            put("a", 1);
            put("b", 2);
        }}), 1);
    }


    @Test
    public void last() throws Exception {
        assertEquals(underTest.last(new TreeMap<String, Object>() {{
            put("a", 1);
            put("b", 2);
        }}), 2);
    }


    @Test
    public void lenght() throws Exception {
        Integer length = underTest.length(new HashMap<String, Object>() {{
            put("a", null);
            put("b", null);
        }});
        assertThat(length, is(2));
    }


    @Test
    public void mapKeys() throws Exception {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("a", "a");
        map.put("b", "a");

        Set<String> list = (Set<String>) underTest.keys(map);
        assertThat(list, hasItem("a"));
        assertThat(list, hasItem("b"));
    }
}