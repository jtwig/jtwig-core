package org.jtwig.util;

import org.jtwig.value.JtwigValue;
import org.jtwig.value.configuration.NamedValueConfiguration;
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
import static org.jtwig.value.configuration.NamedValueConfiguration.COMPATIBLE_MODE;
import static org.junit.Assert.assertThat;

public class JtwigValueTest {
    private JtwigValue underTest;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void asCollectionWhenNull() throws Exception {
        underTest = value(null, COMPATIBLE_MODE);
        Collection<Object> result = underTest.asCollection();

        assertThat(result.isEmpty(), is(true));
    }

    @Test
    public void asCollectionWhenIterable() throws Exception {
        List<String> list = asList("one");

        underTest = value((Iterable) list, COMPATIBLE_MODE);
        Collection<Object> result = underTest.asCollection();

        assertThat(result, hasItem("one"));
    }

    @Test
    public void asCollectionWhenArray() throws Exception {
        underTest = value(new String[]{"test"}, COMPATIBLE_MODE);
        Collection<Object> result = underTest.asCollection();

        assertThat(result, hasItem("test"));
    }

    @Test
    public void undefined() throws Exception {
        underTest = value(new Object(), COMPATIBLE_MODE);
        assertThat(underTest.isUndefined(), is(false));
    }

    @Test
    public void asStringWhenNull() throws Exception {
        underTest = value(null, COMPATIBLE_MODE);
        assertThat(underTest.asString(), is(""));
    }

    @Test
    public void asCollectionWhenMap() throws Exception {
        underTest = value(new HashMap<Object, Object>() {{
            put("a", "b");
        }}, COMPATIBLE_MODE);
        Collection<Object> result = underTest.asCollection();

        assertThat(result, hasItem("b"));
    }

    @Test
    public void asCollectionWhenSingleValue() throws Exception {
        underTest = value(1, COMPATIBLE_MODE);
        Collection<Object> result = underTest.asCollection();

        assertThat(result, hasItem(1));
    }

    @Test
    public void asMapWhenNull() throws Exception {
        underTest = value(null, COMPATIBLE_MODE);
        Map<Object, Object> result = underTest.asMap();

        assertThat(result.isEmpty(), is(true));
    }

    @Test
    public void asMapWhenSingleValue() throws Exception {
        underTest = value("ola", COMPATIBLE_MODE);
        Map<Object, Object> result = underTest.asMap();

        assertThat(result.isEmpty(), is(false));
        assertThat(result.get(0), is((Object) "ola"));
    }

    @Test
    public void mandatoryNumberInvalid() throws Exception {
        expectedException.expect(IllegalArgumentException.class);

        underTest = value("a", COMPATIBLE_MODE);
        underTest.mandatoryNumber();
    }

    @Test
    public void asMapWhenList() throws Exception {
        underTest = value(asList("one"), COMPATIBLE_MODE);
        Map<Object, Object> result = underTest.asMap();

        assertThat(result.get(0), is((Object) "one"));
    }

    @Test
    public void isPresentWhenNull() throws Exception {
        underTest = value(null, COMPATIBLE_MODE);
        boolean result = underTest.isPresent();
        assertThat(result, is(false));
    }

    @Test
    public void isPresentWhenNonNull() throws Exception {
        underTest = value(1, COMPATIBLE_MODE);
        boolean result = underTest.isPresent();
        assertThat(result, is(true));
    }

    @Test
    public void asMapWhenArray() throws Exception {
        underTest = value(new Integer[]{1, 2}, COMPATIBLE_MODE);
        Map<Object, Object> result = underTest.asMap();

        assertThat(result.get(0), is((Object) 1));
        assertThat(result.get(1), is((Object) 2));
    }

    @Test
    public void asMapWhenMap() throws Exception {
        underTest = value(new HashMap<Object, Object>() {{
            put("a", "b");
        }}, COMPATIBLE_MODE);
        Map<Object, Object> result = underTest.asMap();

        assertThat(result.get("a"), is((Object) "b"));
    }

