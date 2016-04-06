package org.jtwig.util.builder;

import com.google.common.base.Predicate;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;

public class MapBuilderTest {
    @Test
    public void addShouldAppendToMap() throws Exception {
        Object parent = new Object();
        Object value = new Object();
        String key = "key";
        Object preAddedValue = new Object();
        String preAddedKey = "test";

        Map<String, Object> map = new HashMap<>();
        map.put(preAddedKey, preAddedValue);
        MapBuilder<Object, String, Object> underTest = new MapBuilder<>(parent, map);

        Map<String, Object> result = underTest.add(key, value).build();

        assertThat(result.get(key), is(value));
        assertThat(result.get(preAddedKey), is(preAddedValue));
        assertThat(result.size(), is(2));
    }

    @Test
    public void addMapShouldAppend() throws Exception {
        Object parent = new Object();
        Object value = new Object();
        String key = "key";
        Object preAddedValue = new Object();
        String preAddedKey = "test";

        Map<String, Object> map = new HashMap<>();
        map.put(preAddedKey, preAddedValue);
        MapBuilder<Object, String, Object> underTest = new MapBuilder<>(parent, map);

        HashMap<String, Object> newValues = new HashMap<>();
        newValues.put(key, value);
        Map<String, Object> result = underTest.add(newValues).build();

        assertThat(result.get(key), is(value));
        assertThat(result.get(preAddedKey), is(preAddedValue));
        assertThat(result.size(), is(2));
    }

    @Test
    public void setShouldOverride() throws Exception {
        Object parent = new Object();
        Object value = new Object();
        String key = "key";
        Object preAddedValue = new Object();
        String preAddedKey = "test";

        Map<String, Object> map = new HashMap<>();
        map.put(preAddedKey, preAddedValue);
        MapBuilder<Object, String, Object> underTest = new MapBuilder<>(parent, map);

        HashMap<String, Object> newValues = new HashMap<>();
        newValues.put(key, value);
        Map<String, Object> result = underTest.set(newValues).build();

        assertThat(result.get(key), is(value));
        assertThat(result.containsKey(preAddedKey), is(false));
        assertThat(result.size(), is(1));
    }

    @Test
    public void filterShouldLeaveOnesReturningTrue() throws Exception {
        Object parent = new Object();
        final String preAddedKey1 = "test1";
        final String preAddedKey2 = "test2";
        Object preAddedValue1 = new Object();
        Object preAddedValue2 = new Object();

        Map<String, Object> map = new HashMap<>();
        map.put(preAddedKey1, preAddedValue1);
        map.put(preAddedKey2, preAddedValue2);
        MapBuilder<Object, String, Object> underTest = new MapBuilder<>(parent, map);
        Map<String, Object> result = underTest.filter(new Predicate<Map.Entry<String, Object>>() {
            @Override
            public boolean apply(Map.Entry<String, Object> input) {
                return input.getKey().equals(preAddedKey2);
            }
        }).build();

        assertThat(result.get(preAddedKey2), is(preAddedValue2));
        assertThat(result.containsKey(preAddedKey1), is(false));
        assertThat(result.size(), is(1));
    }

    @Test
    public void andReturnsArgument() throws Exception {
        Object parent = new Object();
        MapBuilder<Object, String, Object> underTest = new MapBuilder<>(parent);

        assertSame(underTest.and(), parent);
    }
}