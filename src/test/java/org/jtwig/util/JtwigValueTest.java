package org.jtwig.util;

import org.jtwig.value.JtwigValue;
import org.jtwig.value.configuration.CompatibleModeValueConfiguration;
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
        underTest = value(null, new CompatibleModeValueConfiguration());
        Collection<Object> result = underTest.asCollection();

        assertThat(result.isEmpty(), is(true));
    }

    @Test
    public void asCollectionWhenIterable() throws Exception {
        List<String> list = asList("one");

        underTest = value((Iterable) list, new CompatibleModeValueConfiguration());
        Collection<Object> result = underTest.asCollection();

        assertThat(result, hasItem("one"));
    }

    @Test
    public void asCollectionWhenArray() throws Exception {
        underTest = value(new String[]{"test"}, new CompatibleModeValueConfiguration());
        Collection<Object> result = underTest.asCollection();

        assertThat(result, hasItem("test"));
    }

    @Test
    public void undefined() throws Exception {
        underTest = value(new Object(), new CompatibleModeValueConfiguration());
        assertThat(underTest.isUndefined(), is(false));
    }

    @Test
    public void asStringWhenNull() throws Exception {
        underTest = value(null, new CompatibleModeValueConfiguration());
        assertThat(underTest.asString(), is(""));
    }

    @Test
    public void asCollectionWhenMap() throws Exception {
        underTest = value(new HashMap<Object, Object>() {{
            put("a", "b");
        }}, new CompatibleModeValueConfiguration());
        Collection<Object> result = underTest.asCollection();

        assertThat(result, hasItem("b"));
    }

    @Test
    public void asCollectionWhenSingleValue() throws Exception {
        underTest = value(1, new CompatibleModeValueConfiguration());
        Collection<Object> result = underTest.asCollection();

        assertThat(result, hasItem(1));
    }

    @Test
    public void asMapWhenNull() throws Exception {
        underTest = value(null, new CompatibleModeValueConfiguration());
        Map<Object, Object> result = underTest.asMap();

        assertThat(result.isEmpty(), is(true));
    }

    @Test
    public void asMapWhenSingleValue() throws Exception {
        underTest = value("ola", new CompatibleModeValueConfiguration());
        Map<Object, Object> result = underTest.asMap();

        assertThat(result.isEmpty(), is(false));
        assertThat(result.get(0), is((Object) "ola"));
    }

    @Test
    public void mandatoryNumberInvalid() throws Exception {
        expectedException.expect(IllegalArgumentException.class);

        underTest = value("a", new CompatibleModeValueConfiguration());
        underTest.mandatoryNumber();
    }

    @Test
    public void asMapWhenList() throws Exception {
        underTest = value(asList("one"), new CompatibleModeValueConfiguration());
        Map<Object, Object> result = underTest.asMap();

        assertThat(result.get(0), is((Object) "one"));
    }

    @Test
    public void isPresentWhenNull() throws Exception {
        underTest = value(null, new CompatibleModeValueConfiguration());
        boolean result = underTest.isPresent();
        assertThat(result, is(false));
    }

    @Test
    public void isPresentWhenNonNull() throws Exception {
        underTest = value(1, new CompatibleModeValueConfiguration());
        boolean result = underTest.isPresent();
        assertThat(result, is(true));
    }

    @Test
    public void asMapWhenArray() throws Exception {
        underTest = value(new Integer[]{1, 2}, new CompatibleModeValueConfiguration());
        Map<Object, Object> result = underTest.asMap();

        assertThat(result.get(0), is((Object) 1));
        assertThat(result.get(1), is((Object) 2));
    }

    @Test
    public void asMapWhenMap() throws Exception {
        underTest = value(new HashMap<Object, Object>() {{
            put("a", "b");
        }}, new CompatibleModeValueConfiguration());
        Map<Object, Object> result = underTest.asMap();

        assertThat(result.get("a"), is((Object) "b"));
    }

    @Test
    public void equalComparator() throws Exception {
        assertThat(value(null, new CompatibleModeValueConfiguration()).isEqualTo(value(true, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(null, new CompatibleModeValueConfiguration()).isEqualTo(value(false, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(null, new CompatibleModeValueConfiguration()).isEqualTo(value(1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(null, new CompatibleModeValueConfiguration()).isEqualTo(value(0, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(null, new CompatibleModeValueConfiguration()).isEqualTo(value(-1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(null, new CompatibleModeValueConfiguration()).isEqualTo(value("1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(null, new CompatibleModeValueConfiguration()).isEqualTo(value("0", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(null, new CompatibleModeValueConfiguration()).isEqualTo(value("-1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(null, new CompatibleModeValueConfiguration()).isEqualTo(value(null, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(null, new CompatibleModeValueConfiguration()).isEqualTo(value("lala", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(null, new CompatibleModeValueConfiguration()).isEqualTo(value("", new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(true, new CompatibleModeValueConfiguration()).isEqualTo(value(true, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(true, new CompatibleModeValueConfiguration()).isEqualTo(value(false, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(true, new CompatibleModeValueConfiguration()).isEqualTo(value(1, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(true, new CompatibleModeValueConfiguration()).isEqualTo(value(0, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(true, new CompatibleModeValueConfiguration()).isEqualTo(value(-1, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(true, new CompatibleModeValueConfiguration()).isEqualTo(value("1", new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(true, new CompatibleModeValueConfiguration()).isEqualTo(value("0", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(true, new CompatibleModeValueConfiguration()).isEqualTo(value("-1", new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(true, new CompatibleModeValueConfiguration()).isEqualTo(value(null, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(true, new CompatibleModeValueConfiguration()).isEqualTo(value("lala", new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(true, new CompatibleModeValueConfiguration()).isEqualTo(value("", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(false, new CompatibleModeValueConfiguration()).isEqualTo(value(true, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(false, new CompatibleModeValueConfiguration()).isEqualTo(value(false, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(false, new CompatibleModeValueConfiguration()).isEqualTo(value(1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(false, new CompatibleModeValueConfiguration()).isEqualTo(value(0, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(false, new CompatibleModeValueConfiguration()).isEqualTo(value(-1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(false, new CompatibleModeValueConfiguration()).isEqualTo(value("1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(false, new CompatibleModeValueConfiguration()).isEqualTo(value("0", new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(false, new CompatibleModeValueConfiguration()).isEqualTo(value("-1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(false, new CompatibleModeValueConfiguration()).isEqualTo(value(null, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(false, new CompatibleModeValueConfiguration()).isEqualTo(value("lala", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(false, new CompatibleModeValueConfiguration()).isEqualTo(value("", new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(1, new CompatibleModeValueConfiguration()).isEqualTo(value(true, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(1, new CompatibleModeValueConfiguration()).isEqualTo(value(false, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(1, new CompatibleModeValueConfiguration()).isEqualTo(value(1, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(1, new CompatibleModeValueConfiguration()).isEqualTo(value(0, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(1, new CompatibleModeValueConfiguration()).isEqualTo(value(-1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(1, new CompatibleModeValueConfiguration()).isEqualTo(value("1", new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(1, new CompatibleModeValueConfiguration()).isEqualTo(value("0", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(1, new CompatibleModeValueConfiguration()).isEqualTo(value("-1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(1, new CompatibleModeValueConfiguration()).isEqualTo(value(null, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(1, new CompatibleModeValueConfiguration()).isEqualTo(value("lala", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(1, new CompatibleModeValueConfiguration()).isEqualTo(value("", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(0, new CompatibleModeValueConfiguration()).isEqualTo(value(true, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(0, new CompatibleModeValueConfiguration()).isEqualTo(value(false, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(0, new CompatibleModeValueConfiguration()).isEqualTo(value(1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(0, new CompatibleModeValueConfiguration()).isEqualTo(value(0, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(0, new CompatibleModeValueConfiguration()).isEqualTo(value(-1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(0, new CompatibleModeValueConfiguration()).isEqualTo(value("1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(0, new CompatibleModeValueConfiguration()).isEqualTo(value("0", new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(0, new CompatibleModeValueConfiguration()).isEqualTo(value("-1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(0, new CompatibleModeValueConfiguration()).isEqualTo(value(null, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(0, new CompatibleModeValueConfiguration()).isEqualTo(value("lala", new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(0, new CompatibleModeValueConfiguration()).isEqualTo(value("", new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(-1, new CompatibleModeValueConfiguration()).isEqualTo(value(true, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(-1, new CompatibleModeValueConfiguration()).isEqualTo(value(false, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(-1, new CompatibleModeValueConfiguration()).isEqualTo(value(1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(-1, new CompatibleModeValueConfiguration()).isEqualTo(value(0, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(-1, new CompatibleModeValueConfiguration()).isEqualTo(value(-1, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(-1, new CompatibleModeValueConfiguration()).isEqualTo(value("1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(-1, new CompatibleModeValueConfiguration()).isEqualTo(value("0", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(-1, new CompatibleModeValueConfiguration()).isEqualTo(value("-1", new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(-1, new CompatibleModeValueConfiguration()).isEqualTo(value(null, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(-1, new CompatibleModeValueConfiguration()).isEqualTo(value("lala", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(-1, new CompatibleModeValueConfiguration()).isEqualTo(value("", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("", new CompatibleModeValueConfiguration()).isEqualTo(value(true, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("", new CompatibleModeValueConfiguration()).isEqualTo(value(false, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value("", new CompatibleModeValueConfiguration()).isEqualTo(value(1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("", new CompatibleModeValueConfiguration()).isEqualTo(value(0, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value("", new CompatibleModeValueConfiguration()).isEqualTo(value(-1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("", new CompatibleModeValueConfiguration()).isEqualTo(value("1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("", new CompatibleModeValueConfiguration()).isEqualTo(value("0", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("", new CompatibleModeValueConfiguration()).isEqualTo(value("-1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("", new CompatibleModeValueConfiguration()).isEqualTo(value(null, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value("", new CompatibleModeValueConfiguration()).isEqualTo(value("lala", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("", new CompatibleModeValueConfiguration()).isEqualTo(value("", new CompatibleModeValueConfiguration())), is(true));
        assertThat(value("1", new CompatibleModeValueConfiguration()).isEqualTo(value(true, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value("1", new CompatibleModeValueConfiguration()).isEqualTo(value(false, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("1", new CompatibleModeValueConfiguration()).isEqualTo(value(1, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value("1", new CompatibleModeValueConfiguration()).isEqualTo(value(0, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("1", new CompatibleModeValueConfiguration()).isEqualTo(value(-1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("1", new CompatibleModeValueConfiguration()).isEqualTo(value("1", new CompatibleModeValueConfiguration())), is(true));
        assertThat(value("1", new CompatibleModeValueConfiguration()).isEqualTo(value("0", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("1", new CompatibleModeValueConfiguration()).isEqualTo(value("-1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("1", new CompatibleModeValueConfiguration()).isEqualTo(value(null, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("1", new CompatibleModeValueConfiguration()).isEqualTo(value("lala", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("1", new CompatibleModeValueConfiguration()).isEqualTo(value("", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("0", new CompatibleModeValueConfiguration()).isEqualTo(value(true, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("0", new CompatibleModeValueConfiguration()).isEqualTo(value(false, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value("0", new CompatibleModeValueConfiguration()).isEqualTo(value(1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("0", new CompatibleModeValueConfiguration()).isEqualTo(value(0, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value("0", new CompatibleModeValueConfiguration()).isEqualTo(value(-1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("0", new CompatibleModeValueConfiguration()).isEqualTo(value("1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("0", new CompatibleModeValueConfiguration()).isEqualTo(value("0", new CompatibleModeValueConfiguration())), is(true));
        assertThat(value("0", new CompatibleModeValueConfiguration()).isEqualTo(value("-1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("0", new CompatibleModeValueConfiguration()).isEqualTo(value(null, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("0", new CompatibleModeValueConfiguration()).isEqualTo(value("lala", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("0", new CompatibleModeValueConfiguration()).isEqualTo(value("", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("-1", new CompatibleModeValueConfiguration()).isEqualTo(value(true, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value("-1", new CompatibleModeValueConfiguration()).isEqualTo(value(false, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("-1", new CompatibleModeValueConfiguration()).isEqualTo(value(1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("-1", new CompatibleModeValueConfiguration()).isEqualTo(value(0, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("-1", new CompatibleModeValueConfiguration()).isEqualTo(value(-1, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value("-1", new CompatibleModeValueConfiguration()).isEqualTo(value("1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("-1", new CompatibleModeValueConfiguration()).isEqualTo(value("0", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("-1", new CompatibleModeValueConfiguration()).isEqualTo(value("-1", new CompatibleModeValueConfiguration())), is(true));
        assertThat(value("-1", new CompatibleModeValueConfiguration()).isEqualTo(value(null, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("-1", new CompatibleModeValueConfiguration()).isEqualTo(value("lala", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("-1", new CompatibleModeValueConfiguration()).isEqualTo(value("", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("lala", new CompatibleModeValueConfiguration()).isEqualTo(value(true, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value("lala", new CompatibleModeValueConfiguration()).isEqualTo(value(false, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("lala", new CompatibleModeValueConfiguration()).isEqualTo(value(1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("lala", new CompatibleModeValueConfiguration()).isEqualTo(value(0, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value("lala", new CompatibleModeValueConfiguration()).isEqualTo(value(-1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("lala", new CompatibleModeValueConfiguration()).isEqualTo(value("1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("lala", new CompatibleModeValueConfiguration()).isEqualTo(value("0", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("lala", new CompatibleModeValueConfiguration()).isEqualTo(value("-1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("lala", new CompatibleModeValueConfiguration()).isEqualTo(value(null, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("lala", new CompatibleModeValueConfiguration()).isEqualTo(value("lala", new CompatibleModeValueConfiguration())), is(true));
        assertThat(value("lala", new CompatibleModeValueConfiguration()).isEqualTo(value("lalaa", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("lala", new CompatibleModeValueConfiguration()).isEqualTo(value("", new CompatibleModeValueConfiguration())), is(false));
    }

    @Test
    public void identical() throws Exception {
        assertThat(value(true, new CompatibleModeValueConfiguration()).isIdenticalTo(value(true, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(true, new CompatibleModeValueConfiguration()).isIdenticalTo(value(false, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(true, new CompatibleModeValueConfiguration()).isIdenticalTo(value(1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(true, new CompatibleModeValueConfiguration()).isIdenticalTo(value(0, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(true, new CompatibleModeValueConfiguration()).isIdenticalTo(value(-1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(true, new CompatibleModeValueConfiguration()).isIdenticalTo(value("1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(true, new CompatibleModeValueConfiguration()).isIdenticalTo(value("0", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(true, new CompatibleModeValueConfiguration()).isIdenticalTo(value("-1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(true, new CompatibleModeValueConfiguration()).isIdenticalTo(value(null, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(true, new CompatibleModeValueConfiguration()).isIdenticalTo(value("lala", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(true, new CompatibleModeValueConfiguration()).isIdenticalTo(value("", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(true, new CompatibleModeValueConfiguration()).isIdenticalTo(value(1.2, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(false, new CompatibleModeValueConfiguration()).isIdenticalTo(value(true, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(false, new CompatibleModeValueConfiguration()).isIdenticalTo(value(false, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(false, new CompatibleModeValueConfiguration()).isIdenticalTo(value(1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(false, new CompatibleModeValueConfiguration()).isIdenticalTo(value(0, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(false, new CompatibleModeValueConfiguration()).isIdenticalTo(value(-1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(false, new CompatibleModeValueConfiguration()).isIdenticalTo(value("1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(false, new CompatibleModeValueConfiguration()).isIdenticalTo(value("0", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(false, new CompatibleModeValueConfiguration()).isIdenticalTo(value("-1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(false, new CompatibleModeValueConfiguration()).isIdenticalTo(value(null, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(false, new CompatibleModeValueConfiguration()).isIdenticalTo(value("lala", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(false, new CompatibleModeValueConfiguration()).isIdenticalTo(value("", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(false, new CompatibleModeValueConfiguration()).isIdenticalTo(value(1.2, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(1, new CompatibleModeValueConfiguration()).isIdenticalTo(value(true, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(1, new CompatibleModeValueConfiguration()).isIdenticalTo(value(false, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(1, new CompatibleModeValueConfiguration()).isIdenticalTo(value(1, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(1, new CompatibleModeValueConfiguration()).isIdenticalTo(value(0, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(1, new CompatibleModeValueConfiguration()).isIdenticalTo(value(-1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(1, new CompatibleModeValueConfiguration()).isIdenticalTo(value("1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(1, new CompatibleModeValueConfiguration()).isIdenticalTo(value("0", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(1, new CompatibleModeValueConfiguration()).isIdenticalTo(value("-1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(1, new CompatibleModeValueConfiguration()).isIdenticalTo(value(null, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(1, new CompatibleModeValueConfiguration()).isIdenticalTo(value("lala", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(1, new CompatibleModeValueConfiguration()).isIdenticalTo(value("", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(1, new CompatibleModeValueConfiguration()).isIdenticalTo(value(1.2, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(0, new CompatibleModeValueConfiguration()).isIdenticalTo(value(true, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(0, new CompatibleModeValueConfiguration()).isIdenticalTo(value(false, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(0, new CompatibleModeValueConfiguration()).isIdenticalTo(value(1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(0, new CompatibleModeValueConfiguration()).isIdenticalTo(value(0, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(0, new CompatibleModeValueConfiguration()).isIdenticalTo(value(-1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(0, new CompatibleModeValueConfiguration()).isIdenticalTo(value("1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(0, new CompatibleModeValueConfiguration()).isIdenticalTo(value("0", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(0, new CompatibleModeValueConfiguration()).isIdenticalTo(value("-1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(0, new CompatibleModeValueConfiguration()).isIdenticalTo(value(null, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(0, new CompatibleModeValueConfiguration()).isIdenticalTo(value("lala", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(0, new CompatibleModeValueConfiguration()).isIdenticalTo(value("", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(0, new CompatibleModeValueConfiguration()).isIdenticalTo(value(1.2, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(-1, new CompatibleModeValueConfiguration()).isIdenticalTo(value(true, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(-1, new CompatibleModeValueConfiguration()).isIdenticalTo(value(false, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(-1, new CompatibleModeValueConfiguration()).isIdenticalTo(value(1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(-1, new CompatibleModeValueConfiguration()).isIdenticalTo(value(0, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(-1, new CompatibleModeValueConfiguration()).isIdenticalTo(value(-1, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(-1, new CompatibleModeValueConfiguration()).isIdenticalTo(value("1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(-1, new CompatibleModeValueConfiguration()).isIdenticalTo(value("0", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(-1, new CompatibleModeValueConfiguration()).isIdenticalTo(value("-1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(-1, new CompatibleModeValueConfiguration()).isIdenticalTo(value(null, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(-1, new CompatibleModeValueConfiguration()).isIdenticalTo(value("lala", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(-1, new CompatibleModeValueConfiguration()).isIdenticalTo(value("", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(-1, new CompatibleModeValueConfiguration()).isIdenticalTo(value(1.2, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("1", new CompatibleModeValueConfiguration()).isIdenticalTo(value(true, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("1", new CompatibleModeValueConfiguration()).isIdenticalTo(value(false, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("1", new CompatibleModeValueConfiguration()).isIdenticalTo(value(1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("1", new CompatibleModeValueConfiguration()).isIdenticalTo(value(0, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("1", new CompatibleModeValueConfiguration()).isIdenticalTo(value(-1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("1", new CompatibleModeValueConfiguration()).isIdenticalTo(value("1", new CompatibleModeValueConfiguration())), is(true));
        assertThat(value("1", new CompatibleModeValueConfiguration()).isIdenticalTo(value("0", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("1", new CompatibleModeValueConfiguration()).isIdenticalTo(value("-1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("1", new CompatibleModeValueConfiguration()).isIdenticalTo(value(null, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("1", new CompatibleModeValueConfiguration()).isIdenticalTo(value("lala", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("1", new CompatibleModeValueConfiguration()).isIdenticalTo(value("", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("1", new CompatibleModeValueConfiguration()).isIdenticalTo(value(1.2, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("0", new CompatibleModeValueConfiguration()).isIdenticalTo(value(true, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("0", new CompatibleModeValueConfiguration()).isIdenticalTo(value(false, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("0", new CompatibleModeValueConfiguration()).isIdenticalTo(value(1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("0", new CompatibleModeValueConfiguration()).isIdenticalTo(value(0, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("0", new CompatibleModeValueConfiguration()).isIdenticalTo(value(-1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("0", new CompatibleModeValueConfiguration()).isIdenticalTo(value("1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("0", new CompatibleModeValueConfiguration()).isIdenticalTo(value("0", new CompatibleModeValueConfiguration())), is(true));
        assertThat(value("0", new CompatibleModeValueConfiguration()).isIdenticalTo(value("-1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("0", new CompatibleModeValueConfiguration()).isIdenticalTo(value(null, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("0", new CompatibleModeValueConfiguration()).isIdenticalTo(value("lala", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("0", new CompatibleModeValueConfiguration()).isIdenticalTo(value("", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("0", new CompatibleModeValueConfiguration()).isIdenticalTo(value(1.2, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("-1", new CompatibleModeValueConfiguration()).isIdenticalTo(value(true, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("-1", new CompatibleModeValueConfiguration()).isIdenticalTo(value(false, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("-1", new CompatibleModeValueConfiguration()).isIdenticalTo(value(1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("-1", new CompatibleModeValueConfiguration()).isIdenticalTo(value(0, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("-1", new CompatibleModeValueConfiguration()).isIdenticalTo(value(-1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("-1", new CompatibleModeValueConfiguration()).isIdenticalTo(value("1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("-1", new CompatibleModeValueConfiguration()).isIdenticalTo(value("0", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("-1", new CompatibleModeValueConfiguration()).isIdenticalTo(value("-1", new CompatibleModeValueConfiguration())), is(true));
        assertThat(value("-1", new CompatibleModeValueConfiguration()).isIdenticalTo(value(null, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("-1", new CompatibleModeValueConfiguration()).isIdenticalTo(value("lala", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("-1", new CompatibleModeValueConfiguration()).isIdenticalTo(value("", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("-1", new CompatibleModeValueConfiguration()).isIdenticalTo(value(1.2, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(null, new CompatibleModeValueConfiguration()).isIdenticalTo(value(true, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(null, new CompatibleModeValueConfiguration()).isIdenticalTo(value(false, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(null, new CompatibleModeValueConfiguration()).isIdenticalTo(value(1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(null, new CompatibleModeValueConfiguration()).isIdenticalTo(value(0, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(null, new CompatibleModeValueConfiguration()).isIdenticalTo(value(-1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(null, new CompatibleModeValueConfiguration()).isIdenticalTo(value("1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(null, new CompatibleModeValueConfiguration()).isIdenticalTo(value("0", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(null, new CompatibleModeValueConfiguration()).isIdenticalTo(value("-1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(null, new CompatibleModeValueConfiguration()).isIdenticalTo(value(null, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(null, new CompatibleModeValueConfiguration()).isIdenticalTo(value("lala", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(null, new CompatibleModeValueConfiguration()).isIdenticalTo(value("", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(null, new CompatibleModeValueConfiguration()).isIdenticalTo(value(1.2, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("lala", new CompatibleModeValueConfiguration()).isIdenticalTo(value(true, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("lala", new CompatibleModeValueConfiguration()).isIdenticalTo(value(false, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("lala", new CompatibleModeValueConfiguration()).isIdenticalTo(value(1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("lala", new CompatibleModeValueConfiguration()).isIdenticalTo(value(0, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("lala", new CompatibleModeValueConfiguration()).isIdenticalTo(value(-1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("lala", new CompatibleModeValueConfiguration()).isIdenticalTo(value("1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("lala", new CompatibleModeValueConfiguration()).isIdenticalTo(value("0", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("lala", new CompatibleModeValueConfiguration()).isIdenticalTo(value("-1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("lala", new CompatibleModeValueConfiguration()).isIdenticalTo(value(null, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("lala", new CompatibleModeValueConfiguration()).isIdenticalTo(value("lala", new CompatibleModeValueConfiguration())), is(true));
        assertThat(value("lala", new CompatibleModeValueConfiguration()).isIdenticalTo(value("lalaa", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("lala", new CompatibleModeValueConfiguration()).isIdenticalTo(value("", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("lala", new CompatibleModeValueConfiguration()).isIdenticalTo(value(1.2, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("", new CompatibleModeValueConfiguration()).isIdenticalTo(value(true, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("", new CompatibleModeValueConfiguration()).isIdenticalTo(value(false, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("", new CompatibleModeValueConfiguration()).isIdenticalTo(value(1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("", new CompatibleModeValueConfiguration()).isIdenticalTo(value(0, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("", new CompatibleModeValueConfiguration()).isIdenticalTo(value(-1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("", new CompatibleModeValueConfiguration()).isIdenticalTo(value("1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("", new CompatibleModeValueConfiguration()).isIdenticalTo(value("0", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("", new CompatibleModeValueConfiguration()).isIdenticalTo(value("-1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("", new CompatibleModeValueConfiguration()).isIdenticalTo(value(null, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("", new CompatibleModeValueConfiguration()).isIdenticalTo(value("lala", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("", new CompatibleModeValueConfiguration()).isIdenticalTo(value("", new CompatibleModeValueConfiguration())), is(true));
        assertThat(value("", new CompatibleModeValueConfiguration()).isIdenticalTo(value(1.2, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(1.2, new CompatibleModeValueConfiguration()).isIdenticalTo(value(true, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(1.2, new CompatibleModeValueConfiguration()).isIdenticalTo(value(false, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(1.2, new CompatibleModeValueConfiguration()).isIdenticalTo(value(1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(1.2, new CompatibleModeValueConfiguration()).isIdenticalTo(value(0, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(1.2, new CompatibleModeValueConfiguration()).isIdenticalTo(value(-1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(1.2, new CompatibleModeValueConfiguration()).isIdenticalTo(value("1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(1.2, new CompatibleModeValueConfiguration()).isIdenticalTo(value("0", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(1.2, new CompatibleModeValueConfiguration()).isIdenticalTo(value("-1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(1.2, new CompatibleModeValueConfiguration()).isIdenticalTo(value(null, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(1.2, new CompatibleModeValueConfiguration()).isIdenticalTo(value("lala", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(1.2, new CompatibleModeValueConfiguration()).isIdenticalTo(value("", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(1.2, new CompatibleModeValueConfiguration()).isIdenticalTo(value(1.2, new CompatibleModeValueConfiguration())), is(true));
    }

    @Test
    public void lower() throws Exception {
        assertThat(value(true, new CompatibleModeValueConfiguration()).isLowerThan(value(true, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(true, new CompatibleModeValueConfiguration()).isLowerThan(value(false, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(true, new CompatibleModeValueConfiguration()).isLowerThan(value(1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(true, new CompatibleModeValueConfiguration()).isLowerThan(value(0, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(true, new CompatibleModeValueConfiguration()).isLowerThan(value(-1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(true, new CompatibleModeValueConfiguration()).isLowerThan(value("1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(true, new CompatibleModeValueConfiguration()).isLowerThan(value("0", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(true, new CompatibleModeValueConfiguration()).isLowerThan(value("-1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(true, new CompatibleModeValueConfiguration()).isLowerThan(value(null, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(true, new CompatibleModeValueConfiguration()).isLowerThan(value("lala", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(true, new CompatibleModeValueConfiguration()).isLowerThan(value("", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(true, new CompatibleModeValueConfiguration()).isLowerThan(value(1.2, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(false, new CompatibleModeValueConfiguration()).isLowerThan(value(true, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(false, new CompatibleModeValueConfiguration()).isLowerThan(value(false, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(false, new CompatibleModeValueConfiguration()).isLowerThan(value(1, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(false, new CompatibleModeValueConfiguration()).isLowerThan(value(0, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(false, new CompatibleModeValueConfiguration()).isLowerThan(value(-1, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(false, new CompatibleModeValueConfiguration()).isLowerThan(value("1", new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(false, new CompatibleModeValueConfiguration()).isLowerThan(value("0", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(false, new CompatibleModeValueConfiguration()).isLowerThan(value("-1", new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(false, new CompatibleModeValueConfiguration()).isLowerThan(value(null, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(false, new CompatibleModeValueConfiguration()).isLowerThan(value("lala", new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(false, new CompatibleModeValueConfiguration()).isLowerThan(value("", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(false, new CompatibleModeValueConfiguration()).isLowerThan(value(1.2, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(1, new CompatibleModeValueConfiguration()).isLowerThan(value(true, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(1, new CompatibleModeValueConfiguration()).isLowerThan(value(false, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(1, new CompatibleModeValueConfiguration()).isLowerThan(value(1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(1, new CompatibleModeValueConfiguration()).isLowerThan(value(0, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(1, new CompatibleModeValueConfiguration()).isLowerThan(value(-1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(1, new CompatibleModeValueConfiguration()).isLowerThan(value("1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(1, new CompatibleModeValueConfiguration()).isLowerThan(value("0", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(1, new CompatibleModeValueConfiguration()).isLowerThan(value("-1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(1, new CompatibleModeValueConfiguration()).isLowerThan(value(null, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(1, new CompatibleModeValueConfiguration()).isLowerThan(value("lala", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(1, new CompatibleModeValueConfiguration()).isLowerThan(value("", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(1, new CompatibleModeValueConfiguration()).isLowerThan(value(1.2, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(0, new CompatibleModeValueConfiguration()).isLowerThan(value(true, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(0, new CompatibleModeValueConfiguration()).isLowerThan(value(false, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(0, new CompatibleModeValueConfiguration()).isLowerThan(value(1, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(0, new CompatibleModeValueConfiguration()).isLowerThan(value(0, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(0, new CompatibleModeValueConfiguration()).isLowerThan(value(-1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(0, new CompatibleModeValueConfiguration()).isLowerThan(value("1", new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(0, new CompatibleModeValueConfiguration()).isLowerThan(value("0", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(0, new CompatibleModeValueConfiguration()).isLowerThan(value("-1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(0, new CompatibleModeValueConfiguration()).isLowerThan(value(null, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(0, new CompatibleModeValueConfiguration()).isLowerThan(value("lala", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(0, new CompatibleModeValueConfiguration()).isLowerThan(value("", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(0, new CompatibleModeValueConfiguration()).isLowerThan(value(1.2, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(-1, new CompatibleModeValueConfiguration()).isLowerThan(value(true, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(-1, new CompatibleModeValueConfiguration()).isLowerThan(value(false, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(-1, new CompatibleModeValueConfiguration()).isLowerThan(value(1, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(-1, new CompatibleModeValueConfiguration()).isLowerThan(value(0, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(-1, new CompatibleModeValueConfiguration()).isLowerThan(value(-1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(-1, new CompatibleModeValueConfiguration()).isLowerThan(value("1", new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(-1, new CompatibleModeValueConfiguration()).isLowerThan(value("0", new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(-1, new CompatibleModeValueConfiguration()).isLowerThan(value("-1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(-1, new CompatibleModeValueConfiguration()).isLowerThan(value(null, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(-1, new CompatibleModeValueConfiguration()).isLowerThan(value("lala", new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(-1, new CompatibleModeValueConfiguration()).isLowerThan(value("", new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(-1, new CompatibleModeValueConfiguration()).isLowerThan(value(1.2, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value("1", new CompatibleModeValueConfiguration()).isLowerThan(value(true, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("1", new CompatibleModeValueConfiguration()).isLowerThan(value(false, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("1", new CompatibleModeValueConfiguration()).isLowerThan(value(1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("1", new CompatibleModeValueConfiguration()).isLowerThan(value(0, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("1", new CompatibleModeValueConfiguration()).isLowerThan(value(-1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("1", new CompatibleModeValueConfiguration()).isLowerThan(value("1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("1", new CompatibleModeValueConfiguration()).isLowerThan(value("0", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("1", new CompatibleModeValueConfiguration()).isLowerThan(value("-1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("1", new CompatibleModeValueConfiguration()).isLowerThan(value(null, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("1", new CompatibleModeValueConfiguration()).isLowerThan(value("lala", new CompatibleModeValueConfiguration())), is(true));
        assertThat(value("1", new CompatibleModeValueConfiguration()).isLowerThan(value("", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("1", new CompatibleModeValueConfiguration()).isLowerThan(value(1.2, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value("0", new CompatibleModeValueConfiguration()).isLowerThan(value(true, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value("0", new CompatibleModeValueConfiguration()).isLowerThan(value(false, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("0", new CompatibleModeValueConfiguration()).isLowerThan(value(1, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value("0", new CompatibleModeValueConfiguration()).isLowerThan(value(0, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("0", new CompatibleModeValueConfiguration()).isLowerThan(value(-1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("0", new CompatibleModeValueConfiguration()).isLowerThan(value("1", new CompatibleModeValueConfiguration())), is(true));
        assertThat(value("0", new CompatibleModeValueConfiguration()).isLowerThan(value("0", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("0", new CompatibleModeValueConfiguration()).isLowerThan(value("-1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("0", new CompatibleModeValueConfiguration()).isLowerThan(value(null, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("0", new CompatibleModeValueConfiguration()).isLowerThan(value("lala", new CompatibleModeValueConfiguration())), is(true));
        assertThat(value("0", new CompatibleModeValueConfiguration()).isLowerThan(value("", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("0", new CompatibleModeValueConfiguration()).isLowerThan(value(1.2, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value("-1", new CompatibleModeValueConfiguration()).isLowerThan(value(true, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("-1", new CompatibleModeValueConfiguration()).isLowerThan(value(false, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("-1", new CompatibleModeValueConfiguration()).isLowerThan(value(1, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value("-1", new CompatibleModeValueConfiguration()).isLowerThan(value(0, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value("-1", new CompatibleModeValueConfiguration()).isLowerThan(value(-1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("-1", new CompatibleModeValueConfiguration()).isLowerThan(value("1", new CompatibleModeValueConfiguration())), is(true));
        assertThat(value("-1", new CompatibleModeValueConfiguration()).isLowerThan(value("0", new CompatibleModeValueConfiguration())), is(true));
        assertThat(value("-1", new CompatibleModeValueConfiguration()).isLowerThan(value("-1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("-1", new CompatibleModeValueConfiguration()).isLowerThan(value(null, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("-1", new CompatibleModeValueConfiguration()).isLowerThan(value("lala", new CompatibleModeValueConfiguration())), is(true));
        assertThat(value("-1", new CompatibleModeValueConfiguration()).isLowerThan(value("", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("-1", new CompatibleModeValueConfiguration()).isLowerThan(value(1.2, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(null, new CompatibleModeValueConfiguration()).isLowerThan(value(true, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(null, new CompatibleModeValueConfiguration()).isLowerThan(value(false, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(null, new CompatibleModeValueConfiguration()).isLowerThan(value(1, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(null, new CompatibleModeValueConfiguration()).isLowerThan(value(0, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(null, new CompatibleModeValueConfiguration()).isLowerThan(value(-1, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(null, new CompatibleModeValueConfiguration()).isLowerThan(value("1", new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(null, new CompatibleModeValueConfiguration()).isLowerThan(value("0", new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(null, new CompatibleModeValueConfiguration()).isLowerThan(value("-1", new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(null, new CompatibleModeValueConfiguration()).isLowerThan(value(null, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(null, new CompatibleModeValueConfiguration()).isLowerThan(value("lala", new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(null, new CompatibleModeValueConfiguration()).isLowerThan(value("", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(null, new CompatibleModeValueConfiguration()).isLowerThan(value(1.2, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value("lala", new CompatibleModeValueConfiguration()).isLowerThan(value(true, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("lala", new CompatibleModeValueConfiguration()).isLowerThan(value(false, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("lala", new CompatibleModeValueConfiguration()).isLowerThan(value(1, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value("lala", new CompatibleModeValueConfiguration()).isLowerThan(value(0, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("lala", new CompatibleModeValueConfiguration()).isLowerThan(value(-1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("lala", new CompatibleModeValueConfiguration()).isLowerThan(value("1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("lala", new CompatibleModeValueConfiguration()).isLowerThan(value("0", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("lala", new CompatibleModeValueConfiguration()).isLowerThan(value("-1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("lala", new CompatibleModeValueConfiguration()).isLowerThan(value(null, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("lala", new CompatibleModeValueConfiguration()).isLowerThan(value("lala", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("lala", new CompatibleModeValueConfiguration()).isLowerThan(value("", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("lala", new CompatibleModeValueConfiguration()).isLowerThan(value(1.2, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value("", new CompatibleModeValueConfiguration()).isLowerThan(value(true, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value("", new CompatibleModeValueConfiguration()).isLowerThan(value(false, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("", new CompatibleModeValueConfiguration()).isLowerThan(value(1, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value("", new CompatibleModeValueConfiguration()).isLowerThan(value(0, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("", new CompatibleModeValueConfiguration()).isLowerThan(value(-1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("", new CompatibleModeValueConfiguration()).isLowerThan(value("1", new CompatibleModeValueConfiguration())), is(true));
        assertThat(value("", new CompatibleModeValueConfiguration()).isLowerThan(value("0", new CompatibleModeValueConfiguration())), is(true));
        assertThat(value("", new CompatibleModeValueConfiguration()).isLowerThan(value("-1", new CompatibleModeValueConfiguration())), is(true));
        assertThat(value("", new CompatibleModeValueConfiguration()).isLowerThan(value(null, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("", new CompatibleModeValueConfiguration()).isLowerThan(value("lala", new CompatibleModeValueConfiguration())), is(true));
        assertThat(value("", new CompatibleModeValueConfiguration()).isLowerThan(value("", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("", new CompatibleModeValueConfiguration()).isLowerThan(value(1.2, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(1.2, new CompatibleModeValueConfiguration()).isLowerThan(value(true, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(1.2, new CompatibleModeValueConfiguration()).isLowerThan(value(false, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(1.2, new CompatibleModeValueConfiguration()).isLowerThan(value(1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(1.2, new CompatibleModeValueConfiguration()).isLowerThan(value(0, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(1.2, new CompatibleModeValueConfiguration()).isLowerThan(value(-1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(1.2, new CompatibleModeValueConfiguration()).isLowerThan(value("1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(1.2, new CompatibleModeValueConfiguration()).isLowerThan(value("0", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(1.2, new CompatibleModeValueConfiguration()).isLowerThan(value("-1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(1.2, new CompatibleModeValueConfiguration()).isLowerThan(value(null, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(1.2, new CompatibleModeValueConfiguration()).isLowerThan(value("lala", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(1.2, new CompatibleModeValueConfiguration()).isLowerThan(value("", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(1.2, new CompatibleModeValueConfiguration()).isLowerThan(value(1.2, new CompatibleModeValueConfiguration())), is(false));
    }

    @Test
    public void greater() throws Exception {
        assertThat(value(true, new CompatibleModeValueConfiguration()).isGreaterThan(value(true, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(true, new CompatibleModeValueConfiguration()).isGreaterThan(value(false, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(true, new CompatibleModeValueConfiguration()).isGreaterThan(value(1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(true, new CompatibleModeValueConfiguration()).isGreaterThan(value(0, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(true, new CompatibleModeValueConfiguration()).isGreaterThan(value(-1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(true, new CompatibleModeValueConfiguration()).isGreaterThan(value("1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(true, new CompatibleModeValueConfiguration()).isGreaterThan(value("0", new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(true, new CompatibleModeValueConfiguration()).isGreaterThan(value("-1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(true, new CompatibleModeValueConfiguration()).isGreaterThan(value(null, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(true, new CompatibleModeValueConfiguration()).isGreaterThan(value("lala", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(true, new CompatibleModeValueConfiguration()).isGreaterThan(value("", new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(true, new CompatibleModeValueConfiguration()).isGreaterThan(value(1.2, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(false, new CompatibleModeValueConfiguration()).isGreaterThan(value(true, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(false, new CompatibleModeValueConfiguration()).isGreaterThan(value(false, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(false, new CompatibleModeValueConfiguration()).isGreaterThan(value(1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(false, new CompatibleModeValueConfiguration()).isGreaterThan(value(0, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(false, new CompatibleModeValueConfiguration()).isGreaterThan(value(-1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(false, new CompatibleModeValueConfiguration()).isGreaterThan(value("1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(false, new CompatibleModeValueConfiguration()).isGreaterThan(value("0", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(false, new CompatibleModeValueConfiguration()).isGreaterThan(value("-1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(false, new CompatibleModeValueConfiguration()).isGreaterThan(value(null, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(false, new CompatibleModeValueConfiguration()).isGreaterThan(value("lala", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(false, new CompatibleModeValueConfiguration()).isGreaterThan(value("", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(false, new CompatibleModeValueConfiguration()).isGreaterThan(value(1.2, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(1, new CompatibleModeValueConfiguration()).isGreaterThan(value(true, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(1, new CompatibleModeValueConfiguration()).isGreaterThan(value(false, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(1, new CompatibleModeValueConfiguration()).isGreaterThan(value(1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(1, new CompatibleModeValueConfiguration()).isGreaterThan(value(0, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(1, new CompatibleModeValueConfiguration()).isGreaterThan(value(-1, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(1, new CompatibleModeValueConfiguration()).isGreaterThan(value("1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(1, new CompatibleModeValueConfiguration()).isGreaterThan(value("0", new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(1, new CompatibleModeValueConfiguration()).isGreaterThan(value("-1", new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(1, new CompatibleModeValueConfiguration()).isGreaterThan(value(null, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(1, new CompatibleModeValueConfiguration()).isGreaterThan(value("lala", new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(1, new CompatibleModeValueConfiguration()).isGreaterThan(value("", new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(1, new CompatibleModeValueConfiguration()).isGreaterThan(value(1.2, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(0, new CompatibleModeValueConfiguration()).isGreaterThan(value(true, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(0, new CompatibleModeValueConfiguration()).isGreaterThan(value(false, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(0, new CompatibleModeValueConfiguration()).isGreaterThan(value(1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(0, new CompatibleModeValueConfiguration()).isGreaterThan(value(0, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(0, new CompatibleModeValueConfiguration()).isGreaterThan(value(-1, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(0, new CompatibleModeValueConfiguration()).isGreaterThan(value("1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(0, new CompatibleModeValueConfiguration()).isGreaterThan(value("0", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(0, new CompatibleModeValueConfiguration()).isGreaterThan(value("-1", new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(0, new CompatibleModeValueConfiguration()).isGreaterThan(value(null, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(0, new CompatibleModeValueConfiguration()).isGreaterThan(value("lala", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(0, new CompatibleModeValueConfiguration()).isGreaterThan(value("", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(0, new CompatibleModeValueConfiguration()).isGreaterThan(value(1.2, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(-1, new CompatibleModeValueConfiguration()).isGreaterThan(value(true, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(-1, new CompatibleModeValueConfiguration()).isGreaterThan(value(false, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(-1, new CompatibleModeValueConfiguration()).isGreaterThan(value(1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(-1, new CompatibleModeValueConfiguration()).isGreaterThan(value(0, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(-1, new CompatibleModeValueConfiguration()).isGreaterThan(value(-1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(-1, new CompatibleModeValueConfiguration()).isGreaterThan(value("1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(-1, new CompatibleModeValueConfiguration()).isGreaterThan(value("0", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(-1, new CompatibleModeValueConfiguration()).isGreaterThan(value("-1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(-1, new CompatibleModeValueConfiguration()).isGreaterThan(value(null, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(-1, new CompatibleModeValueConfiguration()).isGreaterThan(value("lala", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(-1, new CompatibleModeValueConfiguration()).isGreaterThan(value("", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(-1, new CompatibleModeValueConfiguration()).isGreaterThan(value(1.2, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("1", new CompatibleModeValueConfiguration()).isGreaterThan(value(true, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("1", new CompatibleModeValueConfiguration()).isGreaterThan(value(false, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value("1", new CompatibleModeValueConfiguration()).isGreaterThan(value(1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("1", new CompatibleModeValueConfiguration()).isGreaterThan(value(0, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value("1", new CompatibleModeValueConfiguration()).isGreaterThan(value(-1, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value("1", new CompatibleModeValueConfiguration()).isGreaterThan(value("1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("1", new CompatibleModeValueConfiguration()).isGreaterThan(value("0", new CompatibleModeValueConfiguration())), is(true));
        assertThat(value("1", new CompatibleModeValueConfiguration()).isGreaterThan(value("-1", new CompatibleModeValueConfiguration())), is(true));
        assertThat(value("1", new CompatibleModeValueConfiguration()).isGreaterThan(value(null, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value("1", new CompatibleModeValueConfiguration()).isGreaterThan(value("lala", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("1", new CompatibleModeValueConfiguration()).isGreaterThan(value("", new CompatibleModeValueConfiguration())), is(true));
        assertThat(value("1", new CompatibleModeValueConfiguration()).isGreaterThan(value(1.2, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("0", new CompatibleModeValueConfiguration()).isGreaterThan(value(true, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("0", new CompatibleModeValueConfiguration()).isGreaterThan(value(false, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("0", new CompatibleModeValueConfiguration()).isGreaterThan(value(1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("0", new CompatibleModeValueConfiguration()).isGreaterThan(value(0, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("0", new CompatibleModeValueConfiguration()).isGreaterThan(value(-1, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value("0", new CompatibleModeValueConfiguration()).isGreaterThan(value("1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("0", new CompatibleModeValueConfiguration()).isGreaterThan(value("0", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("0", new CompatibleModeValueConfiguration()).isGreaterThan(value("-1", new CompatibleModeValueConfiguration())), is(true));
        assertThat(value("0", new CompatibleModeValueConfiguration()).isGreaterThan(value(null, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value("0", new CompatibleModeValueConfiguration()).isGreaterThan(value("lala", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("0", new CompatibleModeValueConfiguration()).isGreaterThan(value("", new CompatibleModeValueConfiguration())), is(true));
        assertThat(value("0", new CompatibleModeValueConfiguration()).isGreaterThan(value(1.2, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("-1", new CompatibleModeValueConfiguration()).isGreaterThan(value(true, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("-1", new CompatibleModeValueConfiguration()).isGreaterThan(value(false, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value("-1", new CompatibleModeValueConfiguration()).isGreaterThan(value(1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("-1", new CompatibleModeValueConfiguration()).isGreaterThan(value(0, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("-1", new CompatibleModeValueConfiguration()).isGreaterThan(value(-1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("-1", new CompatibleModeValueConfiguration()).isGreaterThan(value("1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("-1", new CompatibleModeValueConfiguration()).isGreaterThan(value("0", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("-1", new CompatibleModeValueConfiguration()).isGreaterThan(value("-1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("-1", new CompatibleModeValueConfiguration()).isGreaterThan(value(null, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value("-1", new CompatibleModeValueConfiguration()).isGreaterThan(value("lala", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("-1", new CompatibleModeValueConfiguration()).isGreaterThan(value("", new CompatibleModeValueConfiguration())), is(true));
        assertThat(value("-1", new CompatibleModeValueConfiguration()).isGreaterThan(value(1.2, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(null, new CompatibleModeValueConfiguration()).isGreaterThan(value(true, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(null, new CompatibleModeValueConfiguration()).isGreaterThan(value(false, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(null, new CompatibleModeValueConfiguration()).isGreaterThan(value(1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(null, new CompatibleModeValueConfiguration()).isGreaterThan(value(0, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(null, new CompatibleModeValueConfiguration()).isGreaterThan(value(-1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(null, new CompatibleModeValueConfiguration()).isGreaterThan(value("1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(null, new CompatibleModeValueConfiguration()).isGreaterThan(value("0", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(null, new CompatibleModeValueConfiguration()).isGreaterThan(value("-1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(null, new CompatibleModeValueConfiguration()).isGreaterThan(value(null, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(null, new CompatibleModeValueConfiguration()).isGreaterThan(value("lala", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(null, new CompatibleModeValueConfiguration()).isGreaterThan(value("", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(null, new CompatibleModeValueConfiguration()).isGreaterThan(value(1.2, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("lala", new CompatibleModeValueConfiguration()).isGreaterThan(value(true, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("lala", new CompatibleModeValueConfiguration()).isGreaterThan(value(false, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value("lala", new CompatibleModeValueConfiguration()).isGreaterThan(value(1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("lala", new CompatibleModeValueConfiguration()).isGreaterThan(value(0, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("lala", new CompatibleModeValueConfiguration()).isGreaterThan(value(-1, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value("lala", new CompatibleModeValueConfiguration()).isGreaterThan(value("1", new CompatibleModeValueConfiguration())), is(true));
        assertThat(value("lala", new CompatibleModeValueConfiguration()).isGreaterThan(value("0", new CompatibleModeValueConfiguration())), is(true));
        assertThat(value("lala", new CompatibleModeValueConfiguration()).isGreaterThan(value("-1", new CompatibleModeValueConfiguration())), is(true));
        assertThat(value("lala", new CompatibleModeValueConfiguration()).isGreaterThan(value(null, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value("lala", new CompatibleModeValueConfiguration()).isGreaterThan(value("lala", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("lala", new CompatibleModeValueConfiguration()).isGreaterThan(value("", new CompatibleModeValueConfiguration())), is(true));
        assertThat(value("lala", new CompatibleModeValueConfiguration()).isGreaterThan(value(1.2, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("", new CompatibleModeValueConfiguration()).isGreaterThan(value(true, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("", new CompatibleModeValueConfiguration()).isGreaterThan(value(false, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("", new CompatibleModeValueConfiguration()).isGreaterThan(value(1, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("", new CompatibleModeValueConfiguration()).isGreaterThan(value(0, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("", new CompatibleModeValueConfiguration()).isGreaterThan(value(-1, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value("", new CompatibleModeValueConfiguration()).isGreaterThan(value("1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("", new CompatibleModeValueConfiguration()).isGreaterThan(value("0", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("", new CompatibleModeValueConfiguration()).isGreaterThan(value("-1", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("", new CompatibleModeValueConfiguration()).isGreaterThan(value(null, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("", new CompatibleModeValueConfiguration()).isGreaterThan(value("lala", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("", new CompatibleModeValueConfiguration()).isGreaterThan(value("", new CompatibleModeValueConfiguration())), is(false));
        assertThat(value("", new CompatibleModeValueConfiguration()).isGreaterThan(value(1.2, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(1.2, new CompatibleModeValueConfiguration()).isGreaterThan(value(true, new CompatibleModeValueConfiguration())), is(false));
        assertThat(value(1.2, new CompatibleModeValueConfiguration()).isGreaterThan(value(false, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(1.2, new CompatibleModeValueConfiguration()).isGreaterThan(value(1, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(1.2, new CompatibleModeValueConfiguration()).isGreaterThan(value(0, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(1.2, new CompatibleModeValueConfiguration()).isGreaterThan(value(-1, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(1.2, new CompatibleModeValueConfiguration()).isGreaterThan(value("1", new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(1.2, new CompatibleModeValueConfiguration()).isGreaterThan(value("0", new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(1.2, new CompatibleModeValueConfiguration()).isGreaterThan(value("-1", new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(1.2, new CompatibleModeValueConfiguration()).isGreaterThan(value(null, new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(1.2, new CompatibleModeValueConfiguration()).isGreaterThan(value("lala", new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(1.2, new CompatibleModeValueConfiguration()).isGreaterThan(value("", new CompatibleModeValueConfiguration())), is(true));
        assertThat(value(1.2, new CompatibleModeValueConfiguration()).isGreaterThan(value(1.2, new CompatibleModeValueConfiguration())), is(false));
    }
}