package org.jtwig.util;

import org.jtwig.value.JtwigValue;
import org.jtwig.value.configuration.DefaultValueConfiguration;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.jtwig.value.JtwigValueFactory.value;
import static org.junit.Assert.assertThat;

public class JtwigValueTest {
    private JtwigValue underTest;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void asCollectionWhenNull() throws Exception {
        underTest = value(null, new DefaultValueConfiguration());
        Collection<Object> result = underTest.asCollection();

        assertThat(result.isEmpty(), is(true));
    }

    @Test
    public void asCollectionWhenIterable() throws Exception {
        List<String> list = asList("one");

        underTest = value((Iterable) list, new DefaultValueConfiguration());
        Collection<Object> result = underTest.asCollection();

        assertThat(result, hasItem("one"));
    }

    @Test
    public void asCollectionWhenArray() throws Exception {
        underTest = value(new String[]{"test"}, new DefaultValueConfiguration());
        Collection<Object> result = underTest.asCollection();

        assertThat(result, hasItem("test"));
    }

    @Test
    public void undefined() throws Exception {
        underTest = value(new Object(), new DefaultValueConfiguration());
        assertThat(underTest.isUndefined(), is(false));
    }

    @Test
    public void asStringWhenNull() throws Exception {
        underTest = value(null, new DefaultValueConfiguration());
        assertThat(underTest.asString(), is(""));
    }

    @Test
    public void asCollectionWhenMap() throws Exception {
        underTest = value(new HashMap<Object, Object>() {{
            put("a", "b");
        }}, new DefaultValueConfiguration());
        Collection<Object> result = underTest.asCollection();

        assertThat(result, hasItem("b"));
    }

    @Test
    public void asCollectionWhenSingleValue() throws Exception {
        underTest = value(1, new DefaultValueConfiguration());
        Collection<Object> result = underTest.asCollection();

        assertThat(result, hasItem(1));
    }

    @Test
    public void asMapWhenNull() throws Exception {
        underTest = value(null, new DefaultValueConfiguration());
        Map<Object, Object> result = underTest.asMap();

        assertThat(result.isEmpty(), is(true));
    }

    @Test
    public void asMapWhenSingleValue() throws Exception {
        underTest = value("ola", new DefaultValueConfiguration());
        Map<Object, Object> result = underTest.asMap();

        assertThat(result.isEmpty(), is(false));
        assertThat(result.get(0), is((Object) "ola"));
    }

    @Test
    public void mandatoryNumberInvalid() throws Exception {
        expectedException.expect(IllegalArgumentException.class);

        underTest = value("a", new DefaultValueConfiguration());
        underTest.mandatoryNumber();
    }

    @Test
    public void asMapWhenList() throws Exception {
        underTest = value(asList("one"), new DefaultValueConfiguration());
        Map<Object, Object> result = underTest.asMap();

        assertThat(result.get(0), is((Object) "one"));
    }

    @Test
    public void isPresentWhenNull() throws Exception {
        underTest = value(null, new DefaultValueConfiguration());
        boolean result = underTest.isPresent();
        assertThat(result, is(false));
    }

    @Test
    public void isPresentWhenNonNull() throws Exception {
        underTest = value(1, new DefaultValueConfiguration());
        boolean result = underTest.isPresent();
        assertThat(result, is(true));
    }

    @Test
    public void asMapWhenArray() throws Exception {
        underTest = value(new Integer[]{1, 2}, new DefaultValueConfiguration());
        Map<Object, Object> result = underTest.asMap();

        assertThat(result.get(0), is((Object) 1));
        assertThat(result.get(1), is((Object) 2));
    }

    @Test
    public void asMapWhenMap() throws Exception {
        underTest = value(new HashMap<Object, Object>() {{
            put("a", "b");
        }}, new DefaultValueConfiguration());
        Map<Object, Object> result = underTest.asMap();

        assertThat(result.get("a"), is((Object) "b"));
    }

