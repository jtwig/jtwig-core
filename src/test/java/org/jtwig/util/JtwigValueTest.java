package org.jtwig.util;

import org.hamcrest.CoreMatchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.internal.matchers.InstanceOf;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;

public class JtwigValueTest {
    private JtwigValue underTest;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void asCollectionWhenNull() throws Exception {
        underTest = new JtwigValue(null);
        Collection<Object> result = underTest.asCollection();

        assertThat(result, empty());
    }

    @Test
    public void asCollectionWhenIterable() throws Exception {
        List<String> list = asList("one");

        underTest = new JtwigValue((Iterable) list);
        Collection<Object> result = underTest.asCollection();

        assertThat(result, hasItem("one"));
    }

    @Test
    public void asCollectionWhenArray() throws Exception {
        underTest = new JtwigValue(new String[] { "test" });
        Collection<Object> result = underTest.asCollection();

        assertThat(result, hasItem("test"));
    }

    @Test
    public void asCollectionWhenMap() throws Exception {
        underTest = new JtwigValue(new HashMap<Object, Object>() {{ put("a", "b"); }});
        Collection<Object> result = underTest.asCollection();

        assertThat(result, hasItem("b"));
    }

    @Test
    public void asCollectionWhenSingleValue() throws Exception {
        underTest = new JtwigValue(1);
        Collection<Object> result = underTest.asCollection();

        assertThat(result, hasItem(1));
    }

    @Test
    public void asMapWhenNull() throws Exception {
        underTest = new JtwigValue(null);
        Map<Object, Object> result = underTest.asMap();

        assertThat(result.isEmpty(), is(true));
    }

    @Test
    public void asMapWhenSingleValue() throws Exception {
        underTest = new JtwigValue("ola");
        Map<Object, Object> result = underTest.asMap();

        assertThat(result.isEmpty(), is(false));
        assertThat(result.get(0), is((Object)"ola"));
    }

    @Test
    public void asNumberInvalid() throws Exception {
        expectedException.expect(IllegalArgumentException.class);

        underTest = new JtwigValue("a");
        underTest.asNumber();
    }

    @Test
    public void asMapWhenList() throws Exception {
        underTest = new JtwigValue(asList("one"));
        Map<Object, Object> result = underTest.asMap();

        assertThat(result.get(0), is((Object) "one"));
    }

    @Test
    public void isPresentWhenNull() throws Exception {
        underTest = new JtwigValue(null);
        boolean result = underTest.isPresent();
        assertThat(result, is(false));
    }

    @Test
    public void isPresentWhenNonNull() throws Exception {
        underTest = new JtwigValue(1);
        boolean result = underTest.isPresent();
        assertThat(result, is(true));
    }

    @Test
    public void asMapWhenArray() throws Exception {
        underTest = new JtwigValue(new Integer[]{1,2});
        Map<Object, Object> result = underTest.asMap();

        assertThat(result.get(0), is((Object) 1));
        assertThat(result.get(1), is((Object) 2));
    }

    @Test
    public void asMapWhenMap() throws Exception {
        underTest = new JtwigValue(new HashMap<Object, Object>() {{ put("a", "b"); }});
        Map<Object, Object> result = underTest.asMap();

        assertThat(result.get("a"), is((Object) "b"));
    }
}