    @Test
    public void equalComparator() throws Exception {
        assertThat(value(null, COMPATIBLE_MODE).isEqualTo(value(true, COMPATIBLE_MODE)), is(false));
        assertThat(value(null, COMPATIBLE_MODE).isEqualTo(value(false, COMPATIBLE_MODE)), is(true));
        assertThat(value(null, COMPATIBLE_MODE).isEqualTo(value(1, COMPATIBLE_MODE)), is(false));
        assertThat(value(null, COMPATIBLE_MODE).isEqualTo(value(0, COMPATIBLE_MODE)), is(true));
        assertThat(value(null, COMPATIBLE_MODE).isEqualTo(value(-1, COMPATIBLE_MODE)), is(false));
        assertThat(value(null, COMPATIBLE_MODE).isEqualTo(value("1", COMPATIBLE_MODE)), is(false));
        assertThat(value(null, COMPATIBLE_MODE).isEqualTo(value("0", COMPATIBLE_MODE)), is(false));
        assertThat(value(null, COMPATIBLE_MODE).isEqualTo(value("-1", COMPATIBLE_MODE)), is(false));
        assertThat(value(null, COMPATIBLE_MODE).isEqualTo(value(null, COMPATIBLE_MODE)), is(true));
        assertThat(value(null, COMPATIBLE_MODE).isEqualTo(value("lala", COMPATIBLE_MODE)), is(false));
        assertThat(value(null, COMPATIBLE_MODE).isEqualTo(value("", COMPATIBLE_MODE)), is(true));
        assertThat(value(true, COMPATIBLE_MODE).isEqualTo(value(true, COMPATIBLE_MODE)), is(true));
        assertThat(value(true, COMPATIBLE_MODE).isEqualTo(value(false, COMPATIBLE_MODE)), is(false));
        assertThat(value(true, COMPATIBLE_MODE).isEqualTo(value(1, COMPATIBLE_MODE)), is(true));
        assertThat(value(true, COMPATIBLE_MODE).isEqualTo(value(0, COMPATIBLE_MODE)), is(false));
        assertThat(value(true, COMPATIBLE_MODE).isEqualTo(value(-1, COMPATIBLE_MODE)), is(true));
        assertThat(value(true, COMPATIBLE_MODE).isEqualTo(value("1", COMPATIBLE_MODE)), is(true));
        assertThat(value(true, COMPATIBLE_MODE).isEqualTo(value("0", COMPATIBLE_MODE)), is(false));
        assertThat(value(true, COMPATIBLE_MODE).isEqualTo(value("-1", COMPATIBLE_MODE)), is(true));
        assertThat(value(true, COMPATIBLE_MODE).isEqualTo(value(null, COMPATIBLE_MODE)), is(false));
        assertThat(value(true, COMPATIBLE_MODE).isEqualTo(value("lala", COMPATIBLE_MODE)), is(true));
        assertThat(value(true, COMPATIBLE_MODE).isEqualTo(value("", COMPATIBLE_MODE)), is(false));
        assertThat(value(false, COMPATIBLE_MODE).isEqualTo(value(true, COMPATIBLE_MODE)), is(false));
        assertThat(value(false, COMPATIBLE_MODE).isEqualTo(value(false, COMPATIBLE_MODE)), is(true));
        assertThat(value(false, COMPATIBLE_MODE).isEqualTo(value(1, COMPATIBLE_MODE)), is(false));
        assertThat(value(false, COMPATIBLE_MODE).isEqualTo(value(0, COMPATIBLE_MODE)), is(true));
        assertThat(value(false, COMPATIBLE_MODE).isEqualTo(value(-1, COMPATIBLE_MODE)), is(false));
        assertThat(value(false, COMPATIBLE_MODE).isEqualTo(value("1", COMPATIBLE_MODE)), is(false));
        assertThat(value(false, COMPATIBLE_MODE).isEqualTo(value("0", COMPATIBLE_MODE)), is(true));
        assertThat(value(false, COMPATIBLE_MODE).isEqualTo(value("-1", COMPATIBLE_MODE)), is(false));
        assertThat(value(false, COMPATIBLE_MODE).isEqualTo(value(null, COMPATIBLE_MODE)), is(true));
        assertThat(value(false, COMPATIBLE_MODE).isEqualTo(value("lala", COMPATIBLE_MODE)), is(false));
        assertThat(value(false, COMPATIBLE_MODE).isEqualTo(value("", COMPATIBLE_MODE)), is(true));
        assertThat(value(1, COMPATIBLE_MODE).isEqualTo(value(true, COMPATIBLE_MODE)), is(true));
        assertThat(value(1, COMPATIBLE_MODE).isEqualTo(value(false, COMPATIBLE_MODE)), is(false));
        assertThat(value(1, COMPATIBLE_MODE).isEqualTo(value(1, COMPATIBLE_MODE)), is(true));
        assertThat(value(1, COMPATIBLE_MODE).isEqualTo(value(0, COMPATIBLE_MODE)), is(false));
        assertThat(value(1, COMPATIBLE_MODE).isEqualTo(value(-1, COMPATIBLE_MODE)), is(false));
        assertThat(value(1, COMPATIBLE_MODE).isEqualTo(value("1", COMPATIBLE_MODE)), is(true));
        assertThat(value(1, COMPATIBLE_MODE).isEqualTo(value("0", COMPATIBLE_MODE)), is(false));
        assertThat(value(1, COMPATIBLE_MODE).isEqualTo(value("-1", COMPATIBLE_MODE)), is(false));
        assertThat(value(1, COMPATIBLE_MODE).isEqualTo(value(null, COMPATIBLE_MODE)), is(false));
        assertThat(value(1, COMPATIBLE_MODE).isEqualTo(value("lala", COMPATIBLE_MODE)), is(false));
        assertThat(value(1, COMPATIBLE_MODE).isEqualTo(value("", COMPATIBLE_MODE)), is(false));
        assertThat(value(0, COMPATIBLE_MODE).isEqualTo(value(true, COMPATIBLE_MODE)), is(false));
        assertThat(value(0, COMPATIBLE_MODE).isEqualTo(value(false, COMPATIBLE_MODE)), is(true));
        assertThat(value(0, COMPATIBLE_MODE).isEqualTo(value(1, COMPATIBLE_MODE)), is(false));
        assertThat(value(0, COMPATIBLE_MODE).isEqualTo(value(0, COMPATIBLE_MODE)), is(true));
        assertThat(value(0, COMPATIBLE_MODE).isEqualTo(value(-1, COMPATIBLE_MODE)), is(false));
        assertThat(value(0, COMPATIBLE_MODE).isEqualTo(value("1", COMPATIBLE_MODE)), is(false));
        assertThat(value(0, COMPATIBLE_MODE).isEqualTo(value("0", COMPATIBLE_MODE)), is(true));
        assertThat(value(0, COMPATIBLE_MODE).isEqualTo(value("-1", COMPATIBLE_MODE)), is(false));
        assertThat(value(0, COMPATIBLE_MODE).isEqualTo(value(null, COMPATIBLE_MODE)), is(true));
        assertThat(value(0, COMPATIBLE_MODE).isEqualTo(value("lala", COMPATIBLE_MODE)), is(true));
        assertThat(value(0, COMPATIBLE_MODE).isEqualTo(value("", COMPATIBLE_MODE)), is(true));
        assertThat(value(-1, COMPATIBLE_MODE).isEqualTo(value(true, COMPATIBLE_MODE)), is(true));
        assertThat(value(-1, COMPATIBLE_MODE).isEqualTo(value(false, COMPATIBLE_MODE)), is(false));
        assertThat(value(-1, COMPATIBLE_MODE).isEqualTo(value(1, COMPATIBLE_MODE)), is(false));
        assertThat(value(-1, COMPATIBLE_MODE).isEqualTo(value(0, COMPATIBLE_MODE)), is(false));
        assertThat(value(-1, COMPATIBLE_MODE).isEqualTo(value(-1, COMPATIBLE_MODE)), is(true));
        assertThat(value(-1, COMPATIBLE_MODE).isEqualTo(value("1", COMPATIBLE_MODE)), is(false));
        assertThat(value(-1, COMPATIBLE_MODE).isEqualTo(value("0", COMPATIBLE_MODE)), is(false));
        assertThat(value(-1, COMPATIBLE_MODE).isEqualTo(value("-1", COMPATIBLE_MODE)), is(true));
        assertThat(value(-1, COMPATIBLE_MODE).isEqualTo(value(null, COMPATIBLE_MODE)), is(false));
        assertThat(value(-1, COMPATIBLE_MODE).isEqualTo(value("lala", COMPATIBLE_MODE)), is(false));
        assertThat(value(-1, COMPATIBLE_MODE).isEqualTo(value("", COMPATIBLE_MODE)), is(false));
        assertThat(value("", COMPATIBLE_MODE).isEqualTo(value(true, COMPATIBLE_MODE)), is(false));
        assertThat(value("", COMPATIBLE_MODE).isEqualTo(value(false, COMPATIBLE_MODE)), is(true));
        assertThat(value("", COMPATIBLE_MODE).isEqualTo(value(1, COMPATIBLE_MODE)), is(false));
        assertThat(value("", COMPATIBLE_MODE).isEqualTo(value(0, COMPATIBLE_MODE)), is(true));
        assertThat(value("", COMPATIBLE_MODE).isEqualTo(value(-1, COMPATIBLE_MODE)), is(false));
        assertThat(value("", COMPATIBLE_MODE).isEqualTo(value("1", COMPATIBLE_MODE)), is(false));
        assertThat(value("", COMPATIBLE_MODE).isEqualTo(value("0", COMPATIBLE_MODE)), is(false));
        assertThat(value("", COMPATIBLE_MODE).isEqualTo(value("-1", COMPATIBLE_MODE)), is(false));
        assertThat(value("", COMPATIBLE_MODE).isEqualTo(value(null, COMPATIBLE_MODE)), is(true));
        assertThat(value("", COMPATIBLE_MODE).isEqualTo(value("lala", COMPATIBLE_MODE)), is(false));
        assertThat(value("", COMPATIBLE_MODE).isEqualTo(value("", COMPATIBLE_MODE)), is(true));
        assertThat(value("1", COMPATIBLE_MODE).isEqualTo(value(true, COMPATIBLE_MODE)), is(true));
        assertThat(value("1", COMPATIBLE_MODE).isEqualTo(value(false, COMPATIBLE_MODE)), is(false));
        assertThat(value("1", COMPATIBLE_MODE).isEqualTo(value(1, COMPATIBLE_MODE)), is(true));
        assertThat(value("1", COMPATIBLE_MODE).isEqualTo(value(0, COMPATIBLE_MODE)), is(false));
        assertThat(value("1", COMPATIBLE_MODE).isEqualTo(value(-1, COMPATIBLE_MODE)), is(false));
        assertThat(value("1", COMPATIBLE_MODE).isEqualTo(value("1", COMPATIBLE_MODE)), is(true));
        assertThat(value("1", COMPATIBLE_MODE).isEqualTo(value("0", COMPATIBLE_MODE)), is(false));
        assertThat(value("1", COMPATIBLE_MODE).isEqualTo(value("-1", COMPATIBLE_MODE)), is(false));
        assertThat(value("1", COMPATIBLE_MODE).isEqualTo(value(null, COMPATIBLE_MODE)), is(false));
        assertThat(value("1", COMPATIBLE_MODE).isEqualTo(value("lala", COMPATIBLE_MODE)), is(false));
        assertThat(value("1", COMPATIBLE_MODE).isEqualTo(value("", COMPATIBLE_MODE)), is(false));
        assertThat(value("0", COMPATIBLE_MODE).isEqualTo(value(true, COMPATIBLE_MODE)), is(false));
        assertThat(value("0", COMPATIBLE_MODE).isEqualTo(value(false, COMPATIBLE_MODE)), is(true));
        assertThat(value("0", COMPATIBLE_MODE).isEqualTo(value(1, COMPATIBLE_MODE)), is(false));
        assertThat(value("0", COMPATIBLE_MODE).isEqualTo(value(0, COMPATIBLE_MODE)), is(true));
        assertThat(value("0", COMPATIBLE_MODE).isEqualTo(value(-1, COMPATIBLE_MODE)), is(false));
        assertThat(value("0", COMPATIBLE_MODE).isEqualTo(value("1", COMPATIBLE_MODE)), is(false));
        assertThat(value("0", COMPATIBLE_MODE).isEqualTo(value("0", COMPATIBLE_MODE)), is(true));
        assertThat(value("0", COMPATIBLE_MODE).isEqualTo(value("-1", COMPATIBLE_MODE)), is(false));
        assertThat(value("0", COMPATIBLE_MODE).isEqualTo(value(null, COMPATIBLE_MODE)), is(false));
        assertThat(value("0", COMPATIBLE_MODE).isEqualTo(value("lala", COMPATIBLE_MODE)), is(false));
        assertThat(value("0", COMPATIBLE_MODE).isEqualTo(value("", COMPATIBLE_MODE)), is(false));
        assertThat(value("-1", COMPATIBLE_MODE).isEqualTo(value(true, COMPATIBLE_MODE)), is(true));
        assertThat(value("-1", COMPATIBLE_MODE).isEqualTo(value(false, COMPATIBLE_MODE)), is(false));
        assertThat(value("-1", COMPATIBLE_MODE).isEqualTo(value(1, COMPATIBLE_MODE)), is(false));
        assertThat(value("-1", COMPATIBLE_MODE).isEqualTo(value(0, COMPATIBLE_MODE)), is(false));
        assertThat(value("-1", COMPATIBLE_MODE).isEqualTo(value(-1, COMPATIBLE_MODE)), is(true));
        assertThat(value("-1", COMPATIBLE_MODE).isEqualTo(value("1", COMPATIBLE_MODE)), is(false));
        assertThat(value("-1", COMPATIBLE_MODE).isEqualTo(value("0", COMPATIBLE_MODE)), is(false));
        assertThat(value("-1", COMPATIBLE_MODE).isEqualTo(value("-1", COMPATIBLE_MODE)), is(true));
        assertThat(value("-1", COMPATIBLE_MODE).isEqualTo(value(null, COMPATIBLE_MODE)), is(false));
        assertThat(value("-1", COMPATIBLE_MODE).isEqualTo(value("lala", COMPATIBLE_MODE)), is(false));
        assertThat(value("-1", COMPATIBLE_MODE).isEqualTo(value("", COMPATIBLE_MODE)), is(false));
        assertThat(value("lala", COMPATIBLE_MODE).isEqualTo(value(true, COMPATIBLE_MODE)), is(true));
        assertThat(value("lala", COMPATIBLE_MODE).isEqualTo(value(false, COMPATIBLE_MODE)), is(false));
        assertThat(value("lala", COMPATIBLE_MODE).isEqualTo(value(1, COMPATIBLE_MODE)), is(false));
        assertThat(value("lala", COMPATIBLE_MODE).isEqualTo(value(0, COMPATIBLE_MODE)), is(true));
        assertThat(value("lala", COMPATIBLE_MODE).isEqualTo(value(-1, COMPATIBLE_MODE)), is(false));
        assertThat(value("lala", COMPATIBLE_MODE).isEqualTo(value("1", COMPATIBLE_MODE)), is(false));
        assertThat(value("lala", COMPATIBLE_MODE).isEqualTo(value("0", COMPATIBLE_MODE)), is(false));
        assertThat(value("lala", COMPATIBLE_MODE).isEqualTo(value("-1", COMPATIBLE_MODE)), is(false));
        assertThat(value("lala", COMPATIBLE_MODE).isEqualTo(value(null, COMPATIBLE_MODE)), is(false));
        assertThat(value("lala", COMPATIBLE_MODE).isEqualTo(value("lala", COMPATIBLE_MODE)), is(true));
        assertThat(value("lala", COMPATIBLE_MODE).isEqualTo(value("lalaa", COMPATIBLE_MODE)), is(false));
        assertThat(value("lala", COMPATIBLE_MODE).isEqualTo(value("", COMPATIBLE_MODE)), is(false));
    }