    @Test
    public void equalComparator() throws Exception {
        assertThat(value(null, new DefaultValueConfiguration()).isEqualTo(value(true, new DefaultValueConfiguration())), is(false));
        assertThat(value(null, new DefaultValueConfiguration()).isEqualTo(value(false, new DefaultValueConfiguration())), is(true));
        assertThat(value(null, new DefaultValueConfiguration()).isEqualTo(value(1, new DefaultValueConfiguration())), is(false));
        assertThat(value(null, new DefaultValueConfiguration()).isEqualTo(value(0, new DefaultValueConfiguration())), is(true));
        assertThat(value(null, new DefaultValueConfiguration()).isEqualTo(value(-1, new DefaultValueConfiguration())), is(false));
        assertThat(value(null, new DefaultValueConfiguration()).isEqualTo(value("1", new DefaultValueConfiguration())), is(false));
        assertThat(value(null, new DefaultValueConfiguration()).isEqualTo(value("0", new DefaultValueConfiguration())), is(false));
        assertThat(value(null, new DefaultValueConfiguration()).isEqualTo(value("-1", new DefaultValueConfiguration())), is(false));
        assertThat(value(null, new DefaultValueConfiguration()).isEqualTo(value(null, new DefaultValueConfiguration())), is(true));
        assertThat(value(null, new DefaultValueConfiguration()).isEqualTo(value("lala", new DefaultValueConfiguration())), is(false));
        assertThat(value(null, new DefaultValueConfiguration()).isEqualTo(value("", new DefaultValueConfiguration())), is(true));
        assertThat(value(true, new DefaultValueConfiguration()).isEqualTo(value(true, new DefaultValueConfiguration())), is(true));
        assertThat(value(true, new DefaultValueConfiguration()).isEqualTo(value(false, new DefaultValueConfiguration())), is(false));
        assertThat(value(true, new DefaultValueConfiguration()).isEqualTo(value(1, new DefaultValueConfiguration())), is(true));
        assertThat(value(true, new DefaultValueConfiguration()).isEqualTo(value(0, new DefaultValueConfiguration())), is(false));
        assertThat(value(true, new DefaultValueConfiguration()).isEqualTo(value(-1, new DefaultValueConfiguration())), is(true));
        assertThat(value(true, new DefaultValueConfiguration()).isEqualTo(value("1", new DefaultValueConfiguration())), is(true));
        assertThat(value(true, new DefaultValueConfiguration()).isEqualTo(value("0", new DefaultValueConfiguration())), is(false));
        assertThat(value(true, new DefaultValueConfiguration()).isEqualTo(value("-1", new DefaultValueConfiguration())), is(true));
        assertThat(value(true, new DefaultValueConfiguration()).isEqualTo(value(null, new DefaultValueConfiguration())), is(false));
        assertThat(value(true, new DefaultValueConfiguration()).isEqualTo(value("lala", new DefaultValueConfiguration())), is(true));
        assertThat(value(true, new DefaultValueConfiguration()).isEqualTo(value("", new DefaultValueConfiguration())), is(false));
        assertThat(value(false, new DefaultValueConfiguration()).isEqualTo(value(true, new DefaultValueConfiguration())), is(false));
        assertThat(value(false, new DefaultValueConfiguration()).isEqualTo(value(false, new DefaultValueConfiguration())), is(true));
        assertThat(value(false, new DefaultValueConfiguration()).isEqualTo(value(1, new DefaultValueConfiguration())), is(false));
        assertThat(value(false, new DefaultValueConfiguration()).isEqualTo(value(0, new DefaultValueConfiguration())), is(true));
        assertThat(value(false, new DefaultValueConfiguration()).isEqualTo(value(-1, new DefaultValueConfiguration())), is(false));
        assertThat(value(false, new DefaultValueConfiguration()).isEqualTo(value("1", new DefaultValueConfiguration())), is(false));
        assertThat(value(false, new DefaultValueConfiguration()).isEqualTo(value("0", new DefaultValueConfiguration())), is(true));
        assertThat(value(false, new DefaultValueConfiguration()).isEqualTo(value("-1", new DefaultValueConfiguration())), is(false));
        assertThat(value(false, new DefaultValueConfiguration()).isEqualTo(value(null, new DefaultValueConfiguration())), is(true));
        assertThat(value(false, new DefaultValueConfiguration()).isEqualTo(value("lala", new DefaultValueConfiguration())), is(false));
        assertThat(value(false, new DefaultValueConfiguration()).isEqualTo(value("", new DefaultValueConfiguration())), is(true));
        assertThat(value(1, new DefaultValueConfiguration()).isEqualTo(value(true, new DefaultValueConfiguration())), is(true));
        assertThat(value(1, new DefaultValueConfiguration()).isEqualTo(value(false, new DefaultValueConfiguration())), is(false));
        assertThat(value(1, new DefaultValueConfiguration()).isEqualTo(value(1, new DefaultValueConfiguration())), is(true));
        assertThat(value(1, new DefaultValueConfiguration()).isEqualTo(value(0, new DefaultValueConfiguration())), is(false));
        assertThat(value(1, new DefaultValueConfiguration()).isEqualTo(value(-1, new DefaultValueConfiguration())), is(false));
        assertThat(value(1, new DefaultValueConfiguration()).isEqualTo(value("1", new DefaultValueConfiguration())), is(true));
        assertThat(value(1, new DefaultValueConfiguration()).isEqualTo(value("0", new DefaultValueConfiguration())), is(false));
        assertThat(value(1, new DefaultValueConfiguration()).isEqualTo(value("-1", new DefaultValueConfiguration())), is(false));
        assertThat(value(1, new DefaultValueConfiguration()).isEqualTo(value(null, new DefaultValueConfiguration())), is(false));
        assertThat(value(1, new DefaultValueConfiguration()).isEqualTo(value("lala", new DefaultValueConfiguration())), is(false));
        assertThat(value(1, new DefaultValueConfiguration()).isEqualTo(value("", new DefaultValueConfiguration())), is(false));
        assertThat(value(0, new DefaultValueConfiguration()).isEqualTo(value(true, new DefaultValueConfiguration())), is(false));
        assertThat(value(0, new DefaultValueConfiguration()).isEqualTo(value(false, new DefaultValueConfiguration())), is(true));
        assertThat(value(0, new DefaultValueConfiguration()).isEqualTo(value(1, new DefaultValueConfiguration())), is(false));
        assertThat(value(0, new DefaultValueConfiguration()).isEqualTo(value(0, new DefaultValueConfiguration())), is(true));
        assertThat(value(0, new DefaultValueConfiguration()).isEqualTo(value(-1, new DefaultValueConfiguration())), is(false));
        assertThat(value(0, new DefaultValueConfiguration()).isEqualTo(value("1", new DefaultValueConfiguration())), is(false));
        assertThat(value(0, new DefaultValueConfiguration()).isEqualTo(value("0", new DefaultValueConfiguration())), is(true));
        assertThat(value(0, new DefaultValueConfiguration()).isEqualTo(value("-1", new DefaultValueConfiguration())), is(false));
        assertThat(value(0, new DefaultValueConfiguration()).isEqualTo(value(null, new DefaultValueConfiguration())), is(true));
        assertThat(value(0, new DefaultValueConfiguration()).isEqualTo(value("lala", new DefaultValueConfiguration())), is(true));
        assertThat(value(0, new DefaultValueConfiguration()).isEqualTo(value("", new DefaultValueConfiguration())), is(true));
        assertThat(value(-1, new DefaultValueConfiguration()).isEqualTo(value(true, new DefaultValueConfiguration())), is(true));
        assertThat(value(-1, new DefaultValueConfiguration()).isEqualTo(value(false, new DefaultValueConfiguration())), is(false));
        assertThat(value(-1, new DefaultValueConfiguration()).isEqualTo(value(1, new DefaultValueConfiguration())), is(false));
        assertThat(value(-1, new DefaultValueConfiguration()).isEqualTo(value(0, new DefaultValueConfiguration())), is(false));
        assertThat(value(-1, new DefaultValueConfiguration()).isEqualTo(value(-1, new DefaultValueConfiguration())), is(true));
        assertThat(value(-1, new DefaultValueConfiguration()).isEqualTo(value("1", new DefaultValueConfiguration())), is(false));
        assertThat(value(-1, new DefaultValueConfiguration()).isEqualTo(value("0", new DefaultValueConfiguration())), is(false));
        assertThat(value(-1, new DefaultValueConfiguration()).isEqualTo(value("-1", new DefaultValueConfiguration())), is(true));
        assertThat(value(-1, new DefaultValueConfiguration()).isEqualTo(value(null, new DefaultValueConfiguration())), is(false));
        assertThat(value(-1, new DefaultValueConfiguration()).isEqualTo(value("lala", new DefaultValueConfiguration())), is(false));
        assertThat(value(-1, new DefaultValueConfiguration()).isEqualTo(value("", new DefaultValueConfiguration())), is(false));
        assertThat(value("", new DefaultValueConfiguration()).isEqualTo(value(true, new DefaultValueConfiguration())), is(false));
        assertThat(value("", new DefaultValueConfiguration()).isEqualTo(value(false, new DefaultValueConfiguration())), is(true));
        assertThat(value("", new DefaultValueConfiguration()).isEqualTo(value(1, new DefaultValueConfiguration())), is(false));
        assertThat(value("", new DefaultValueConfiguration()).isEqualTo(value(0, new DefaultValueConfiguration())), is(true));
        assertThat(value("", new DefaultValueConfiguration()).isEqualTo(value(-1, new DefaultValueConfiguration())), is(false));
        assertThat(value("", new DefaultValueConfiguration()).isEqualTo(value("1", new DefaultValueConfiguration())), is(false));
        assertThat(value("", new DefaultValueConfiguration()).isEqualTo(value("0", new DefaultValueConfiguration())), is(false));
        assertThat(value("", new DefaultValueConfiguration()).isEqualTo(value("-1", new DefaultValueConfiguration())), is(false));
        assertThat(value("", new DefaultValueConfiguration()).isEqualTo(value(null, new DefaultValueConfiguration())), is(true));
        assertThat(value("", new DefaultValueConfiguration()).isEqualTo(value("lala", new DefaultValueConfiguration())), is(false));
        assertThat(value("", new DefaultValueConfiguration()).isEqualTo(value("", new DefaultValueConfiguration())), is(true));
        assertThat(value("1", new DefaultValueConfiguration()).isEqualTo(value(true, new DefaultValueConfiguration())), is(true));
        assertThat(value("1", new DefaultValueConfiguration()).isEqualTo(value(false, new DefaultValueConfiguration())), is(false));
        assertThat(value("1", new DefaultValueConfiguration()).isEqualTo(value(1, new DefaultValueConfiguration())), is(true));
        assertThat(value("1", new DefaultValueConfiguration()).isEqualTo(value(0, new DefaultValueConfiguration())), is(false));
        assertThat(value("1", new DefaultValueConfiguration()).isEqualTo(value(-1, new DefaultValueConfiguration())), is(false));
        assertThat(value("1", new DefaultValueConfiguration()).isEqualTo(value("1", new DefaultValueConfiguration())), is(true));
        assertThat(value("1", new DefaultValueConfiguration()).isEqualTo(value("0", new DefaultValueConfiguration())), is(false));
        assertThat(value("1", new DefaultValueConfiguration()).isEqualTo(value("-1", new DefaultValueConfiguration())), is(false));
        assertThat(value("1", new DefaultValueConfiguration()).isEqualTo(value(null, new DefaultValueConfiguration())), is(false));
        assertThat(value("1", new DefaultValueConfiguration()).isEqualTo(value("lala", new DefaultValueConfiguration())), is(false));
        assertThat(value("1", new DefaultValueConfiguration()).isEqualTo(value("", new DefaultValueConfiguration())), is(false));
        assertThat(value("0", new DefaultValueConfiguration()).isEqualTo(value(true, new DefaultValueConfiguration())), is(false));
        assertThat(value("0", new DefaultValueConfiguration()).isEqualTo(value(false, new DefaultValueConfiguration())), is(true));
        assertThat(value("0", new DefaultValueConfiguration()).isEqualTo(value(1, new DefaultValueConfiguration())), is(false));
        assertThat(value("0", new DefaultValueConfiguration()).isEqualTo(value(0, new DefaultValueConfiguration())), is(true));
        assertThat(value("0", new DefaultValueConfiguration()).isEqualTo(value(-1, new DefaultValueConfiguration())), is(false));
        assertThat(value("0", new DefaultValueConfiguration()).isEqualTo(value("1", new DefaultValueConfiguration())), is(false));
        assertThat(value("0", new DefaultValueConfiguration()).isEqualTo(value("0", new DefaultValueConfiguration())), is(true));
        assertThat(value("0", new DefaultValueConfiguration()).isEqualTo(value("-1", new DefaultValueConfiguration())), is(false));
        assertThat(value("0", new DefaultValueConfiguration()).isEqualTo(value(null, new DefaultValueConfiguration())), is(false));
        assertThat(value("0", new DefaultValueConfiguration()).isEqualTo(value("lala", new DefaultValueConfiguration())), is(false));
        assertThat(value("0", new DefaultValueConfiguration()).isEqualTo(value("", new DefaultValueConfiguration())), is(false));
        assertThat(value("-1", new DefaultValueConfiguration()).isEqualTo(value(true, new DefaultValueConfiguration())), is(true));
        assertThat(value("-1", new DefaultValueConfiguration()).isEqualTo(value(false, new DefaultValueConfiguration())), is(false));
        assertThat(value("-1", new DefaultValueConfiguration()).isEqualTo(value(1, new DefaultValueConfiguration())), is(false));
        assertThat(value("-1", new DefaultValueConfiguration()).isEqualTo(value(0, new DefaultValueConfiguration())), is(false));
        assertThat(value("-1", new DefaultValueConfiguration()).isEqualTo(value(-1, new DefaultValueConfiguration())), is(true));
        assertThat(value("-1", new DefaultValueConfiguration()).isEqualTo(value("1", new DefaultValueConfiguration())), is(false));
        assertThat(value("-1", new DefaultValueConfiguration()).isEqualTo(value("0", new DefaultValueConfiguration())), is(false));
        assertThat(value("-1", new DefaultValueConfiguration()).isEqualTo(value("-1", new DefaultValueConfiguration())), is(true));
        assertThat(value("-1", new DefaultValueConfiguration()).isEqualTo(value(null, new DefaultValueConfiguration())), is(false));
        assertThat(value("-1", new DefaultValueConfiguration()).isEqualTo(value("lala", new DefaultValueConfiguration())), is(false));
        assertThat(value("-1", new DefaultValueConfiguration()).isEqualTo(value("", new DefaultValueConfiguration())), is(false));
        assertThat(value("lala", new DefaultValueConfiguration()).isEqualTo(value(true, new DefaultValueConfiguration())), is(true));
        assertThat(value("lala", new DefaultValueConfiguration()).isEqualTo(value(false, new DefaultValueConfiguration())), is(false));
        assertThat(value("lala", new DefaultValueConfiguration()).isEqualTo(value(1, new DefaultValueConfiguration())), is(false));
        assertThat(value("lala", new DefaultValueConfiguration()).isEqualTo(value(0, new DefaultValueConfiguration())), is(true));
        assertThat(value("lala", new DefaultValueConfiguration()).isEqualTo(value(-1, new DefaultValueConfiguration())), is(false));
        assertThat(value("lala", new DefaultValueConfiguration()).isEqualTo(value("1", new DefaultValueConfiguration())), is(false));
        assertThat(value("lala", new DefaultValueConfiguration()).isEqualTo(value("0", new DefaultValueConfiguration())), is(false));
        assertThat(value("lala", new DefaultValueConfiguration()).isEqualTo(value("-1", new DefaultValueConfiguration())), is(false));
        assertThat(value("lala", new DefaultValueConfiguration()).isEqualTo(value(null, new DefaultValueConfiguration())), is(false));
        assertThat(value("lala", new DefaultValueConfiguration()).isEqualTo(value("lala", new DefaultValueConfiguration())), is(true));
        assertThat(value("lala", new DefaultValueConfiguration()).isEqualTo(value("lalaa", new DefaultValueConfiguration())), is(false));
        assertThat(value("lala", new DefaultValueConfiguration()).isEqualTo(value("", new DefaultValueConfiguration())), is(false));
    }

