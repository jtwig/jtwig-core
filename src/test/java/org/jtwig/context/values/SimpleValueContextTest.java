package org.jtwig.context.values;

import com.google.common.base.Optional;
import org.jtwig.reflection.model.Value;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;

public class SimpleValueContextTest {
    private final HashMap<String, Value> map = new HashMap<>();
    private SimpleValueContext underTest = new SimpleValueContext(map);

    @Before
    public void setUp() throws Exception {
        map.clear();
    }

    @Test
    public void addHappy() throws Exception {
        underTest.add("bluh", null);
        Optional<Value> result = underTest.value("bluh");

        assertThat(result.isPresent(), is(true));
        assertThat(result.get().getValue(), nullValue());
    }
}