    @Test
    public void identical() throws Exception {
        assertThat(value(true, COMPATIBLE_MODE).isIdenticalTo(value(true, COMPATIBLE_MODE)), is(true));
        assertThat(value(true, COMPATIBLE_MODE).isIdenticalTo(value(false, COMPATIBLE_MODE)), is(false));
        assertThat(value(true, COMPATIBLE_MODE).isIdenticalTo(value(1, COMPATIBLE_MODE)), is(false));
        assertThat(value(true, COMPATIBLE_MODE).isIdenticalTo(value(0, COMPATIBLE_MODE)), is(false));
        assertThat(value(true, COMPATIBLE_MODE).isIdenticalTo(value(-1, COMPATIBLE_MODE)), is(false));
        assertThat(value(true, COMPATIBLE_MODE).isIdenticalTo(value("1", COMPATIBLE_MODE)), is(false));
        assertThat(value(true, COMPATIBLE_MODE).isIdenticalTo(value("0", COMPATIBLE_MODE)), is(false));
        assertThat(value(true, COMPATIBLE_MODE).isIdenticalTo(value("-1", COMPATIBLE_MODE)), is(false));
        assertThat(value(true, COMPATIBLE_MODE).isIdenticalTo(value(null, COMPATIBLE_MODE)), is(false));
        assertThat(value(true, COMPATIBLE_MODE).isIdenticalTo(value("lala", COMPATIBLE_MODE)), is(false));
        assertThat(value(true, COMPATIBLE_MODE).isIdenticalTo(value("", COMPATIBLE_MODE)), is(false));
        assertThat(value(true, COMPATIBLE_MODE).isIdenticalTo(value(1.2, COMPATIBLE_MODE)), is(false));
        assertThat(value(false, COMPATIBLE_MODE).isIdenticalTo(value(true, COMPATIBLE_MODE)), is(false));
        assertThat(value(false, COMPATIBLE_MODE).isIdenticalTo(value(false, COMPATIBLE_MODE)), is(true));
        assertThat(value(false, COMPATIBLE_MODE).isIdenticalTo(value(1, COMPATIBLE_MODE)), is(false));
        assertThat(value(false, COMPATIBLE_MODE).isIdenticalTo(value(0, COMPATIBLE_MODE)), is(false));
        assertThat(value(false, COMPATIBLE_MODE).isIdenticalTo(value(-1, COMPATIBLE_MODE)), is(false));
        assertThat(value(false, COMPATIBLE_MODE).isIdenticalTo(value("1", COMPATIBLE_MODE)), is(false));
        assertThat(value(false, COMPATIBLE_MODE).isIdenticalTo(value("0", COMPATIBLE_MODE)), is(false));
        assertThat(value(false, COMPATIBLE_MODE).isIdenticalTo(value("-1", COMPATIBLE_MODE)), is(false));
        assertThat(value(false, COMPATIBLE_MODE).isIdenticalTo(value(null, COMPATIBLE_MODE)), is(false));
        assertThat(value(false, COMPATIBLE_MODE).isIdenticalTo(value("lala", COMPATIBLE_MODE)), is(false));
        assertThat(value(false, COMPATIBLE_MODE).isIdenticalTo(value("", COMPATIBLE_MODE)), is(false));
        assertThat(value(false, COMPATIBLE_MODE).isIdenticalTo(value(1.2, COMPATIBLE_MODE)), is(false));
        assertThat(value(1, COMPATIBLE_MODE).isIdenticalTo(value(true, COMPATIBLE_MODE)), is(false));
        assertThat(value(1, COMPATIBLE_MODE).isIdenticalTo(value(false, COMPATIBLE_MODE)), is(false));
        assertThat(value(1, COMPATIBLE_MODE).isIdenticalTo(value(1, COMPATIBLE_MODE)), is(true));
        assertThat(value(1, COMPATIBLE_MODE).isIdenticalTo(value(0, COMPATIBLE_MODE)), is(false));
        assertThat(value(1, COMPATIBLE_MODE).isIdenticalTo(value(-1, COMPATIBLE_MODE)), is(false));
        assertThat(value(1, COMPATIBLE_MODE).isIdenticalTo(value("1", COMPATIBLE_MODE)), is(false));
        assertThat(value(1, COMPATIBLE_MODE).isIdenticalTo(value("0", COMPATIBLE_MODE)), is(false));
        assertThat(value(1, COMPATIBLE_MODE).isIdenticalTo(value("-1", COMPATIBLE_MODE)), is(false));
        assertThat(value(1, COMPATIBLE_MODE).isIdenticalTo(value(null, COMPATIBLE_MODE)), is(false));
        assertThat(value(1, COMPATIBLE_MODE).isIdenticalTo(value("lala", COMPATIBLE_MODE)), is(false));
        assertThat(value(1, COMPATIBLE_MODE).isIdenticalTo(value("", COMPATIBLE_MODE)), is(false));
        assertThat(value(1, COMPATIBLE_MODE).isIdenticalTo(value(1.2, COMPATIBLE_MODE)), is(false));
        assertThat(value(0, COMPATIBLE_MODE).isIdenticalTo(value(true, COMPATIBLE_MODE)), is(false));
        assertThat(value(0, COMPATIBLE_MODE).isIdenticalTo(value(false, COMPATIBLE_MODE)), is(false));
        assertThat(value(0, COMPATIBLE_MODE).isIdenticalTo(value(1, COMPATIBLE_MODE)), is(false));
        assertThat(value(0, COMPATIBLE_MODE).isIdenticalTo(value(0, COMPATIBLE_MODE)), is(true));
        assertThat(value(0, COMPATIBLE_MODE).isIdenticalTo(value(-1, COMPATIBLE_MODE)), is(false));
        assertThat(value(0, COMPATIBLE_MODE).isIdenticalTo(value("1", COMPATIBLE_MODE)), is(false));
        assertThat(value(0, COMPATIBLE_MODE).isIdenticalTo(value("0", COMPATIBLE_MODE)), is(false));
        assertThat(value(0, COMPATIBLE_MODE).isIdenticalTo(value("-1", COMPATIBLE_MODE)), is(false));
        assertThat(value(0, COMPATIBLE_MODE).isIdenticalTo(value(null, COMPATIBLE_MODE)), is(false));
        assertThat(value(0, COMPATIBLE_MODE).isIdenticalTo(value("lala", COMPATIBLE_MODE)), is(false));
        assertThat(value(0, COMPATIBLE_MODE).isIdenticalTo(value("", COMPATIBLE_MODE)), is(false));
        assertThat(value(0, COMPATIBLE_MODE).isIdenticalTo(value(1.2, COMPATIBLE_MODE)), is(false));
        assertThat(value(-1, COMPATIBLE_MODE).isIdenticalTo(value(true, COMPATIBLE_MODE)), is(false));
        assertThat(value(-1, COMPATIBLE_MODE).isIdenticalTo(value(false, COMPATIBLE_MODE)), is(false));
        assertThat(value(-1, COMPATIBLE_MODE).isIdenticalTo(value(1, COMPATIBLE_MODE)), is(false));
        assertThat(value(-1, COMPATIBLE_MODE).isIdenticalTo(value(0, COMPATIBLE_MODE)), is(false));
        assertThat(value(-1, COMPATIBLE_MODE).isIdenticalTo(value(-1, COMPATIBLE_MODE)), is(true));
        assertThat(value(-1, COMPATIBLE_MODE).isIdenticalTo(value("1", COMPATIBLE_MODE)), is(false));
        assertThat(value(-1, COMPATIBLE_MODE).isIdenticalTo(value("0", COMPATIBLE_MODE)), is(false));
        assertThat(value(-1, COMPATIBLE_MODE).isIdenticalTo(value("-1", COMPATIBLE_MODE)), is(false));
        assertThat(value(-1, COMPATIBLE_MODE).isIdenticalTo(value(null, COMPATIBLE_MODE)), is(false));
        assertThat(value(-1, COMPATIBLE_MODE).isIdenticalTo(value("lala", COMPATIBLE_MODE)), is(false));
        assertThat(value(-1, COMPATIBLE_MODE).isIdenticalTo(value("", COMPATIBLE_MODE)), is(false));
        assertThat(value(-1, COMPATIBLE_MODE).isIdenticalTo(value(1.2, COMPATIBLE_MODE)), is(false));
        assertThat(value("1", COMPATIBLE_MODE).isIdenticalTo(value(true, COMPATIBLE_MODE)), is(false));
        assertThat(value("1", COMPATIBLE_MODE).isIdenticalTo(value(false, COMPATIBLE_MODE)), is(false));
        assertThat(value("1", COMPATIBLE_MODE).isIdenticalTo(value(1, COMPATIBLE_MODE)), is(false));
        assertThat(value("1", COMPATIBLE_MODE).isIdenticalTo(value(0, COMPATIBLE_MODE)), is(false));
        assertThat(value("1", COMPATIBLE_MODE).isIdenticalTo(value(-1, COMPATIBLE_MODE)), is(false));
        assertThat(value("1", COMPATIBLE_MODE).isIdenticalTo(value("1", COMPATIBLE_MODE)), is(true));
        assertThat(value("1", COMPATIBLE_MODE).isIdenticalTo(value("0", COMPATIBLE_MODE)), is(false));
        assertThat(value("1", COMPATIBLE_MODE).isIdenticalTo(value("-1", COMPATIBLE_MODE)), is(false));
        assertThat(value("1", COMPATIBLE_MODE).isIdenticalTo(value(null, COMPATIBLE_MODE)), is(false));
        assertThat(value("1", COMPATIBLE_MODE).isIdenticalTo(value("lala", COMPATIBLE_MODE)), is(false));
        assertThat(value("1", COMPATIBLE_MODE).isIdenticalTo(value("", COMPATIBLE_MODE)), is(false));
        assertThat(value("1", COMPATIBLE_MODE).isIdenticalTo(value(1.2, COMPATIBLE_MODE)), is(false));
        assertThat(value("0", COMPATIBLE_MODE).isIdenticalTo(value(true, COMPATIBLE_MODE)), is(false));
        assertThat(value("0", COMPATIBLE_MODE).isIdenticalTo(value(false, COMPATIBLE_MODE)), is(false));
        assertThat(value("0", COMPATIBLE_MODE).isIdenticalTo(value(1, COMPATIBLE_MODE)), is(false));
        assertThat(value("0", COMPATIBLE_MODE).isIdenticalTo(value(0, COMPATIBLE_MODE)), is(false));
        assertThat(value("0", COMPATIBLE_MODE).isIdenticalTo(value(-1, COMPATIBLE_MODE)), is(false));
        assertThat(value("0", COMPATIBLE_MODE).isIdenticalTo(value("1", COMPATIBLE_MODE)), is(false));
        assertThat(value("0", COMPATIBLE_MODE).isIdenticalTo(value("0", COMPATIBLE_MODE)), is(true));
        assertThat(value("0", COMPATIBLE_MODE).isIdenticalTo(value("-1", COMPATIBLE_MODE)), is(false));
        assertThat(value("0", COMPATIBLE_MODE).isIdenticalTo(value(null, COMPATIBLE_MODE)), is(false));
        assertThat(value("0", COMPATIBLE_MODE).isIdenticalTo(value("lala", COMPATIBLE_MODE)), is(false));
        assertThat(value("0", COMPATIBLE_MODE).isIdenticalTo(value("", COMPATIBLE_MODE)), is(false));
        assertThat(value("0", COMPATIBLE_MODE).isIdenticalTo(value(1.2, COMPATIBLE_MODE)), is(false));
        assertThat(value("-1", COMPATIBLE_MODE).isIdenticalTo(value(true, COMPATIBLE_MODE)), is(false));
        assertThat(value("-1", COMPATIBLE_MODE).isIdenticalTo(value(false, COMPATIBLE_MODE)), is(false));
        assertThat(value("-1", COMPATIBLE_MODE).isIdenticalTo(value(1, COMPATIBLE_MODE)), is(false));
        assertThat(value("-1", COMPATIBLE_MODE).isIdenticalTo(value(0, COMPATIBLE_MODE)), is(false));
        assertThat(value("-1", COMPATIBLE_MODE).isIdenticalTo(value(-1, COMPATIBLE_MODE)), is(false));
        assertThat(value("-1", COMPATIBLE_MODE).isIdenticalTo(value("1", COMPATIBLE_MODE)), is(false));
        assertThat(value("-1", COMPATIBLE_MODE).isIdenticalTo(value("0", COMPATIBLE_MODE)), is(false));
        assertThat(value("-1", COMPATIBLE_MODE).isIdenticalTo(value("-1", COMPATIBLE_MODE)), is(true));
        assertThat(value("-1", COMPATIBLE_MODE).isIdenticalTo(value(null, COMPATIBLE_MODE)), is(false));
        assertThat(value("-1", COMPATIBLE_MODE).isIdenticalTo(value("lala", COMPATIBLE_MODE)), is(false));
        assertThat(value("-1", COMPATIBLE_MODE).isIdenticalTo(value("", COMPATIBLE_MODE)), is(false));
        assertThat(value("-1", COMPATIBLE_MODE).isIdenticalTo(value(1.2, COMPATIBLE_MODE)), is(false));
        assertThat(value(null, COMPATIBLE_MODE).isIdenticalTo(value(true, COMPATIBLE_MODE)), is(false));
        assertThat(value(null, COMPATIBLE_MODE).isIdenticalTo(value(false, COMPATIBLE_MODE)), is(false));
        assertThat(value(null, COMPATIBLE_MODE).isIdenticalTo(value(1, COMPATIBLE_MODE)), is(false));
        assertThat(value(null, COMPATIBLE_MODE).isIdenticalTo(value(0, COMPATIBLE_MODE)), is(false));
        assertThat(value(null, COMPATIBLE_MODE).isIdenticalTo(value(-1, COMPATIBLE_MODE)), is(false));
        assertThat(value(null, COMPATIBLE_MODE).isIdenticalTo(value("1", COMPATIBLE_MODE)), is(false));
        assertThat(value(null, COMPATIBLE_MODE).isIdenticalTo(value("0", COMPATIBLE_MODE)), is(false));
        assertThat(value(null, COMPATIBLE_MODE).isIdenticalTo(value("-1", COMPATIBLE_MODE)), is(false));
        assertThat(value(null, COMPATIBLE_MODE).isIdenticalTo(value(null, COMPATIBLE_MODE)), is(true));
        assertThat(value(null, COMPATIBLE_MODE).isIdenticalTo(value("lala", COMPATIBLE_MODE)), is(false));
        assertThat(value(null, COMPATIBLE_MODE).isIdenticalTo(value("", COMPATIBLE_MODE)), is(false));
        assertThat(value(null, COMPATIBLE_MODE).isIdenticalTo(value(1.2, COMPATIBLE_MODE)), is(false));
        assertThat(value("lala", COMPATIBLE_MODE).isIdenticalTo(value(true, COMPATIBLE_MODE)), is(false));
        assertThat(value("lala", COMPATIBLE_MODE).isIdenticalTo(value(false, COMPATIBLE_MODE)), is(false));
        assertThat(value("lala", COMPATIBLE_MODE).isIdenticalTo(value(1, COMPATIBLE_MODE)), is(false));
        assertThat(value("lala", COMPATIBLE_MODE).isIdenticalTo(value(0, COMPATIBLE_MODE)), is(false));
        assertThat(value("lala", COMPATIBLE_MODE).isIdenticalTo(value(-1, COMPATIBLE_MODE)), is(false));
        assertThat(value("lala", COMPATIBLE_MODE).isIdenticalTo(value("1", COMPATIBLE_MODE)), is(false));
        assertThat(value("lala", COMPATIBLE_MODE).isIdenticalTo(value("0", COMPATIBLE_MODE)), is(false));
        assertThat(value("lala", COMPATIBLE_MODE).isIdenticalTo(value("-1", COMPATIBLE_MODE)), is(false));
        assertThat(value("lala", COMPATIBLE_MODE).isIdenticalTo(value(null, COMPATIBLE_MODE)), is(false));
        assertThat(value("lala", COMPATIBLE_MODE).isIdenticalTo(value("lala", COMPATIBLE_MODE)), is(true));
        assertThat(value("lala", COMPATIBLE_MODE).isIdenticalTo(value("lalaa", COMPATIBLE_MODE)), is(false));
        assertThat(value("lala", COMPATIBLE_MODE).isIdenticalTo(value("", COMPATIBLE_MODE)), is(false));
        assertThat(value("lala", COMPATIBLE_MODE).isIdenticalTo(value(1.2, COMPATIBLE_MODE)), is(false));
        assertThat(value("", COMPATIBLE_MODE).isIdenticalTo(value(true, COMPATIBLE_MODE)), is(false));
        assertThat(value("", COMPATIBLE_MODE).isIdenticalTo(value(false, COMPATIBLE_MODE)), is(false));
        assertThat(value("", COMPATIBLE_MODE).isIdenticalTo(value(1, COMPATIBLE_MODE)), is(false));
        assertThat(value("", COMPATIBLE_MODE).isIdenticalTo(value(0, COMPATIBLE_MODE)), is(false));
        assertThat(value("", COMPATIBLE_MODE).isIdenticalTo(value(-1, COMPATIBLE_MODE)), is(false));
        assertThat(value("", COMPATIBLE_MODE).isIdenticalTo(value("1", COMPATIBLE_MODE)), is(false));
        assertThat(value("", COMPATIBLE_MODE).isIdenticalTo(value("0", COMPATIBLE_MODE)), is(false));
        assertThat(value("", COMPATIBLE_MODE).isIdenticalTo(value("-1", COMPATIBLE_MODE)), is(false));
        assertThat(value("", COMPATIBLE_MODE).isIdenticalTo(value(null, COMPATIBLE_MODE)), is(false));
        assertThat(value("", COMPATIBLE_MODE).isIdenticalTo(value("lala", COMPATIBLE_MODE)), is(false));
        assertThat(value("", COMPATIBLE_MODE).isIdenticalTo(value("", COMPATIBLE_MODE)), is(true));
        assertThat(value("", COMPATIBLE_MODE).isIdenticalTo(value(1.2, COMPATIBLE_MODE)), is(false));
        assertThat(value(1.2, COMPATIBLE_MODE).isIdenticalTo(value(true, COMPATIBLE_MODE)), is(false));
        assertThat(value(1.2, COMPATIBLE_MODE).isIdenticalTo(value(false, COMPATIBLE_MODE)), is(false));
        assertThat(value(1.2, COMPATIBLE_MODE).isIdenticalTo(value(1, COMPATIBLE_MODE)), is(false));
        assertThat(value(1.2, COMPATIBLE_MODE).isIdenticalTo(value(0, COMPATIBLE_MODE)), is(false));
        assertThat(value(1.2, COMPATIBLE_MODE).isIdenticalTo(value(-1, COMPATIBLE_MODE)), is(false));
        assertThat(value(1.2, COMPATIBLE_MODE).isIdenticalTo(value("1", COMPATIBLE_MODE)), is(false));
        assertThat(value(1.2, COMPATIBLE_MODE).isIdenticalTo(value("0", COMPATIBLE_MODE)), is(false));
        assertThat(value(1.2, COMPATIBLE_MODE).isIdenticalTo(value("-1", COMPATIBLE_MODE)), is(false));
        assertThat(value(1.2, COMPATIBLE_MODE).isIdenticalTo(value(null, COMPATIBLE_MODE)), is(false));
        assertThat(value(1.2, COMPATIBLE_MODE).isIdenticalTo(value("lala", COMPATIBLE_MODE)), is(false));
        assertThat(value(1.2, COMPATIBLE_MODE).isIdenticalTo(value("", COMPATIBLE_MODE)), is(false));
        assertThat(value(1.2, COMPATIBLE_MODE).isIdenticalTo(value(1.2, COMPATIBLE_MODE)), is(true));
    }