    @Test
    public void identical() throws Exception {
        assertThat(value(true, new DefaultValueConfiguration()).isIdenticalTo(value(true, new DefaultValueConfiguration())), is(true));
        assertThat(value(true, new DefaultValueConfiguration()).isIdenticalTo(value(false, new DefaultValueConfiguration())), is(false));
        assertThat(value(true, new DefaultValueConfiguration()).isIdenticalTo(value(1, new DefaultValueConfiguration())), is(false));
        assertThat(value(true, new DefaultValueConfiguration()).isIdenticalTo(value(0, new DefaultValueConfiguration())), is(false));
        assertThat(value(true, new DefaultValueConfiguration()).isIdenticalTo(value(-1, new DefaultValueConfiguration())), is(false));
        assertThat(value(true, new DefaultValueConfiguration()).isIdenticalTo(value("1", new DefaultValueConfiguration())), is(false));
        assertThat(value(true, new DefaultValueConfiguration()).isIdenticalTo(value("0", new DefaultValueConfiguration())), is(false));
        assertThat(value(true, new DefaultValueConfiguration()).isIdenticalTo(value("-1", new DefaultValueConfiguration())), is(false));
        assertThat(value(true, new DefaultValueConfiguration()).isIdenticalTo(value(null, new DefaultValueConfiguration())), is(false));
        assertThat(value(true, new DefaultValueConfiguration()).isIdenticalTo(value("lala", new DefaultValueConfiguration())), is(false));
        assertThat(value(true, new DefaultValueConfiguration()).isIdenticalTo(value("", new DefaultValueConfiguration())), is(false));
        assertThat(value(true, new DefaultValueConfiguration()).isIdenticalTo(value(1.2, new DefaultValueConfiguration())), is(false));
        assertThat(value(false, new DefaultValueConfiguration()).isIdenticalTo(value(true, new DefaultValueConfiguration())), is(false));
        assertThat(value(false, new DefaultValueConfiguration()).isIdenticalTo(value(false, new DefaultValueConfiguration())), is(true));
        assertThat(value(false, new DefaultValueConfiguration()).isIdenticalTo(value(1, new DefaultValueConfiguration())), is(false));
        assertThat(value(false, new DefaultValueConfiguration()).isIdenticalTo(value(0, new DefaultValueConfiguration())), is(false));
        assertThat(value(false, new DefaultValueConfiguration()).isIdenticalTo(value(-1, new DefaultValueConfiguration())), is(false));
        assertThat(value(false, new DefaultValueConfiguration()).isIdenticalTo(value("1", new DefaultValueConfiguration())), is(false));
        assertThat(value(false, new DefaultValueConfiguration()).isIdenticalTo(value("0", new DefaultValueConfiguration())), is(false));
        assertThat(value(false, new DefaultValueConfiguration()).isIdenticalTo(value("-1", new DefaultValueConfiguration())), is(false));
        assertThat(value(false, new DefaultValueConfiguration()).isIdenticalTo(value(null, new DefaultValueConfiguration())), is(false));
        assertThat(value(false, new DefaultValueConfiguration()).isIdenticalTo(value("lala", new DefaultValueConfiguration())), is(false));
        assertThat(value(false, new DefaultValueConfiguration()).isIdenticalTo(value("", new DefaultValueConfiguration())), is(false));
        assertThat(value(false, new DefaultValueConfiguration()).isIdenticalTo(value(1.2, new DefaultValueConfiguration())), is(false));
        assertThat(value(1, new DefaultValueConfiguration()).isIdenticalTo(value(true, new DefaultValueConfiguration())), is(false));
        assertThat(value(1, new DefaultValueConfiguration()).isIdenticalTo(value(false, new DefaultValueConfiguration())), is(false));
        assertThat(value(1, new DefaultValueConfiguration()).isIdenticalTo(value(1, new DefaultValueConfiguration())), is(true));
        assertThat(value(1, new DefaultValueConfiguration()).isIdenticalTo(value(0, new DefaultValueConfiguration())), is(false));
        assertThat(value(1, new DefaultValueConfiguration()).isIdenticalTo(value(-1, new DefaultValueConfiguration())), is(false));
        assertThat(value(1, new DefaultValueConfiguration()).isIdenticalTo(value("1", new DefaultValueConfiguration())), is(false));
        assertThat(value(1, new DefaultValueConfiguration()).isIdenticalTo(value("0", new DefaultValueConfiguration())), is(false));
        assertThat(value(1, new DefaultValueConfiguration()).isIdenticalTo(value("-1", new DefaultValueConfiguration())), is(false));
        assertThat(value(1, new DefaultValueConfiguration()).isIdenticalTo(value(null, new DefaultValueConfiguration())), is(false));
        assertThat(value(1, new DefaultValueConfiguration()).isIdenticalTo(value("lala", new DefaultValueConfiguration())), is(false));
        assertThat(value(1, new DefaultValueConfiguration()).isIdenticalTo(value("", new DefaultValueConfiguration())), is(false));
        assertThat(value(1, new DefaultValueConfiguration()).isIdenticalTo(value(1.2, new DefaultValueConfiguration())), is(false));
        assertThat(value(0, new DefaultValueConfiguration()).isIdenticalTo(value(true, new DefaultValueConfiguration())), is(false));
        assertThat(value(0, new DefaultValueConfiguration()).isIdenticalTo(value(false, new DefaultValueConfiguration())), is(false));
        assertThat(value(0, new DefaultValueConfiguration()).isIdenticalTo(value(1, new DefaultValueConfiguration())), is(false));
        assertThat(value(0, new DefaultValueConfiguration()).isIdenticalTo(value(0, new DefaultValueConfiguration())), is(true));
        assertThat(value(0, new DefaultValueConfiguration()).isIdenticalTo(value(-1, new DefaultValueConfiguration())), is(false));
        assertThat(value(0, new DefaultValueConfiguration()).isIdenticalTo(value("1", new DefaultValueConfiguration())), is(false));
        assertThat(value(0, new DefaultValueConfiguration()).isIdenticalTo(value("0", new DefaultValueConfiguration())), is(false));
        assertThat(value(0, new DefaultValueConfiguration()).isIdenticalTo(value("-1", new DefaultValueConfiguration())), is(false));
        assertThat(value(0, new DefaultValueConfiguration()).isIdenticalTo(value(null, new DefaultValueConfiguration())), is(false));
        assertThat(value(0, new DefaultValueConfiguration()).isIdenticalTo(value("lala", new DefaultValueConfiguration())), is(false));
        assertThat(value(0, new DefaultValueConfiguration()).isIdenticalTo(value("", new DefaultValueConfiguration())), is(false));
        assertThat(value(0, new DefaultValueConfiguration()).isIdenticalTo(value(1.2, new DefaultValueConfiguration())), is(false));
        assertThat(value(-1, new DefaultValueConfiguration()).isIdenticalTo(value(true, new DefaultValueConfiguration())), is(false));
        assertThat(value(-1, new DefaultValueConfiguration()).isIdenticalTo(value(false, new DefaultValueConfiguration())), is(false));
        assertThat(value(-1, new DefaultValueConfiguration()).isIdenticalTo(value(1, new DefaultValueConfiguration())), is(false));
        assertThat(value(-1, new DefaultValueConfiguration()).isIdenticalTo(value(0, new DefaultValueConfiguration())), is(false));
        assertThat(value(-1, new DefaultValueConfiguration()).isIdenticalTo(value(-1, new DefaultValueConfiguration())), is(true));
        assertThat(value(-1, new DefaultValueConfiguration()).isIdenticalTo(value("1", new DefaultValueConfiguration())), is(false));
        assertThat(value(-1, new DefaultValueConfiguration()).isIdenticalTo(value("0", new DefaultValueConfiguration())), is(false));
        assertThat(value(-1, new DefaultValueConfiguration()).isIdenticalTo(value("-1", new DefaultValueConfiguration())), is(false));
        assertThat(value(-1, new DefaultValueConfiguration()).isIdenticalTo(value(null, new DefaultValueConfiguration())), is(false));
        assertThat(value(-1, new DefaultValueConfiguration()).isIdenticalTo(value("lala", new DefaultValueConfiguration())), is(false));
        assertThat(value(-1, new DefaultValueConfiguration()).isIdenticalTo(value("", new DefaultValueConfiguration())), is(false));
        assertThat(value(-1, new DefaultValueConfiguration()).isIdenticalTo(value(1.2, new DefaultValueConfiguration())), is(false));
        assertThat(value("1", new DefaultValueConfiguration()).isIdenticalTo(value(true, new DefaultValueConfiguration())), is(false));
        assertThat(value("1", new DefaultValueConfiguration()).isIdenticalTo(value(false, new DefaultValueConfiguration())), is(false));
        assertThat(value("1", new DefaultValueConfiguration()).isIdenticalTo(value(1, new DefaultValueConfiguration())), is(false));
        assertThat(value("1", new DefaultValueConfiguration()).isIdenticalTo(value(0, new DefaultValueConfiguration())), is(false));
        assertThat(value("1", new DefaultValueConfiguration()).isIdenticalTo(value(-1, new DefaultValueConfiguration())), is(false));
        assertThat(value("1", new DefaultValueConfiguration()).isIdenticalTo(value("1", new DefaultValueConfiguration())), is(true));
        assertThat(value("1", new DefaultValueConfiguration()).isIdenticalTo(value("0", new DefaultValueConfiguration())), is(false));
        assertThat(value("1", new DefaultValueConfiguration()).isIdenticalTo(value("-1", new DefaultValueConfiguration())), is(false));
        assertThat(value("1", new DefaultValueConfiguration()).isIdenticalTo(value(null, new DefaultValueConfiguration())), is(false));
        assertThat(value("1", new DefaultValueConfiguration()).isIdenticalTo(value("lala", new DefaultValueConfiguration())), is(false));
        assertThat(value("1", new DefaultValueConfiguration()).isIdenticalTo(value("", new DefaultValueConfiguration())), is(false));
        assertThat(value("1", new DefaultValueConfiguration()).isIdenticalTo(value(1.2, new DefaultValueConfiguration())), is(false));
        assertThat(value("0", new DefaultValueConfiguration()).isIdenticalTo(value(true, new DefaultValueConfiguration())), is(false));
        assertThat(value("0", new DefaultValueConfiguration()).isIdenticalTo(value(false, new DefaultValueConfiguration())), is(false));
        assertThat(value("0", new DefaultValueConfiguration()).isIdenticalTo(value(1, new DefaultValueConfiguration())), is(false));
        assertThat(value("0", new DefaultValueConfiguration()).isIdenticalTo(value(0, new DefaultValueConfiguration())), is(false));
        assertThat(value("0", new DefaultValueConfiguration()).isIdenticalTo(value(-1, new DefaultValueConfiguration())), is(false));
        assertThat(value("0", new DefaultValueConfiguration()).isIdenticalTo(value("1", new DefaultValueConfiguration())), is(false));
        assertThat(value("0", new DefaultValueConfiguration()).isIdenticalTo(value("0", new DefaultValueConfiguration())), is(true));
        assertThat(value("0", new DefaultValueConfiguration()).isIdenticalTo(value("-1", new DefaultValueConfiguration())), is(false));
        assertThat(value("0", new DefaultValueConfiguration()).isIdenticalTo(value(null, new DefaultValueConfiguration())), is(false));
        assertThat(value("0", new DefaultValueConfiguration()).isIdenticalTo(value("lala", new DefaultValueConfiguration())), is(false));
        assertThat(value("0", new DefaultValueConfiguration()).isIdenticalTo(value("", new DefaultValueConfiguration())), is(false));
        assertThat(value("0", new DefaultValueConfiguration()).isIdenticalTo(value(1.2, new DefaultValueConfiguration())), is(false));
        assertThat(value("-1", new DefaultValueConfiguration()).isIdenticalTo(value(true, new DefaultValueConfiguration())), is(false));
        assertThat(value("-1", new DefaultValueConfiguration()).isIdenticalTo(value(false, new DefaultValueConfiguration())), is(false));
        assertThat(value("-1", new DefaultValueConfiguration()).isIdenticalTo(value(1, new DefaultValueConfiguration())), is(false));
        assertThat(value("-1", new DefaultValueConfiguration()).isIdenticalTo(value(0, new DefaultValueConfiguration())), is(false));
        assertThat(value("-1", new DefaultValueConfiguration()).isIdenticalTo(value(-1, new DefaultValueConfiguration())), is(false));
        assertThat(value("-1", new DefaultValueConfiguration()).isIdenticalTo(value("1", new DefaultValueConfiguration())), is(false));
        assertThat(value("-1", new DefaultValueConfiguration()).isIdenticalTo(value("0", new DefaultValueConfiguration())), is(false));
        assertThat(value("-1", new DefaultValueConfiguration()).isIdenticalTo(value("-1", new DefaultValueConfiguration())), is(true));
        assertThat(value("-1", new DefaultValueConfiguration()).isIdenticalTo(value(null, new DefaultValueConfiguration())), is(false));
        assertThat(value("-1", new DefaultValueConfiguration()).isIdenticalTo(value("lala", new DefaultValueConfiguration())), is(false));
        assertThat(value("-1", new DefaultValueConfiguration()).isIdenticalTo(value("", new DefaultValueConfiguration())), is(false));
        assertThat(value("-1", new DefaultValueConfiguration()).isIdenticalTo(value(1.2, new DefaultValueConfiguration())), is(false));
        assertThat(value(null, new DefaultValueConfiguration()).isIdenticalTo(value(true, new DefaultValueConfiguration())), is(false));
        assertThat(value(null, new DefaultValueConfiguration()).isIdenticalTo(value(false, new DefaultValueConfiguration())), is(false));
        assertThat(value(null, new DefaultValueConfiguration()).isIdenticalTo(value(1, new DefaultValueConfiguration())), is(false));
        assertThat(value(null, new DefaultValueConfiguration()).isIdenticalTo(value(0, new DefaultValueConfiguration())), is(false));
        assertThat(value(null, new DefaultValueConfiguration()).isIdenticalTo(value(-1, new DefaultValueConfiguration())), is(false));
        assertThat(value(null, new DefaultValueConfiguration()).isIdenticalTo(value("1", new DefaultValueConfiguration())), is(false));
        assertThat(value(null, new DefaultValueConfiguration()).isIdenticalTo(value("0", new DefaultValueConfiguration())), is(false));
        assertThat(value(null, new DefaultValueConfiguration()).isIdenticalTo(value("-1", new DefaultValueConfiguration())), is(false));
        assertThat(value(null, new DefaultValueConfiguration()).isIdenticalTo(value(null, new DefaultValueConfiguration())), is(true));
        assertThat(value(null, new DefaultValueConfiguration()).isIdenticalTo(value("lala", new DefaultValueConfiguration())), is(false));
        assertThat(value(null, new DefaultValueConfiguration()).isIdenticalTo(value("", new DefaultValueConfiguration())), is(false));
        assertThat(value(null, new DefaultValueConfiguration()).isIdenticalTo(value(1.2, new DefaultValueConfiguration())), is(false));
        assertThat(value("lala", new DefaultValueConfiguration()).isIdenticalTo(value(true, new DefaultValueConfiguration())), is(false));
        assertThat(value("lala", new DefaultValueConfiguration()).isIdenticalTo(value(false, new DefaultValueConfiguration())), is(false));
        assertThat(value("lala", new DefaultValueConfiguration()).isIdenticalTo(value(1, new DefaultValueConfiguration())), is(false));
        assertThat(value("lala", new DefaultValueConfiguration()).isIdenticalTo(value(0, new DefaultValueConfiguration())), is(false));
        assertThat(value("lala", new DefaultValueConfiguration()).isIdenticalTo(value(-1, new DefaultValueConfiguration())), is(false));
        assertThat(value("lala", new DefaultValueConfiguration()).isIdenticalTo(value("1", new DefaultValueConfiguration())), is(false));
        assertThat(value("lala", new DefaultValueConfiguration()).isIdenticalTo(value("0", new DefaultValueConfiguration())), is(false));
        assertThat(value("lala", new DefaultValueConfiguration()).isIdenticalTo(value("-1", new DefaultValueConfiguration())), is(false));
        assertThat(value("lala", new DefaultValueConfiguration()).isIdenticalTo(value(null, new DefaultValueConfiguration())), is(false));
        assertThat(value("lala", new DefaultValueConfiguration()).isIdenticalTo(value("lala", new DefaultValueConfiguration())), is(true));
        assertThat(value("lala", new DefaultValueConfiguration()).isIdenticalTo(value("lalaa", new DefaultValueConfiguration())), is(false));
        assertThat(value("lala", new DefaultValueConfiguration()).isIdenticalTo(value("", new DefaultValueConfiguration())), is(false));
        assertThat(value("lala", new DefaultValueConfiguration()).isIdenticalTo(value(1.2, new DefaultValueConfiguration())), is(false));
        assertThat(value("", new DefaultValueConfiguration()).isIdenticalTo(value(true, new DefaultValueConfiguration())), is(false));
        assertThat(value("", new DefaultValueConfiguration()).isIdenticalTo(value(false, new DefaultValueConfiguration())), is(false));
        assertThat(value("", new DefaultValueConfiguration()).isIdenticalTo(value(1, new DefaultValueConfiguration())), is(false));
        assertThat(value("", new DefaultValueConfiguration()).isIdenticalTo(value(0, new DefaultValueConfiguration())), is(false));
        assertThat(value("", new DefaultValueConfiguration()).isIdenticalTo(value(-1, new DefaultValueConfiguration())), is(false));
        assertThat(value("", new DefaultValueConfiguration()).isIdenticalTo(value("1", new DefaultValueConfiguration())), is(false));
        assertThat(value("", new DefaultValueConfiguration()).isIdenticalTo(value("0", new DefaultValueConfiguration())), is(false));
        assertThat(value("", new DefaultValueConfiguration()).isIdenticalTo(value("-1", new DefaultValueConfiguration())), is(false));
        assertThat(value("", new DefaultValueConfiguration()).isIdenticalTo(value(null, new DefaultValueConfiguration())), is(false));
        assertThat(value("", new DefaultValueConfiguration()).isIdenticalTo(value("lala", new DefaultValueConfiguration())), is(false));
        assertThat(value("", new DefaultValueConfiguration()).isIdenticalTo(value("", new DefaultValueConfiguration())), is(true));
        assertThat(value("", new DefaultValueConfiguration()).isIdenticalTo(value(1.2, new DefaultValueConfiguration())), is(false));
        assertThat(value(1.2, new DefaultValueConfiguration()).isIdenticalTo(value(true, new DefaultValueConfiguration())), is(false));
        assertThat(value(1.2, new DefaultValueConfiguration()).isIdenticalTo(value(false, new DefaultValueConfiguration())), is(false));
        assertThat(value(1.2, new DefaultValueConfiguration()).isIdenticalTo(value(1, new DefaultValueConfiguration())), is(false));
        assertThat(value(1.2, new DefaultValueConfiguration()).isIdenticalTo(value(0, new DefaultValueConfiguration())), is(false));
        assertThat(value(1.2, new DefaultValueConfiguration()).isIdenticalTo(value(-1, new DefaultValueConfiguration())), is(false));
        assertThat(value(1.2, new DefaultValueConfiguration()).isIdenticalTo(value("1", new DefaultValueConfiguration())), is(false));
        assertThat(value(1.2, new DefaultValueConfiguration()).isIdenticalTo(value("0", new DefaultValueConfiguration())), is(false));
        assertThat(value(1.2, new DefaultValueConfiguration()).isIdenticalTo(value("-1", new DefaultValueConfiguration())), is(false));
        assertThat(value(1.2, new DefaultValueConfiguration()).isIdenticalTo(value(null, new DefaultValueConfiguration())), is(false));
        assertThat(value(1.2, new DefaultValueConfiguration()).isIdenticalTo(value("lala", new DefaultValueConfiguration())), is(false));
        assertThat(value(1.2, new DefaultValueConfiguration()).isIdenticalTo(value("", new DefaultValueConfiguration())), is(false));
        assertThat(value(1.2, new DefaultValueConfiguration()).isIdenticalTo(value(1.2, new DefaultValueConfiguration())), is(true));
    }

