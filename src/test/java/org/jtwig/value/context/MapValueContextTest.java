package org.jtwig.value.context;

import org.jtwig.value.Undefined;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertSame;

public class MapValueContextTest {
    private final HashMap<String, Object> map = new HashMap<>();
    private MapValueContext underTest = new MapValueContext(map);

    @Before
    public void setUp() throws Exception {
        map.clear();
    }

    @Test
    public void resolveUndefined() throws Exception {
        String key = "key";

        Object result = underTest.resolve(key);

        assertSame(Undefined.UNDEFINED, result);
    }

    @Test
    public void resolveNull() throws Exception {
        String key = "key";

        map.put(key, null);

        Object result = underTest.resolve(key);

        assertSame(null, result);
    }

    @Test
    public void resolveObject() throws Exception {
        String key = "key";
        Object object = new Object();

        map.put(key, object);

        Object result = underTest.resolve(key);

        assertSame(object, result);
    }

    @Test
    public void with() throws Exception {
        String key = "key";
        Object value = new Object();

        underTest.with(key, value);

        assertSame(value, map.get(key));
    }
}