    @Test
    public void lower() throws Exception {
        assertThat(value(true, COMPATIBLE_MODE).isLowerThan(value(true, COMPATIBLE_MODE)), is(false));
        assertThat(value(true, COMPATIBLE_MODE).isLowerThan(value(false, COMPATIBLE_MODE)), is(false));
        assertThat(value(true, COMPATIBLE_MODE).isLowerThan(value(1, COMPATIBLE_MODE)), is(false));
        assertThat(value(true, COMPATIBLE_MODE).isLowerThan(value(0, COMPATIBLE_MODE)), is(false));
        assertThat(value(true, COMPATIBLE_MODE).isLowerThan(value(-1, COMPATIBLE_MODE)), is(false));
        assertThat(value(true, COMPATIBLE_MODE).isLowerThan(value("1", COMPATIBLE_MODE)), is(false));
        assertThat(value(true, COMPATIBLE_MODE).isLowerThan(value("0", COMPATIBLE_MODE)), is(false));
        assertThat(value(true, COMPATIBLE_MODE).isLowerThan(value("-1", COMPATIBLE_MODE)), is(false));
        assertThat(value(true, COMPATIBLE_MODE).isLowerThan(value(null, COMPATIBLE_MODE)), is(false));
        assertThat(value(true, COMPATIBLE_MODE).isLowerThan(value("lala", COMPATIBLE_MODE)), is(false));
        assertThat(value(true, COMPATIBLE_MODE).isLowerThan(value("", COMPATIBLE_MODE)), is(false));
        assertThat(value(true, COMPATIBLE_MODE).isLowerThan(value(1.2, COMPATIBLE_MODE)), is(false));
        assertThat(value(false, COMPATIBLE_MODE).isLowerThan(value(true, COMPATIBLE_MODE)), is(true));
        assertThat(value(false, COMPATIBLE_MODE).isLowerThan(value(false, COMPATIBLE_MODE)), is(false));
        assertThat(value(false, COMPATIBLE_MODE).isLowerThan(value(1, COMPATIBLE_MODE)), is(true));
        assertThat(value(false, COMPATIBLE_MODE).isLowerThan(value(0, COMPATIBLE_MODE)), is(false));
        assertThat(value(false, COMPATIBLE_MODE).isLowerThan(value(-1, COMPATIBLE_MODE)), is(true));
        assertThat(value(false, COMPATIBLE_MODE).isLowerThan(value("1", COMPATIBLE_MODE)), is(true));
        assertThat(value(false, COMPATIBLE_MODE).isLowerThan(value("0", COMPATIBLE_MODE)), is(false));
        assertThat(value(false, COMPATIBLE_MODE).isLowerThan(value("-1", COMPATIBLE_MODE)), is(true));
        assertThat(value(false, COMPATIBLE_MODE).isLowerThan(value(null, COMPATIBLE_MODE)), is(false));
        assertThat(value(false, COMPATIBLE_MODE).isLowerThan(value("lala", COMPATIBLE_MODE)), is(true));
        assertThat(value(false, COMPATIBLE_MODE).isLowerThan(value("", COMPATIBLE_MODE)), is(false));
        assertThat(value(false, COMPATIBLE_MODE).isLowerThan(value(1.2, COMPATIBLE_MODE)), is(true));
        assertThat(value(1, COMPATIBLE_MODE).isLowerThan(value(true, COMPATIBLE_MODE)), is(false));
        assertThat(value(1, COMPATIBLE_MODE).isLowerThan(value(false, COMPATIBLE_MODE)), is(false));
        assertThat(value(1, COMPATIBLE_MODE).isLowerThan(value(1, COMPATIBLE_MODE)), is(false));
        assertThat(value(1, COMPATIBLE_MODE).isLowerThan(value(0, COMPATIBLE_MODE)), is(false));
        assertThat(value(1, COMPATIBLE_MODE).isLowerThan(value(-1, COMPATIBLE_MODE)), is(false));
        assertThat(value(1, COMPATIBLE_MODE).isLowerThan(value("1", COMPATIBLE_MODE)), is(false));
        assertThat(value(1, COMPATIBLE_MODE).isLowerThan(value("0", COMPATIBLE_MODE)), is(false));
        assertThat(value(1, COMPATIBLE_MODE).isLowerThan(value("-1", COMPATIBLE_MODE)), is(false));
        assertThat(value(1, COMPATIBLE_MODE).isLowerThan(value(null, COMPATIBLE_MODE)), is(false));
        assertThat(value(1, COMPATIBLE_MODE).isLowerThan(value("lala", COMPATIBLE_MODE)), is(false));
        assertThat(value(1, COMPATIBLE_MODE).isLowerThan(value("", COMPATIBLE_MODE)), is(false));
        assertThat(value(1, COMPATIBLE_MODE).isLowerThan(value(1.2, COMPATIBLE_MODE)), is(true));
        assertThat(value(0, COMPATIBLE_MODE).isLowerThan(value(true, COMPATIBLE_MODE)), is(true));
        assertThat(value(0, COMPATIBLE_MODE).isLowerThan(value(false, COMPATIBLE_MODE)), is(false));
        assertThat(value(0, COMPATIBLE_MODE).isLowerThan(value(1, COMPATIBLE_MODE)), is(true));
        assertThat(value(0, COMPATIBLE_MODE).isLowerThan(value(0, COMPATIBLE_MODE)), is(false));
        assertThat(value(0, COMPATIBLE_MODE).isLowerThan(value(-1, COMPATIBLE_MODE)), is(false));
        assertThat(value(0, COMPATIBLE_MODE).isLowerThan(value("1", COMPATIBLE_MODE)), is(true));
        assertThat(value(0, COMPATIBLE_MODE).isLowerThan(value("0", COMPATIBLE_MODE)), is(false));
        assertThat(value(0, COMPATIBLE_MODE).isLowerThan(value("-1", COMPATIBLE_MODE)), is(false));
        assertThat(value(0, COMPATIBLE_MODE).isLowerThan(value(null, COMPATIBLE_MODE)), is(false));
        assertThat(value(0, COMPATIBLE_MODE).isLowerThan(value("lala", COMPATIBLE_MODE)), is(false));
        assertThat(value(0, COMPATIBLE_MODE).isLowerThan(value("", COMPATIBLE_MODE)), is(false));
        assertThat(value(0, COMPATIBLE_MODE).isLowerThan(value(1.2, COMPATIBLE_MODE)), is(true));
        assertThat(value(-1, COMPATIBLE_MODE).isLowerThan(value(true, COMPATIBLE_MODE)), is(false));
        assertThat(value(-1, COMPATIBLE_MODE).isLowerThan(value(false, COMPATIBLE_MODE)), is(false));
        assertThat(value(-1, COMPATIBLE_MODE).isLowerThan(value(1, COMPATIBLE_MODE)), is(true));
        assertThat(value(-1, COMPATIBLE_MODE).isLowerThan(value(0, COMPATIBLE_MODE)), is(true));
        assertThat(value(-1, COMPATIBLE_MODE).isLowerThan(value(-1, COMPATIBLE_MODE)), is(false));
        assertThat(value(-1, COMPATIBLE_MODE).isLowerThan(value("1", COMPATIBLE_MODE)), is(true));
        assertThat(value(-1, COMPATIBLE_MODE).isLowerThan(value("0", COMPATIBLE_MODE)), is(true));
        assertThat(value(-1, COMPATIBLE_MODE).isLowerThan(value("-1", COMPATIBLE_MODE)), is(false));
        assertThat(value(-1, COMPATIBLE_MODE).isLowerThan(value(null, COMPATIBLE_MODE)), is(false));
        assertThat(value(-1, COMPATIBLE_MODE).isLowerThan(value("lala", COMPATIBLE_MODE)), is(true));
        assertThat(value(-1, COMPATIBLE_MODE).isLowerThan(value("", COMPATIBLE_MODE)), is(true));
        assertThat(value(-1, COMPATIBLE_MODE).isLowerThan(value(1.2, COMPATIBLE_MODE)), is(true));
        assertThat(value("1", COMPATIBLE_MODE).isLowerThan(value(true, COMPATIBLE_MODE)), is(false));
        assertThat(value("1", COMPATIBLE_MODE).isLowerThan(value(false, COMPATIBLE_MODE)), is(false));
        assertThat(value("1", COMPATIBLE_MODE).isLowerThan(value(1, COMPATIBLE_MODE)), is(false));
        assertThat(value("1", COMPATIBLE_MODE).isLowerThan(value(0, COMPATIBLE_MODE)), is(false));
        assertThat(value("1", COMPATIBLE_MODE).isLowerThan(value(-1, COMPATIBLE_MODE)), is(false));
        assertThat(value("1", COMPATIBLE_MODE).isLowerThan(value("1", COMPATIBLE_MODE)), is(false));
        assertThat(value("1", COMPATIBLE_MODE).isLowerThan(value("0", COMPATIBLE_MODE)), is(false));
        assertThat(value("1", COMPATIBLE_MODE).isLowerThan(value("-1", COMPATIBLE_MODE)), is(false));
        assertThat(value("1", COMPATIBLE_MODE).isLowerThan(value(null, COMPATIBLE_MODE)), is(false));
        assertThat(value("1", COMPATIBLE_MODE).isLowerThan(value("lala", COMPATIBLE_MODE)), is(true));
        assertThat(value("1", COMPATIBLE_MODE).isLowerThan(value("", COMPATIBLE_MODE)), is(false));
        assertThat(value("1", COMPATIBLE_MODE).isLowerThan(value(1.2, COMPATIBLE_MODE)), is(true));
        assertThat(value("0", COMPATIBLE_MODE).isLowerThan(value(true, COMPATIBLE_MODE)), is(true));
        assertThat(value("0", COMPATIBLE_MODE).isLowerThan(value(false, COMPATIBLE_MODE)), is(false));
        assertThat(value("0", COMPATIBLE_MODE).isLowerThan(value(1, COMPATIBLE_MODE)), is(true));
        assertThat(value("0", COMPATIBLE_MODE).isLowerThan(value(0, COMPATIBLE_MODE)), is(false));
        assertThat(value("0", COMPATIBLE_MODE).isLowerThan(value(-1, COMPATIBLE_MODE)), is(false));
        assertThat(value("0", COMPATIBLE_MODE).isLowerThan(value("1", COMPATIBLE_MODE)), is(true));
        assertThat(value("0", COMPATIBLE_MODE).isLowerThan(value("0", COMPATIBLE_MODE)), is(false));
        assertThat(value("0", COMPATIBLE_MODE).isLowerThan(value("-1", COMPATIBLE_MODE)), is(false));
        assertThat(value("0", COMPATIBLE_MODE).isLowerThan(value(null, COMPATIBLE_MODE)), is(false));
        assertThat(value("0", COMPATIBLE_MODE).isLowerThan(value("lala", COMPATIBLE_MODE)), is(true));
        assertThat(value("0", COMPATIBLE_MODE).isLowerThan(value("", COMPATIBLE_MODE)), is(false));
        assertThat(value("0", COMPATIBLE_MODE).isLowerThan(value(1.2, COMPATIBLE_MODE)), is(true));
        assertThat(value("-1", COMPATIBLE_MODE).isLowerThan(value(true, COMPATIBLE_MODE)), is(false));
        assertThat(value("-1", COMPATIBLE_MODE).isLowerThan(value(false, COMPATIBLE_MODE)), is(false));
        assertThat(value("-1", COMPATIBLE_MODE).isLowerThan(value(1, COMPATIBLE_MODE)), is(true));
        assertThat(value("-1", COMPATIBLE_MODE).isLowerThan(value(0, COMPATIBLE_MODE)), is(true));
        assertThat(value("-1", COMPATIBLE_MODE).isLowerThan(value(-1, COMPATIBLE_MODE)), is(false));
        assertThat(value("-1", COMPATIBLE_MODE).isLowerThan(value("1", COMPATIBLE_MODE)), is(true));
        assertThat(value("-1", COMPATIBLE_MODE).isLowerThan(value("0", COMPATIBLE_MODE)), is(true));
        assertThat(value("-1", COMPATIBLE_MODE).isLowerThan(value("-1", COMPATIBLE_MODE)), is(false));
        assertThat(value("-1", COMPATIBLE_MODE).isLowerThan(value(null, COMPATIBLE_MODE)), is(false));
        assertThat(value("-1", COMPATIBLE_MODE).isLowerThan(value("lala", COMPATIBLE_MODE)), is(true));
        assertThat(value("-1", COMPATIBLE_MODE).isLowerThan(value("", COMPATIBLE_MODE)), is(false));
        assertThat(value("-1", COMPATIBLE_MODE).isLowerThan(value(1.2, COMPATIBLE_MODE)), is(true));
        assertThat(value(null, COMPATIBLE_MODE).isLowerThan(value(true, COMPATIBLE_MODE)), is(true));
        assertThat(value(null, COMPATIBLE_MODE).isLowerThan(value(false, COMPATIBLE_MODE)), is(false));
        assertThat(value(null, COMPATIBLE_MODE).isLowerThan(value(1, COMPATIBLE_MODE)), is(true));
        assertThat(value(null, COMPATIBLE_MODE).isLowerThan(value(0, COMPATIBLE_MODE)), is(false));
        assertThat(value(null, COMPATIBLE_MODE).isLowerThan(value(-1, COMPATIBLE_MODE)), is(true));
        assertThat(value(null, COMPATIBLE_MODE).isLowerThan(value("1", COMPATIBLE_MODE)), is(true));
        assertThat(value(null, COMPATIBLE_MODE).isLowerThan(value("0", COMPATIBLE_MODE)), is(true));
        assertThat(value(null, COMPATIBLE_MODE).isLowerThan(value("-1", COMPATIBLE_MODE)), is(true));
        assertThat(value(null, COMPATIBLE_MODE).isLowerThan(value(null, COMPATIBLE_MODE)), is(false));
        assertThat(value(null, COMPATIBLE_MODE).isLowerThan(value("lala", COMPATIBLE_MODE)), is(true));
        assertThat(value(null, COMPATIBLE_MODE).isLowerThan(value("", COMPATIBLE_MODE)), is(false));
        assertThat(value(null, COMPATIBLE_MODE).isLowerThan(value(1.2, COMPATIBLE_MODE)), is(true));
        assertThat(value("lala", COMPATIBLE_MODE).isLowerThan(value(true, COMPATIBLE_MODE)), is(false));
        assertThat(value("lala", COMPATIBLE_MODE).isLowerThan(value(false, COMPATIBLE_MODE)), is(false));
        assertThat(value("lala", COMPATIBLE_MODE).isLowerThan(value(1, COMPATIBLE_MODE)), is(true));
        assertThat(value("lala", COMPATIBLE_MODE).isLowerThan(value(0, COMPATIBLE_MODE)), is(false));
        assertThat(value("lala", COMPATIBLE_MODE).isLowerThan(value(-1, COMPATIBLE_MODE)), is(false));
        assertThat(value("lala", COMPATIBLE_MODE).isLowerThan(value("1", COMPATIBLE_MODE)), is(false));
        assertThat(value("lala", COMPATIBLE_MODE).isLowerThan(value("0", COMPATIBLE_MODE)), is(false));
        assertThat(value("lala", COMPATIBLE_MODE).isLowerThan(value("-1", COMPATIBLE_MODE)), is(false));
        assertThat(value("lala", COMPATIBLE_MODE).isLowerThan(value(null, COMPATIBLE_MODE)), is(false));
        assertThat(value("lala", COMPATIBLE_MODE).isLowerThan(value("lala", COMPATIBLE_MODE)), is(false));
        assertThat(value("lala", COMPATIBLE_MODE).isLowerThan(value("", COMPATIBLE_MODE)), is(false));
        assertThat(value("lala", COMPATIBLE_MODE).isLowerThan(value(1.2, COMPATIBLE_MODE)), is(true));
        assertThat(value("", COMPATIBLE_MODE).isLowerThan(value(true, COMPATIBLE_MODE)), is(true));
        assertThat(value("", COMPATIBLE_MODE).isLowerThan(value(false, COMPATIBLE_MODE)), is(false));
        assertThat(value("", COMPATIBLE_MODE).isLowerThan(value(1, COMPATIBLE_MODE)), is(true));
        assertThat(value("", COMPATIBLE_MODE).isLowerThan(value(0, COMPATIBLE_MODE)), is(false));
        assertThat(value("", COMPATIBLE_MODE).isLowerThan(value(-1, COMPATIBLE_MODE)), is(false));
        assertThat(value("", COMPATIBLE_MODE).isLowerThan(value("1", COMPATIBLE_MODE)), is(true));
        assertThat(value("", COMPATIBLE_MODE).isLowerThan(value("0", COMPATIBLE_MODE)), is(true));
        assertThat(value("", COMPATIBLE_MODE).isLowerThan(value("-1", COMPATIBLE_MODE)), is(true));
        assertThat(value("", COMPATIBLE_MODE).isLowerThan(value(null, COMPATIBLE_MODE)), is(false));
        assertThat(value("", COMPATIBLE_MODE).isLowerThan(value("lala", COMPATIBLE_MODE)), is(true));
        assertThat(value("", COMPATIBLE_MODE).isLowerThan(value("", COMPATIBLE_MODE)), is(false));
        assertThat(value("", COMPATIBLE_MODE).isLowerThan(value(1.2, COMPATIBLE_MODE)), is(true));
        assertThat(value(1.2, COMPATIBLE_MODE).isLowerThan(value(true, COMPATIBLE_MODE)), is(false));
        assertThat(value(1.2, COMPATIBLE_MODE).isLowerThan(value(false, COMPATIBLE_MODE)), is(false));
        assertThat(value(1.2, COMPATIBLE_MODE).isLowerThan(value(1, COMPATIBLE_MODE)), is(false));
        assertThat(value(1.2, COMPATIBLE_MODE).isLowerThan(value(0, COMPATIBLE_MODE)), is(false));
        assertThat(value(1.2, COMPATIBLE_MODE).isLowerThan(value(-1, COMPATIBLE_MODE)), is(false));
        assertThat(value(1.2, COMPATIBLE_MODE).isLowerThan(value("1", COMPATIBLE_MODE)), is(false));
        assertThat(value(1.2, COMPATIBLE_MODE).isLowerThan(value("0", COMPATIBLE_MODE)), is(false));
        assertThat(value(1.2, COMPATIBLE_MODE).isLowerThan(value("-1", COMPATIBLE_MODE)), is(false));
        assertThat(value(1.2, COMPATIBLE_MODE).isLowerThan(value(null, COMPATIBLE_MODE)), is(false));
        assertThat(value(1.2, COMPATIBLE_MODE).isLowerThan(value("lala", COMPATIBLE_MODE)), is(false));
        assertThat(value(1.2, COMPATIBLE_MODE).isLowerThan(value("", COMPATIBLE_MODE)), is(false));
        assertThat(value(1.2, COMPATIBLE_MODE).isLowerThan(value(1.2, COMPATIBLE_MODE)), is(false));
    }