    @Test
    public void lower() throws Exception {
        assertThat(value(true, new DefaultValueConfiguration()).isLowerThan(value(true, new DefaultValueConfiguration())), is(false));
        assertThat(value(true, new DefaultValueConfiguration()).isLowerThan(value(false, new DefaultValueConfiguration())), is(false));
        assertThat(value(true, new DefaultValueConfiguration()).isLowerThan(value(1, new DefaultValueConfiguration())), is(false));
        assertThat(value(true, new DefaultValueConfiguration()).isLowerThan(value(0, new DefaultValueConfiguration())), is(false));
        assertThat(value(true, new DefaultValueConfiguration()).isLowerThan(value(-1, new DefaultValueConfiguration())), is(false));
        assertThat(value(true, new DefaultValueConfiguration()).isLowerThan(value("1", new DefaultValueConfiguration())), is(false));
        assertThat(value(true, new DefaultValueConfiguration()).isLowerThan(value("0", new DefaultValueConfiguration())), is(false));
        assertThat(value(true, new DefaultValueConfiguration()).isLowerThan(value("-1", new DefaultValueConfiguration())), is(false));
        assertThat(value(true, new DefaultValueConfiguration()).isLowerThan(value(null, new DefaultValueConfiguration())), is(false));
        assertThat(value(true, new DefaultValueConfiguration()).isLowerThan(value("lala", new DefaultValueConfiguration())), is(false));
        assertThat(value(true, new DefaultValueConfiguration()).isLowerThan(value("", new DefaultValueConfiguration())), is(false));
        assertThat(value(true, new DefaultValueConfiguration()).isLowerThan(value(1.2, new DefaultValueConfiguration())), is(false));
        assertThat(value(false, new DefaultValueConfiguration()).isLowerThan(value(true, new DefaultValueConfiguration())), is(true));
        assertThat(value(false, new DefaultValueConfiguration()).isLowerThan(value(false, new DefaultValueConfiguration())), is(false));
        assertThat(value(false, new DefaultValueConfiguration()).isLowerThan(value(1, new DefaultValueConfiguration())), is(true));
        assertThat(value(false, new DefaultValueConfiguration()).isLowerThan(value(0, new DefaultValueConfiguration())), is(false));
        assertThat(value(false, new DefaultValueConfiguration()).isLowerThan(value(-1, new DefaultValueConfiguration())), is(true));
        assertThat(value(false, new DefaultValueConfiguration()).isLowerThan(value("1", new DefaultValueConfiguration())), is(true));
        assertThat(value(false, new DefaultValueConfiguration()).isLowerThan(value("0", new DefaultValueConfiguration())), is(false));
        assertThat(value(false, new DefaultValueConfiguration()).isLowerThan(value("-1", new DefaultValueConfiguration())), is(true));
        assertThat(value(false, new DefaultValueConfiguration()).isLowerThan(value(null, new DefaultValueConfiguration())), is(false));
        assertThat(value(false, new DefaultValueConfiguration()).isLowerThan(value("lala", new DefaultValueConfiguration())), is(true));
        assertThat(value(false, new DefaultValueConfiguration()).isLowerThan(value("", new DefaultValueConfiguration())), is(false));
        assertThat(value(false, new DefaultValueConfiguration()).isLowerThan(value(1.2, new DefaultValueConfiguration())), is(true));
        assertThat(value(1, new DefaultValueConfiguration()).isLowerThan(value(true, new DefaultValueConfiguration())), is(false));
        assertThat(value(1, new DefaultValueConfiguration()).isLowerThan(value(false, new DefaultValueConfiguration())), is(false));
        assertThat(value(1, new DefaultValueConfiguration()).isLowerThan(value(1, new DefaultValueConfiguration())), is(false));
        assertThat(value(1, new DefaultValueConfiguration()).isLowerThan(value(0, new DefaultValueConfiguration())), is(false));
        assertThat(value(1, new DefaultValueConfiguration()).isLowerThan(value(-1, new DefaultValueConfiguration())), is(false));
        assertThat(value(1, new DefaultValueConfiguration()).isLowerThan(value("1", new DefaultValueConfiguration())), is(false));
        assertThat(value(1, new DefaultValueConfiguration()).isLowerThan(value("0", new DefaultValueConfiguration())), is(false));
        assertThat(value(1, new DefaultValueConfiguration()).isLowerThan(value("-1", new DefaultValueConfiguration())), is(false));
        assertThat(value(1, new DefaultValueConfiguration()).isLowerThan(value(null, new DefaultValueConfiguration())), is(false));
        assertThat(value(1, new DefaultValueConfiguration()).isLowerThan(value("lala", new DefaultValueConfiguration())), is(false));
        assertThat(value(1, new DefaultValueConfiguration()).isLowerThan(value("", new DefaultValueConfiguration())), is(false));
        assertThat(value(1, new DefaultValueConfiguration()).isLowerThan(value(1.2, new DefaultValueConfiguration())), is(true));
        assertThat(value(0, new DefaultValueConfiguration()).isLowerThan(value(true, new DefaultValueConfiguration())), is(true));
        assertThat(value(0, new DefaultValueConfiguration()).isLowerThan(value(false, new DefaultValueConfiguration())), is(false));
        assertThat(value(0, new DefaultValueConfiguration()).isLowerThan(value(1, new DefaultValueConfiguration())), is(true));
        assertThat(value(0, new DefaultValueConfiguration()).isLowerThan(value(0, new DefaultValueConfiguration())), is(false));
        assertThat(value(0, new DefaultValueConfiguration()).isLowerThan(value(-1, new DefaultValueConfiguration())), is(false));
        assertThat(value(0, new DefaultValueConfiguration()).isLowerThan(value("1", new DefaultValueConfiguration())), is(true));
        assertThat(value(0, new DefaultValueConfiguration()).isLowerThan(value("0", new DefaultValueConfiguration())), is(false));
        assertThat(value(0, new DefaultValueConfiguration()).isLowerThan(value("-1", new DefaultValueConfiguration())), is(false));
        assertThat(value(0, new DefaultValueConfiguration()).isLowerThan(value(null, new DefaultValueConfiguration())), is(false));
        assertThat(value(0, new DefaultValueConfiguration()).isLowerThan(value("lala", new DefaultValueConfiguration())), is(false));
        assertThat(value(0, new DefaultValueConfiguration()).isLowerThan(value("", new DefaultValueConfiguration())), is(false));
        assertThat(value(0, new DefaultValueConfiguration()).isLowerThan(value(1.2, new DefaultValueConfiguration())), is(true));
        assertThat(value(-1, new DefaultValueConfiguration()).isLowerThan(value(true, new DefaultValueConfiguration())), is(false));
        assertThat(value(-1, new DefaultValueConfiguration()).isLowerThan(value(false, new DefaultValueConfiguration())), is(false));
        assertThat(value(-1, new DefaultValueConfiguration()).isLowerThan(value(1, new DefaultValueConfiguration())), is(true));
        assertThat(value(-1, new DefaultValueConfiguration()).isLowerThan(value(0, new DefaultValueConfiguration())), is(true));
        assertThat(value(-1, new DefaultValueConfiguration()).isLowerThan(value(-1, new DefaultValueConfiguration())), is(false));
        assertThat(value(-1, new DefaultValueConfiguration()).isLowerThan(value("1", new DefaultValueConfiguration())), is(true));
        assertThat(value(-1, new DefaultValueConfiguration()).isLowerThan(value("0", new DefaultValueConfiguration())), is(true));
        assertThat(value(-1, new DefaultValueConfiguration()).isLowerThan(value("-1", new DefaultValueConfiguration())), is(false));
        assertThat(value(-1, new DefaultValueConfiguration()).isLowerThan(value(null, new DefaultValueConfiguration())), is(false));
        assertThat(value(-1, new DefaultValueConfiguration()).isLowerThan(value("lala", new DefaultValueConfiguration())), is(true));
        assertThat(value(-1, new DefaultValueConfiguration()).isLowerThan(value("", new DefaultValueConfiguration())), is(true));
        assertThat(value(-1, new DefaultValueConfiguration()).isLowerThan(value(1.2, new DefaultValueConfiguration())), is(true));
        assertThat(value("1", new DefaultValueConfiguration()).isLowerThan(value(true, new DefaultValueConfiguration())), is(false));
        assertThat(value("1", new DefaultValueConfiguration()).isLowerThan(value(false, new DefaultValueConfiguration())), is(false));
        assertThat(value("1", new DefaultValueConfiguration()).isLowerThan(value(1, new DefaultValueConfiguration())), is(false));
        assertThat(value("1", new DefaultValueConfiguration()).isLowerThan(value(0, new DefaultValueConfiguration())), is(false));
        assertThat(value("1", new DefaultValueConfiguration()).isLowerThan(value(-1, new DefaultValueConfiguration())), is(false));
        assertThat(value("1", new DefaultValueConfiguration()).isLowerThan(value("1", new DefaultValueConfiguration())), is(false));
        assertThat(value("1", new DefaultValueConfiguration()).isLowerThan(value("0", new DefaultValueConfiguration())), is(false));
        assertThat(value("1", new DefaultValueConfiguration()).isLowerThan(value("-1", new DefaultValueConfiguration())), is(false));
        assertThat(value("1", new DefaultValueConfiguration()).isLowerThan(value(null, new DefaultValueConfiguration())), is(false));
        assertThat(value("1", new DefaultValueConfiguration()).isLowerThan(value("lala", new DefaultValueConfiguration())), is(true));
        assertThat(value("1", new DefaultValueConfiguration()).isLowerThan(value("", new DefaultValueConfiguration())), is(false));
        assertThat(value("1", new DefaultValueConfiguration()).isLowerThan(value(1.2, new DefaultValueConfiguration())), is(true));
        assertThat(value("0", new DefaultValueConfiguration()).isLowerThan(value(true, new DefaultValueConfiguration())), is(true));
        assertThat(value("0", new DefaultValueConfiguration()).isLowerThan(value(false, new DefaultValueConfiguration())), is(false));
        assertThat(value("0", new DefaultValueConfiguration()).isLowerThan(value(1, new DefaultValueConfiguration())), is(true));
        assertThat(value("0", new DefaultValueConfiguration()).isLowerThan(value(0, new DefaultValueConfiguration())), is(false));
        assertThat(value("0", new DefaultValueConfiguration()).isLowerThan(value(-1, new DefaultValueConfiguration())), is(false));
        assertThat(value("0", new DefaultValueConfiguration()).isLowerThan(value("1", new DefaultValueConfiguration())), is(true));
        assertThat(value("0", new DefaultValueConfiguration()).isLowerThan(value("0", new DefaultValueConfiguration())), is(false));
        assertThat(value("0", new DefaultValueConfiguration()).isLowerThan(value("-1", new DefaultValueConfiguration())), is(false));
        assertThat(value("0", new DefaultValueConfiguration()).isLowerThan(value(null, new DefaultValueConfiguration())), is(false));
        assertThat(value("0", new DefaultValueConfiguration()).isLowerThan(value("lala", new DefaultValueConfiguration())), is(true));
        assertThat(value("0", new DefaultValueConfiguration()).isLowerThan(value("", new DefaultValueConfiguration())), is(false));
        assertThat(value("0", new DefaultValueConfiguration()).isLowerThan(value(1.2, new DefaultValueConfiguration())), is(true));
        assertThat(value("-1", new DefaultValueConfiguration()).isLowerThan(value(true, new DefaultValueConfiguration())), is(false));
        assertThat(value("-1", new DefaultValueConfiguration()).isLowerThan(value(false, new DefaultValueConfiguration())), is(false));
        assertThat(value("-1", new DefaultValueConfiguration()).isLowerThan(value(1, new DefaultValueConfiguration())), is(true));
        assertThat(value("-1", new DefaultValueConfiguration()).isLowerThan(value(0, new DefaultValueConfiguration())), is(true));
        assertThat(value("-1", new DefaultValueConfiguration()).isLowerThan(value(-1, new DefaultValueConfiguration())), is(false));
        assertThat(value("-1", new DefaultValueConfiguration()).isLowerThan(value("1", new DefaultValueConfiguration())), is(true));
        assertThat(value("-1", new DefaultValueConfiguration()).isLowerThan(value("0", new DefaultValueConfiguration())), is(true));
        assertThat(value("-1", new DefaultValueConfiguration()).isLowerThan(value("-1", new DefaultValueConfiguration())), is(false));
        assertThat(value("-1", new DefaultValueConfiguration()).isLowerThan(value(null, new DefaultValueConfiguration())), is(false));
        assertThat(value("-1", new DefaultValueConfiguration()).isLowerThan(value("lala", new DefaultValueConfiguration())), is(true));
        assertThat(value("-1", new DefaultValueConfiguration()).isLowerThan(value("", new DefaultValueConfiguration())), is(false));
        assertThat(value("-1", new DefaultValueConfiguration()).isLowerThan(value(1.2, new DefaultValueConfiguration())), is(true));
        assertThat(value(null, new DefaultValueConfiguration()).isLowerThan(value(true, new DefaultValueConfiguration())), is(true));
        assertThat(value(null, new DefaultValueConfiguration()).isLowerThan(value(false, new DefaultValueConfiguration())), is(false));
        assertThat(value(null, new DefaultValueConfiguration()).isLowerThan(value(1, new DefaultValueConfiguration())), is(true));
        assertThat(value(null, new DefaultValueConfiguration()).isLowerThan(value(0, new DefaultValueConfiguration())), is(false));
        assertThat(value(null, new DefaultValueConfiguration()).isLowerThan(value(-1, new DefaultValueConfiguration())), is(true));
        assertThat(value(null, new DefaultValueConfiguration()).isLowerThan(value("1", new DefaultValueConfiguration())), is(true));
        assertThat(value(null, new DefaultValueConfiguration()).isLowerThan(value("0", new DefaultValueConfiguration())), is(true));
        assertThat(value(null, new DefaultValueConfiguration()).isLowerThan(value("-1", new DefaultValueConfiguration())), is(true));
        assertThat(value(null, new DefaultValueConfiguration()).isLowerThan(value(null, new DefaultValueConfiguration())), is(false));
        assertThat(value(null, new DefaultValueConfiguration()).isLowerThan(value("lala", new DefaultValueConfiguration())), is(true));
        assertThat(value(null, new DefaultValueConfiguration()).isLowerThan(value("", new DefaultValueConfiguration())), is(false));
        assertThat(value(null, new DefaultValueConfiguration()).isLowerThan(value(1.2, new DefaultValueConfiguration())), is(true));
        assertThat(value("lala", new DefaultValueConfiguration()).isLowerThan(value(true, new DefaultValueConfiguration())), is(false));
        assertThat(value("lala", new DefaultValueConfiguration()).isLowerThan(value(false, new DefaultValueConfiguration())), is(false));
        assertThat(value("lala", new DefaultValueConfiguration()).isLowerThan(value(1, new DefaultValueConfiguration())), is(true));
        assertThat(value("lala", new DefaultValueConfiguration()).isLowerThan(value(0, new DefaultValueConfiguration())), is(false));
        assertThat(value("lala", new DefaultValueConfiguration()).isLowerThan(value(-1, new DefaultValueConfiguration())), is(false));
        assertThat(value("lala", new DefaultValueConfiguration()).isLowerThan(value("1", new DefaultValueConfiguration())), is(false));
        assertThat(value("lala", new DefaultValueConfiguration()).isLowerThan(value("0", new DefaultValueConfiguration())), is(false));
        assertThat(value("lala", new DefaultValueConfiguration()).isLowerThan(value("-1", new DefaultValueConfiguration())), is(false));
        assertThat(value("lala", new DefaultValueConfiguration()).isLowerThan(value(null, new DefaultValueConfiguration())), is(false));
        assertThat(value("lala", new DefaultValueConfiguration()).isLowerThan(value("lala", new DefaultValueConfiguration())), is(false));
        assertThat(value("lala", new DefaultValueConfiguration()).isLowerThan(value("", new DefaultValueConfiguration())), is(false));
        assertThat(value("lala", new DefaultValueConfiguration()).isLowerThan(value(1.2, new DefaultValueConfiguration())), is(true));
        assertThat(value("", new DefaultValueConfiguration()).isLowerThan(value(true, new DefaultValueConfiguration())), is(true));
        assertThat(value("", new DefaultValueConfiguration()).isLowerThan(value(false, new DefaultValueConfiguration())), is(false));
        assertThat(value("", new DefaultValueConfiguration()).isLowerThan(value(1, new DefaultValueConfiguration())), is(true));
        assertThat(value("", new DefaultValueConfiguration()).isLowerThan(value(0, new DefaultValueConfiguration())), is(false));
        assertThat(value("", new DefaultValueConfiguration()).isLowerThan(value(-1, new DefaultValueConfiguration())), is(false));
        assertThat(value("", new DefaultValueConfiguration()).isLowerThan(value("1", new DefaultValueConfiguration())), is(true));
        assertThat(value("", new DefaultValueConfiguration()).isLowerThan(value("0", new DefaultValueConfiguration())), is(true));
        assertThat(value("", new DefaultValueConfiguration()).isLowerThan(value("-1", new DefaultValueConfiguration())), is(true));
        assertThat(value("", new DefaultValueConfiguration()).isLowerThan(value(null, new DefaultValueConfiguration())), is(false));
        assertThat(value("", new DefaultValueConfiguration()).isLowerThan(value("lala", new DefaultValueConfiguration())), is(true));
        assertThat(value("", new DefaultValueConfiguration()).isLowerThan(value("", new DefaultValueConfiguration())), is(false));
        assertThat(value("", new DefaultValueConfiguration()).isLowerThan(value(1.2, new DefaultValueConfiguration())), is(true));
        assertThat(value(1.2, new DefaultValueConfiguration()).isLowerThan(value(true, new DefaultValueConfiguration())), is(false));
        assertThat(value(1.2, new DefaultValueConfiguration()).isLowerThan(value(false, new DefaultValueConfiguration())), is(false));
        assertThat(value(1.2, new DefaultValueConfiguration()).isLowerThan(value(1, new DefaultValueConfiguration())), is(false));
        assertThat(value(1.2, new DefaultValueConfiguration()).isLowerThan(value(0, new DefaultValueConfiguration())), is(false));
        assertThat(value(1.2, new DefaultValueConfiguration()).isLowerThan(value(-1, new DefaultValueConfiguration())), is(false));
        assertThat(value(1.2, new DefaultValueConfiguration()).isLowerThan(value("1", new DefaultValueConfiguration())), is(false));
        assertThat(value(1.2, new DefaultValueConfiguration()).isLowerThan(value("0", new DefaultValueConfiguration())), is(false));
        assertThat(value(1.2, new DefaultValueConfiguration()).isLowerThan(value("-1", new DefaultValueConfiguration())), is(false));
        assertThat(value(1.2, new DefaultValueConfiguration()).isLowerThan(value(null, new DefaultValueConfiguration())), is(false));
        assertThat(value(1.2, new DefaultValueConfiguration()).isLowerThan(value("lala", new DefaultValueConfiguration())), is(false));
        assertThat(value(1.2, new DefaultValueConfiguration()).isLowerThan(value("", new DefaultValueConfiguration())), is(false));
        assertThat(value(1.2, new DefaultValueConfiguration()).isLowerThan(value(1.2, new DefaultValueConfiguration())), is(false));
    }

    @Test
    public void greater() throws Exception {
        assertThat(value(true, new DefaultValueConfiguration()).isGreaterThan(value(true, new DefaultValueConfiguration())), is(false));
        assertThat(value(true, new DefaultValueConfiguration()).isGreaterThan(value(false, new DefaultValueConfiguration())), is(true));
        assertThat(value(true, new DefaultValueConfiguration()).isGreaterThan(value(1, new DefaultValueConfiguration())), is(false));
        assertThat(value(true, new DefaultValueConfiguration()).isGreaterThan(value(0, new DefaultValueConfiguration())), is(true));
        assertThat(value(true, new DefaultValueConfiguration()).isGreaterThan(value(-1, new DefaultValueConfiguration())), is(false));
        assertThat(value(true, new DefaultValueConfiguration()).isGreaterThan(value("1", new DefaultValueConfiguration())), is(false));
        assertThat(value(true, new DefaultValueConfiguration()).isGreaterThan(value("0", new DefaultValueConfiguration())), is(true));
        assertThat(value(true, new DefaultValueConfiguration()).isGreaterThan(value("-1", new DefaultValueConfiguration())), is(false));
        assertThat(value(true, new DefaultValueConfiguration()).isGreaterThan(value(null, new DefaultValueConfiguration())), is(true));
        assertThat(value(true, new DefaultValueConfiguration()).isGreaterThan(value("lala", new DefaultValueConfiguration())), is(false));
        assertThat(value(true, new DefaultValueConfiguration()).isGreaterThan(value("", new DefaultValueConfiguration())), is(true));
        assertThat(value(true, new DefaultValueConfiguration()).isGreaterThan(value(1.2, new DefaultValueConfiguration())), is(false));
        assertThat(value(false, new DefaultValueConfiguration()).isGreaterThan(value(true, new DefaultValueConfiguration())), is(false));
        assertThat(value(false, new DefaultValueConfiguration()).isGreaterThan(value(false, new DefaultValueConfiguration())), is(false));
        assertThat(value(false, new DefaultValueConfiguration()).isGreaterThan(value(1, new DefaultValueConfiguration())), is(false));
        assertThat(value(false, new DefaultValueConfiguration()).isGreaterThan(value(0, new DefaultValueConfiguration())), is(false));
        assertThat(value(false, new DefaultValueConfiguration()).isGreaterThan(value(-1, new DefaultValueConfiguration())), is(false));
        assertThat(value(false, new DefaultValueConfiguration()).isGreaterThan(value("1", new DefaultValueConfiguration())), is(false));
        assertThat(value(false, new DefaultValueConfiguration()).isGreaterThan(value("0", new DefaultValueConfiguration())), is(false));
        assertThat(value(false, new DefaultValueConfiguration()).isGreaterThan(value("-1", new DefaultValueConfiguration())), is(false));
        assertThat(value(false, new DefaultValueConfiguration()).isGreaterThan(value(null, new DefaultValueConfiguration())), is(false));
        assertThat(value(false, new DefaultValueConfiguration()).isGreaterThan(value("lala", new DefaultValueConfiguration())), is(false));
        assertThat(value(false, new DefaultValueConfiguration()).isGreaterThan(value("", new DefaultValueConfiguration())), is(false));
        assertThat(value(false, new DefaultValueConfiguration()).isGreaterThan(value(1.2, new DefaultValueConfiguration())), is(false));
        assertThat(value(1, new DefaultValueConfiguration()).isGreaterThan(value(true, new DefaultValueConfiguration())), is(false));
        assertThat(value(1, new DefaultValueConfiguration()).isGreaterThan(value(false, new DefaultValueConfiguration())), is(true));
        assertThat(value(1, new DefaultValueConfiguration()).isGreaterThan(value(1, new DefaultValueConfiguration())), is(false));
        assertThat(value(1, new DefaultValueConfiguration()).isGreaterThan(value(0, new DefaultValueConfiguration())), is(true));
        assertThat(value(1, new DefaultValueConfiguration()).isGreaterThan(value(-1, new DefaultValueConfiguration())), is(true));
        assertThat(value(1, new DefaultValueConfiguration()).isGreaterThan(value("1", new DefaultValueConfiguration())), is(false));
        assertThat(value(1, new DefaultValueConfiguration()).isGreaterThan(value("0", new DefaultValueConfiguration())), is(true));
        assertThat(value(1, new DefaultValueConfiguration()).isGreaterThan(value("-1", new DefaultValueConfiguration())), is(true));
        assertThat(value(1, new DefaultValueConfiguration()).isGreaterThan(value(null, new DefaultValueConfiguration())), is(true));
        assertThat(value(1, new DefaultValueConfiguration()).isGreaterThan(value("lala", new DefaultValueConfiguration())), is(true));
        assertThat(value(1, new DefaultValueConfiguration()).isGreaterThan(value("", new DefaultValueConfiguration())), is(true));
        assertThat(value(1, new DefaultValueConfiguration()).isGreaterThan(value(1.2, new DefaultValueConfiguration())), is(false));
        assertThat(value(0, new DefaultValueConfiguration()).isGreaterThan(value(true, new DefaultValueConfiguration())), is(false));
        assertThat(value(0, new DefaultValueConfiguration()).isGreaterThan(value(false, new DefaultValueConfiguration())), is(false));
        assertThat(value(0, new DefaultValueConfiguration()).isGreaterThan(value(1, new DefaultValueConfiguration())), is(false));
        assertThat(value(0, new DefaultValueConfiguration()).isGreaterThan(value(0, new DefaultValueConfiguration())), is(false));
        assertThat(value(0, new DefaultValueConfiguration()).isGreaterThan(value(-1, new DefaultValueConfiguration())), is(true));
        assertThat(value(0, new DefaultValueConfiguration()).isGreaterThan(value("1", new DefaultValueConfiguration())), is(false));
        assertThat(value(0, new DefaultValueConfiguration()).isGreaterThan(value("0", new DefaultValueConfiguration())), is(false));
        assertThat(value(0, new DefaultValueConfiguration()).isGreaterThan(value("-1", new DefaultValueConfiguration())), is(true));
        assertThat(value(0, new DefaultValueConfiguration()).isGreaterThan(value(null, new DefaultValueConfiguration())), is(false));
        assertThat(value(0, new DefaultValueConfiguration()).isGreaterThan(value("lala", new DefaultValueConfiguration())), is(false));
        assertThat(value(0, new DefaultValueConfiguration()).isGreaterThan(value("", new DefaultValueConfiguration())), is(false));
        assertThat(value(0, new DefaultValueConfiguration()).isGreaterThan(value(1.2, new DefaultValueConfiguration())), is(false));
        assertThat(value(-1, new DefaultValueConfiguration()).isGreaterThan(value(true, new DefaultValueConfiguration())), is(false));
        assertThat(value(-1, new DefaultValueConfiguration()).isGreaterThan(value(false, new DefaultValueConfiguration())), is(true));
        assertThat(value(-1, new DefaultValueConfiguration()).isGreaterThan(value(1, new DefaultValueConfiguration())), is(false));
        assertThat(value(-1, new DefaultValueConfiguration()).isGreaterThan(value(0, new DefaultValueConfiguration())), is(false));
        assertThat(value(-1, new DefaultValueConfiguration()).isGreaterThan(value(-1, new DefaultValueConfiguration())), is(false));
        assertThat(value(-1, new DefaultValueConfiguration()).isGreaterThan(value("1", new DefaultValueConfiguration())), is(false));
        assertThat(value(-1, new DefaultValueConfiguration()).isGreaterThan(value("0", new DefaultValueConfiguration())), is(false));
        assertThat(value(-1, new DefaultValueConfiguration()).isGreaterThan(value("-1", new DefaultValueConfiguration())), is(false));
        assertThat(value(-1, new DefaultValueConfiguration()).isGreaterThan(value(null, new DefaultValueConfiguration())), is(true));
        assertThat(value(-1, new DefaultValueConfiguration()).isGreaterThan(value("lala", new DefaultValueConfiguration())), is(false));
        assertThat(value(-1, new DefaultValueConfiguration()).isGreaterThan(value("", new DefaultValueConfiguration())), is(false));
        assertThat(value(-1, new DefaultValueConfiguration()).isGreaterThan(value(1.2, new DefaultValueConfiguration())), is(false));
        assertThat(value("1", new DefaultValueConfiguration()).isGreaterThan(value(true, new DefaultValueConfiguration())), is(false));
        assertThat(value("1", new DefaultValueConfiguration()).isGreaterThan(value(false, new DefaultValueConfiguration())), is(true));
        assertThat(value("1", new DefaultValueConfiguration()).isGreaterThan(value(1, new DefaultValueConfiguration())), is(false));
        assertThat(value("1", new DefaultValueConfiguration()).isGreaterThan(value(0, new DefaultValueConfiguration())), is(true));
        assertThat(value("1", new DefaultValueConfiguration()).isGreaterThan(value(-1, new DefaultValueConfiguration())), is(true));
        assertThat(value("1", new DefaultValueConfiguration()).isGreaterThan(value("1", new DefaultValueConfiguration())), is(false));
        assertThat(value("1", new DefaultValueConfiguration()).isGreaterThan(value("0", new DefaultValueConfiguration())), is(true));
        assertThat(value("1", new DefaultValueConfiguration()).isGreaterThan(value("-1", new DefaultValueConfiguration())), is(true));
        assertThat(value("1", new DefaultValueConfiguration()).isGreaterThan(value(null, new DefaultValueConfiguration())), is(true));
        assertThat(value("1", new DefaultValueConfiguration()).isGreaterThan(value("lala", new DefaultValueConfiguration())), is(false));
        assertThat(value("1", new DefaultValueConfiguration()).isGreaterThan(value("", new DefaultValueConfiguration())), is(true));
        assertThat(value("1", new DefaultValueConfiguration()).isGreaterThan(value(1.2, new DefaultValueConfiguration())), is(false));
        assertThat(value("0", new DefaultValueConfiguration()).isGreaterThan(value(true, new DefaultValueConfiguration())), is(false));
        assertThat(value("0", new DefaultValueConfiguration()).isGreaterThan(value(false, new DefaultValueConfiguration())), is(false));
        assertThat(value("0", new DefaultValueConfiguration()).isGreaterThan(value(1, new DefaultValueConfiguration())), is(false));
        assertThat(value("0", new DefaultValueConfiguration()).isGreaterThan(value(0, new DefaultValueConfiguration())), is(false));
        assertThat(value("0", new DefaultValueConfiguration()).isGreaterThan(value(-1, new DefaultValueConfiguration())), is(true));
        assertThat(value("0", new DefaultValueConfiguration()).isGreaterThan(value("1", new DefaultValueConfiguration())), is(false));
        assertThat(value("0", new DefaultValueConfiguration()).isGreaterThan(value("0", new DefaultValueConfiguration())), is(false));
        assertThat(value("0", new DefaultValueConfiguration()).isGreaterThan(value("-1", new DefaultValueConfiguration())), is(true));
        assertThat(value("0", new DefaultValueConfiguration()).isGreaterThan(value(null, new DefaultValueConfiguration())), is(true));
        assertThat(value("0", new DefaultValueConfiguration()).isGreaterThan(value("lala", new DefaultValueConfiguration())), is(false));
        assertThat(value("0", new DefaultValueConfiguration()).isGreaterThan(value("", new DefaultValueConfiguration())), is(true));
        assertThat(value("0", new DefaultValueConfiguration()).isGreaterThan(value(1.2, new DefaultValueConfiguration())), is(false));
        assertThat(value("-1", new DefaultValueConfiguration()).isGreaterThan(value(true, new DefaultValueConfiguration())), is(false));
        assertThat(value("-1", new DefaultValueConfiguration()).isGreaterThan(value(false, new DefaultValueConfiguration())), is(true));
        assertThat(value("-1", new DefaultValueConfiguration()).isGreaterThan(value(1, new DefaultValueConfiguration())), is(false));
        assertThat(value("-1", new DefaultValueConfiguration()).isGreaterThan(value(0, new DefaultValueConfiguration())), is(false));
        assertThat(value("-1", new DefaultValueConfiguration()).isGreaterThan(value(-1, new DefaultValueConfiguration())), is(false));
        assertThat(value("-1", new DefaultValueConfiguration()).isGreaterThan(value("1", new DefaultValueConfiguration())), is(false));
        assertThat(value("-1", new DefaultValueConfiguration()).isGreaterThan(value("0", new DefaultValueConfiguration())), is(false));
        assertThat(value("-1", new DefaultValueConfiguration()).isGreaterThan(value("-1", new DefaultValueConfiguration())), is(false));
        assertThat(value("-1", new DefaultValueConfiguration()).isGreaterThan(value(null, new DefaultValueConfiguration())), is(true));
        assertThat(value("-1", new DefaultValueConfiguration()).isGreaterThan(value("lala", new DefaultValueConfiguration())), is(false));
        assertThat(value("-1", new DefaultValueConfiguration()).isGreaterThan(value("", new DefaultValueConfiguration())), is(true));
        assertThat(value("-1", new DefaultValueConfiguration()).isGreaterThan(value(1.2, new DefaultValueConfiguration())), is(false));
        assertThat(value(null, new DefaultValueConfiguration()).isGreaterThan(value(true, new DefaultValueConfiguration())), is(false));
        assertThat(value(null, new DefaultValueConfiguration()).isGreaterThan(value(false, new DefaultValueConfiguration())), is(false));
        assertThat(value(null, new DefaultValueConfiguration()).isGreaterThan(value(1, new DefaultValueConfiguration())), is(false));
        assertThat(value(null, new DefaultValueConfiguration()).isGreaterThan(value(0, new DefaultValueConfiguration())), is(false));
        assertThat(value(null, new DefaultValueConfiguration()).isGreaterThan(value(-1, new DefaultValueConfiguration())), is(false));
        assertThat(value(null, new DefaultValueConfiguration()).isGreaterThan(value("1", new DefaultValueConfiguration())), is(false));
        assertThat(value(null, new DefaultValueConfiguration()).isGreaterThan(value("0", new DefaultValueConfiguration())), is(false));
        assertThat(value(null, new DefaultValueConfiguration()).isGreaterThan(value("-1", new DefaultValueConfiguration())), is(false));
        assertThat(value(null, new DefaultValueConfiguration()).isGreaterThan(value(null, new DefaultValueConfiguration())), is(false));
        assertThat(value(null, new DefaultValueConfiguration()).isGreaterThan(value("lala", new DefaultValueConfiguration())), is(false));
        assertThat(value(null, new DefaultValueConfiguration()).isGreaterThan(value("", new DefaultValueConfiguration())), is(false));
        assertThat(value(null, new DefaultValueConfiguration()).isGreaterThan(value(1.2, new DefaultValueConfiguration())), is(false));
        assertThat(value("lala", new DefaultValueConfiguration()).isGreaterThan(value(true, new DefaultValueConfiguration())), is(false));
        assertThat(value("lala", new DefaultValueConfiguration()).isGreaterThan(value(false, new DefaultValueConfiguration())), is(true));
        assertThat(value("lala", new DefaultValueConfiguration()).isGreaterThan(value(1, new DefaultValueConfiguration())), is(false));
        assertThat(value("lala", new DefaultValueConfiguration()).isGreaterThan(value(0, new DefaultValueConfiguration())), is(false));
        assertThat(value("lala", new DefaultValueConfiguration()).isGreaterThan(value(-1, new DefaultValueConfiguration())), is(true));
        assertThat(value("lala", new DefaultValueConfiguration()).isGreaterThan(value("1", new DefaultValueConfiguration())), is(true));
        assertThat(value("lala", new DefaultValueConfiguration()).isGreaterThan(value("0", new DefaultValueConfiguration())), is(true));
        assertThat(value("lala", new DefaultValueConfiguration()).isGreaterThan(value("-1", new DefaultValueConfiguration())), is(true));
        assertThat(value("lala", new DefaultValueConfiguration()).isGreaterThan(value(null, new DefaultValueConfiguration())), is(true));
        assertThat(value("lala", new DefaultValueConfiguration()).isGreaterThan(value("lala", new DefaultValueConfiguration())), is(false));
        assertThat(value("lala", new DefaultValueConfiguration()).isGreaterThan(value("", new DefaultValueConfiguration())), is(true));
        assertThat(value("lala", new DefaultValueConfiguration()).isGreaterThan(value(1.2, new DefaultValueConfiguration())), is(false));
        assertThat(value("", new DefaultValueConfiguration()).isGreaterThan(value(true, new DefaultValueConfiguration())), is(false));
        assertThat(value("", new DefaultValueConfiguration()).isGreaterThan(value(false, new DefaultValueConfiguration())), is(false));
        assertThat(value("", new DefaultValueConfiguration()).isGreaterThan(value(1, new DefaultValueConfiguration())), is(false));
        assertThat(value("", new DefaultValueConfiguration()).isGreaterThan(value(0, new DefaultValueConfiguration())), is(false));
        assertThat(value("", new DefaultValueConfiguration()).isGreaterThan(value(-1, new DefaultValueConfiguration())), is(true));
        assertThat(value("", new DefaultValueConfiguration()).isGreaterThan(value("1", new DefaultValueConfiguration())), is(false));
        assertThat(value("", new DefaultValueConfiguration()).isGreaterThan(value("0", new DefaultValueConfiguration())), is(false));
        assertThat(value("", new DefaultValueConfiguration()).isGreaterThan(value("-1", new DefaultValueConfiguration())), is(false));
        assertThat(value("", new DefaultValueConfiguration()).isGreaterThan(value(null, new DefaultValueConfiguration())), is(false));
        assertThat(value("", new DefaultValueConfiguration()).isGreaterThan(value("lala", new DefaultValueConfiguration())), is(false));
        assertThat(value("", new DefaultValueConfiguration()).isGreaterThan(value("", new DefaultValueConfiguration())), is(false));
        assertThat(value("", new DefaultValueConfiguration()).isGreaterThan(value(1.2, new DefaultValueConfiguration())), is(false));
        assertThat(value(1.2, new DefaultValueConfiguration()).isGreaterThan(value(true, new DefaultValueConfiguration())), is(false));
        assertThat(value(1.2, new DefaultValueConfiguration()).isGreaterThan(value(false, new DefaultValueConfiguration())), is(true));
        assertThat(value(1.2, new DefaultValueConfiguration()).isGreaterThan(value(1, new DefaultValueConfiguration())), is(true));
        assertThat(value(1.2, new DefaultValueConfiguration()).isGreaterThan(value(0, new DefaultValueConfiguration())), is(true));
        assertThat(value(1.2, new DefaultValueConfiguration()).isGreaterThan(value(-1, new DefaultValueConfiguration())), is(true));
        assertThat(value(1.2, new DefaultValueConfiguration()).isGreaterThan(value("1", new DefaultValueConfiguration())), is(true));
        assertThat(value(1.2, new DefaultValueConfiguration()).isGreaterThan(value("0", new DefaultValueConfiguration())), is(true));
        assertThat(value(1.2, new DefaultValueConfiguration()).isGreaterThan(value("-1", new DefaultValueConfiguration())), is(true));
        assertThat(value(1.2, new DefaultValueConfiguration()).isGreaterThan(value(null, new DefaultValueConfiguration())), is(true));
        assertThat(value(1.2, new DefaultValueConfiguration()).isGreaterThan(value("lala", new DefaultValueConfiguration())), is(true));
        assertThat(value(1.2, new DefaultValueConfiguration()).isGreaterThan(value("", new DefaultValueConfiguration())), is(true));
        assertThat(value(1.2, new DefaultValueConfiguration()).isGreaterThan(value(1.2, new DefaultValueConfiguration())), is(false));
    }
}