    @Test
    public void greater() throws Exception {
        assertThat(value(true, COMPATIBLE_MODE).isGreaterThan(value(true, COMPATIBLE_MODE)), is(false));
        assertThat(value(true, COMPATIBLE_MODE).isGreaterThan(value(false, COMPATIBLE_MODE)), is(true));
        assertThat(value(true, COMPATIBLE_MODE).isGreaterThan(value(1, COMPATIBLE_MODE)), is(false));
        assertThat(value(true, COMPATIBLE_MODE).isGreaterThan(value(0, COMPATIBLE_MODE)), is(true));
        assertThat(value(true, COMPATIBLE_MODE).isGreaterThan(value(-1, COMPATIBLE_MODE)), is(false));
        assertThat(value(true, COMPATIBLE_MODE).isGreaterThan(value("1", COMPATIBLE_MODE)), is(false));
        assertThat(value(true, COMPATIBLE_MODE).isGreaterThan(value("0", COMPATIBLE_MODE)), is(true));
        assertThat(value(true, COMPATIBLE_MODE).isGreaterThan(value("-1", COMPATIBLE_MODE)), is(false));
        assertThat(value(true, COMPATIBLE_MODE).isGreaterThan(value(null, COMPATIBLE_MODE)), is(true));
        assertThat(value(true, COMPATIBLE_MODE).isGreaterThan(value("lala", COMPATIBLE_MODE)), is(false));
        assertThat(value(true, COMPATIBLE_MODE).isGreaterThan(value("", COMPATIBLE_MODE)), is(true));
        assertThat(value(true, COMPATIBLE_MODE).isGreaterThan(value(1.2, COMPATIBLE_MODE)), is(false));
        assertThat(value(false, COMPATIBLE_MODE).isGreaterThan(value(true, COMPATIBLE_MODE)), is(false));
        assertThat(value(false, COMPATIBLE_MODE).isGreaterThan(value(false, COMPATIBLE_MODE)), is(false));
        assertThat(value(false, COMPATIBLE_MODE).isGreaterThan(value(1, COMPATIBLE_MODE)), is(false));
        assertThat(value(false, COMPATIBLE_MODE).isGreaterThan(value(0, COMPATIBLE_MODE)), is(false));
        assertThat(value(false, COMPATIBLE_MODE).isGreaterThan(value(-1, COMPATIBLE_MODE)), is(false));
        assertThat(value(false, COMPATIBLE_MODE).isGreaterThan(value("1", COMPATIBLE_MODE)), is(false));
        assertThat(value(false, COMPATIBLE_MODE).isGreaterThan(value("0", COMPATIBLE_MODE)), is(false));
        assertThat(value(false, COMPATIBLE_MODE).isGreaterThan(value("-1", COMPATIBLE_MODE)), is(false));
        assertThat(value(false, COMPATIBLE_MODE).isGreaterThan(value(null, COMPATIBLE_MODE)), is(false));
        assertThat(value(false, COMPATIBLE_MODE).isGreaterThan(value("lala", COMPATIBLE_MODE)), is(false));
        assertThat(value(false, COMPATIBLE_MODE).isGreaterThan(value("", COMPATIBLE_MODE)), is(false));
        assertThat(value(false, COMPATIBLE_MODE).isGreaterThan(value(1.2, COMPATIBLE_MODE)), is(false));
        assertThat(value(1, COMPATIBLE_MODE).isGreaterThan(value(true, COMPATIBLE_MODE)), is(false));
        assertThat(value(1, COMPATIBLE_MODE).isGreaterThan(value(false, COMPATIBLE_MODE)), is(true));
        assertThat(value(1, COMPATIBLE_MODE).isGreaterThan(value(1, COMPATIBLE_MODE)), is(false));
        assertThat(value(1, COMPATIBLE_MODE).isGreaterThan(value(0, COMPATIBLE_MODE)), is(true));
        assertThat(value(1, COMPATIBLE_MODE).isGreaterThan(value(-1, COMPATIBLE_MODE)), is(true));
        assertThat(value(1, COMPATIBLE_MODE).isGreaterThan(value("1", COMPATIBLE_MODE)), is(false));
        assertThat(value(1, COMPATIBLE_MODE).isGreaterThan(value("0", COMPATIBLE_MODE)), is(true));
        assertThat(value(1, COMPATIBLE_MODE).isGreaterThan(value("-1", COMPATIBLE_MODE)), is(true));
        assertThat(value(1, COMPATIBLE_MODE).isGreaterThan(value(null, COMPATIBLE_MODE)), is(true));
        assertThat(value(1, COMPATIBLE_MODE).isGreaterThan(value("lala", COMPATIBLE_MODE)), is(true));
        assertThat(value(1, COMPATIBLE_MODE).isGreaterThan(value("", COMPATIBLE_MODE)), is(true));
        assertThat(value(1, COMPATIBLE_MODE).isGreaterThan(value(1.2, COMPATIBLE_MODE)), is(false));
        assertThat(value(0, COMPATIBLE_MODE).isGreaterThan(value(true, COMPATIBLE_MODE)), is(false));
        assertThat(value(0, COMPATIBLE_MODE).isGreaterThan(value(false, COMPATIBLE_MODE)), is(false));
        assertThat(value(0, COMPATIBLE_MODE).isGreaterThan(value(1, COMPATIBLE_MODE)), is(false));
        assertThat(value(0, COMPATIBLE_MODE).isGreaterThan(value(0, COMPATIBLE_MODE)), is(false));
        assertThat(value(0, COMPATIBLE_MODE).isGreaterThan(value(-1, COMPATIBLE_MODE)), is(true));
        assertThat(value(0, COMPATIBLE_MODE).isGreaterThan(value("1", COMPATIBLE_MODE)), is(false));
        assertThat(value(0, COMPATIBLE_MODE).isGreaterThan(value("0", COMPATIBLE_MODE)), is(false));
        assertThat(value(0, COMPATIBLE_MODE).isGreaterThan(value("-1", COMPATIBLE_MODE)), is(true));
        assertThat(value(0, COMPATIBLE_MODE).isGreaterThan(value(null, COMPATIBLE_MODE)), is(false));
        assertThat(value(0, COMPATIBLE_MODE).isGreaterThan(value("lala", COMPATIBLE_MODE)), is(false));
        assertThat(value(0, COMPATIBLE_MODE).isGreaterThan(value("", COMPATIBLE_MODE)), is(false));
        assertThat(value(0, COMPATIBLE_MODE).isGreaterThan(value(1.2, COMPATIBLE_MODE)), is(false));
        assertThat(value(-1, COMPATIBLE_MODE).isGreaterThan(value(true, COMPATIBLE_MODE)), is(false));
        assertThat(value(-1, COMPATIBLE_MODE).isGreaterThan(value(false, COMPATIBLE_MODE)), is(true));
        assertThat(value(-1, COMPATIBLE_MODE).isGreaterThan(value(1, COMPATIBLE_MODE)), is(false));
        assertThat(value(-1, COMPATIBLE_MODE).isGreaterThan(value(0, COMPATIBLE_MODE)), is(false));
        assertThat(value(-1, COMPATIBLE_MODE).isGreaterThan(value(-1, COMPATIBLE_MODE)), is(false));
        assertThat(value(-1, COMPATIBLE_MODE).isGreaterThan(value("1", COMPATIBLE_MODE)), is(false));
        assertThat(value(-1, COMPATIBLE_MODE).isGreaterThan(value("0", COMPATIBLE_MODE)), is(false));
        assertThat(value(-1, COMPATIBLE_MODE).isGreaterThan(value("-1", COMPATIBLE_MODE)), is(false));
        assertThat(value(-1, COMPATIBLE_MODE).isGreaterThan(value(null, COMPATIBLE_MODE)), is(true));
        assertThat(value(-1, COMPATIBLE_MODE).isGreaterThan(value("lala", COMPATIBLE_MODE)), is(false));
        assertThat(value(-1, COMPATIBLE_MODE).isGreaterThan(value("", COMPATIBLE_MODE)), is(false));
        assertThat(value(-1, COMPATIBLE_MODE).isGreaterThan(value(1.2, COMPATIBLE_MODE)), is(false));
        assertThat(value("1", COMPATIBLE_MODE).isGreaterThan(value(true, COMPATIBLE_MODE)), is(false));
        assertThat(value("1", COMPATIBLE_MODE).isGreaterThan(value(false, COMPATIBLE_MODE)), is(true));
        assertThat(value("1", COMPATIBLE_MODE).isGreaterThan(value(1, COMPATIBLE_MODE)), is(false));
        assertThat(value("1", COMPATIBLE_MODE).isGreaterThan(value(0, COMPATIBLE_MODE)), is(true));
        assertThat(value("1", COMPATIBLE_MODE).isGreaterThan(value(-1, COMPATIBLE_MODE)), is(true));
        assertThat(value("1", COMPATIBLE_MODE).isGreaterThan(value("1", COMPATIBLE_MODE)), is(false));
        assertThat(value("1", COMPATIBLE_MODE).isGreaterThan(value("0", COMPATIBLE_MODE)), is(true));
        assertThat(value("1", COMPATIBLE_MODE).isGreaterThan(value("-1", COMPATIBLE_MODE)), is(true));
        assertThat(value("1", COMPATIBLE_MODE).isGreaterThan(value(null, COMPATIBLE_MODE)), is(true));
        assertThat(value("1", COMPATIBLE_MODE).isGreaterThan(value("lala", COMPATIBLE_MODE)), is(false));
        assertThat(value("1", COMPATIBLE_MODE).isGreaterThan(value("", COMPATIBLE_MODE)), is(true));
        assertThat(value("1", COMPATIBLE_MODE).isGreaterThan(value(1.2, COMPATIBLE_MODE)), is(false));
        assertThat(value("0", COMPATIBLE_MODE).isGreaterThan(value(true, COMPATIBLE_MODE)), is(false));
        assertThat(value("0", COMPATIBLE_MODE).isGreaterThan(value(false, COMPATIBLE_MODE)), is(false));
        assertThat(value("0", COMPATIBLE_MODE).isGreaterThan(value(1, COMPATIBLE_MODE)), is(false));
        assertThat(value("0", COMPATIBLE_MODE).isGreaterThan(value(0, COMPATIBLE_MODE)), is(false));
        assertThat(value("0", COMPATIBLE_MODE).isGreaterThan(value(-1, COMPATIBLE_MODE)), is(true));
        assertThat(value("0", COMPATIBLE_MODE).isGreaterThan(value("1", COMPATIBLE_MODE)), is(false));
        assertThat(value("0", COMPATIBLE_MODE).isGreaterThan(value("0", COMPATIBLE_MODE)), is(false));
        assertThat(value("0", COMPATIBLE_MODE).isGreaterThan(value("-1", COMPATIBLE_MODE)), is(true));
        assertThat(value("0", COMPATIBLE_MODE).isGreaterThan(value(null, COMPATIBLE_MODE)), is(true));
        assertThat(value("0", COMPATIBLE_MODE).isGreaterThan(value("lala", COMPATIBLE_MODE)), is(false));
        assertThat(value("0", COMPATIBLE_MODE).isGreaterThan(value("", COMPATIBLE_MODE)), is(true));
        assertThat(value("0", COMPATIBLE_MODE).isGreaterThan(value(1.2, COMPATIBLE_MODE)), is(false));
        assertThat(value("-1", COMPATIBLE_MODE).isGreaterThan(value(true, COMPATIBLE_MODE)), is(false));
        assertThat(value("-1", COMPATIBLE_MODE).isGreaterThan(value(false, COMPATIBLE_MODE)), is(true));
        assertThat(value("-1", COMPATIBLE_MODE).isGreaterThan(value(1, COMPATIBLE_MODE)), is(false));
        assertThat(value("-1", COMPATIBLE_MODE).isGreaterThan(value(0, COMPATIBLE_MODE)), is(false));
        assertThat(value("-1", COMPATIBLE_MODE).isGreaterThan(value(-1, COMPATIBLE_MODE)), is(false));
        assertThat(value("-1", COMPATIBLE_MODE).isGreaterThan(value("1", COMPATIBLE_MODE)), is(false));
        assertThat(value("-1", COMPATIBLE_MODE).isGreaterThan(value("0", COMPATIBLE_MODE)), is(false));
        assertThat(value("-1", COMPATIBLE_MODE).isGreaterThan(value("-1", COMPATIBLE_MODE)), is(false));
        assertThat(value("-1", COMPATIBLE_MODE).isGreaterThan(value(null, COMPATIBLE_MODE)), is(true));
        assertThat(value("-1", COMPATIBLE_MODE).isGreaterThan(value("lala", COMPATIBLE_MODE)), is(false));
        assertThat(value("-1", COMPATIBLE_MODE).isGreaterThan(value("", COMPATIBLE_MODE)), is(true));
        assertThat(value("-1", COMPATIBLE_MODE).isGreaterThan(value(1.2, COMPATIBLE_MODE)), is(false));
        assertThat(value(null, COMPATIBLE_MODE).isGreaterThan(value(true, COMPATIBLE_MODE)), is(false));
        assertThat(value(null, COMPATIBLE_MODE).isGreaterThan(value(false, COMPATIBLE_MODE)), is(false));
        assertThat(value(null, COMPATIBLE_MODE).isGreaterThan(value(1, COMPATIBLE_MODE)), is(false));
        assertThat(value(null, COMPATIBLE_MODE).isGreaterThan(value(0, COMPATIBLE_MODE)), is(false));
        assertThat(value(null, COMPATIBLE_MODE).isGreaterThan(value(-1, COMPATIBLE_MODE)), is(false));
        assertThat(value(null, COMPATIBLE_MODE).isGreaterThan(value("1", COMPATIBLE_MODE)), is(false));
        assertThat(value(null, COMPATIBLE_MODE).isGreaterThan(value("0", COMPATIBLE_MODE)), is(false));
        assertThat(value(null, COMPATIBLE_MODE).isGreaterThan(value("-1", COMPATIBLE_MODE)), is(false));
        assertThat(value(null, COMPATIBLE_MODE).isGreaterThan(value(null, COMPATIBLE_MODE)), is(false));
        assertThat(value(null, COMPATIBLE_MODE).isGreaterThan(value("lala", COMPATIBLE_MODE)), is(false));
        assertThat(value(null, COMPATIBLE_MODE).isGreaterThan(value("", COMPATIBLE_MODE)), is(false));
        assertThat(value(null, COMPATIBLE_MODE).isGreaterThan(value(1.2, COMPATIBLE_MODE)), is(false));
        assertThat(value("lala", COMPATIBLE_MODE).isGreaterThan(value(true, COMPATIBLE_MODE)), is(false));
        assertThat(value("lala", COMPATIBLE_MODE).isGreaterThan(value(false, COMPATIBLE_MODE)), is(true));
        assertThat(value("lala", COMPATIBLE_MODE).isGreaterThan(value(1, COMPATIBLE_MODE)), is(false));
        assertThat(value("lala", COMPATIBLE_MODE).isGreaterThan(value(0, COMPATIBLE_MODE)), is(false));
        assertThat(value("lala", COMPATIBLE_MODE).isGreaterThan(value(-1, COMPATIBLE_MODE)), is(true));
        assertThat(value("lala", COMPATIBLE_MODE).isGreaterThan(value("1", COMPATIBLE_MODE)), is(true));
        assertThat(value("lala", COMPATIBLE_MODE).isGreaterThan(value("0", COMPATIBLE_MODE)), is(true));
        assertThat(value("lala", COMPATIBLE_MODE).isGreaterThan(value("-1", COMPATIBLE_MODE)), is(true));
        assertThat(value("lala", COMPATIBLE_MODE).isGreaterThan(value(null, COMPATIBLE_MODE)), is(true));
        assertThat(value("lala", COMPATIBLE_MODE).isGreaterThan(value("lala", COMPATIBLE_MODE)), is(false));
        assertThat(value("lala", COMPATIBLE_MODE).isGreaterThan(value("", COMPATIBLE_MODE)), is(true));
        assertThat(value("lala", COMPATIBLE_MODE).isGreaterThan(value(1.2, COMPATIBLE_MODE)), is(false));
        assertThat(value("", COMPATIBLE_MODE).isGreaterThan(value(true, COMPATIBLE_MODE)), is(false));
        assertThat(value("", COMPATIBLE_MODE).isGreaterThan(value(false, COMPATIBLE_MODE)), is(false));
        assertThat(value("", COMPATIBLE_MODE).isGreaterThan(value(1, COMPATIBLE_MODE)), is(false));
        assertThat(value("", COMPATIBLE_MODE).isGreaterThan(value(0, COMPATIBLE_MODE)), is(false));
        assertThat(value("", COMPATIBLE_MODE).isGreaterThan(value(-1, COMPATIBLE_MODE)), is(true));
        assertThat(value("", COMPATIBLE_MODE).isGreaterThan(value("1", COMPATIBLE_MODE)), is(false));
        assertThat(value("", COMPATIBLE_MODE).isGreaterThan(value("0", COMPATIBLE_MODE)), is(false));
        assertThat(value("", COMPATIBLE_MODE).isGreaterThan(value("-1", COMPATIBLE_MODE)), is(false));
        assertThat(value("", COMPATIBLE_MODE).isGreaterThan(value(null, COMPATIBLE_MODE)), is(false));
        assertThat(value("", COMPATIBLE_MODE).isGreaterThan(value("lala", COMPATIBLE_MODE)), is(false));
        assertThat(value("", COMPATIBLE_MODE).isGreaterThan(value("", COMPATIBLE_MODE)), is(false));
        assertThat(value("", COMPATIBLE_MODE).isGreaterThan(value(1.2, COMPATIBLE_MODE)), is(false));
        assertThat(value(1.2, COMPATIBLE_MODE).isGreaterThan(value(true, COMPATIBLE_MODE)), is(false));
        assertThat(value(1.2, COMPATIBLE_MODE).isGreaterThan(value(false, COMPATIBLE_MODE)), is(true));
        assertThat(value(1.2, COMPATIBLE_MODE).isGreaterThan(value(1, COMPATIBLE_MODE)), is(true));
        assertThat(value(1.2, COMPATIBLE_MODE).isGreaterThan(value(0, COMPATIBLE_MODE)), is(true));
        assertThat(value(1.2, COMPATIBLE_MODE).isGreaterThan(value(-1, COMPATIBLE_MODE)), is(true));
        assertThat(value(1.2, COMPATIBLE_MODE).isGreaterThan(value("1", COMPATIBLE_MODE)), is(true));
        assertThat(value(1.2, COMPATIBLE_MODE).isGreaterThan(value("0", COMPATIBLE_MODE)), is(true));
        assertThat(value(1.2, COMPATIBLE_MODE).isGreaterThan(value("-1", COMPATIBLE_MODE)), is(true));
        assertThat(value(1.2, COMPATIBLE_MODE).isGreaterThan(value(null, COMPATIBLE_MODE)), is(true));
        assertThat(value(1.2, COMPATIBLE_MODE).isGreaterThan(value("lala", COMPATIBLE_MODE)), is(true));
        assertThat(value(1.2, COMPATIBLE_MODE).isGreaterThan(value("", COMPATIBLE_MODE)), is(true));
        assertThat(value(1.2, COMPATIBLE_MODE).isGreaterThan(value(1.2, COMPATIBLE_MODE)), is(false));
    }
}