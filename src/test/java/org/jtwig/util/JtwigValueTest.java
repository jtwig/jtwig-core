package org.jtwig.util;

import org.jtwig.value.JtwigValue;
import org.jtwig.value.configuration.DefaultValueConfiguration;
import org.jtwig.value.environment.ValueEnvironment;
import org.jtwig.value.environment.ValueEnvironmentFactory;
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
    public static final ValueEnvironment VALUE_ENVIRONMENT = new ValueEnvironmentFactory().crete(new DefaultValueConfiguration());
    private JtwigValue underTest;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void asCollectionWhenNull() throws Exception {
        underTest = value(null, VALUE_ENVIRONMENT);
        Collection<Object> result = underTest.asCollection();

        assertThat(result.isEmpty(), is(true));
    }

    @Test
    public void asCollectionWhenIterable() throws Exception {
        List<String> list = asList("one");

        underTest = value((Iterable) list, VALUE_ENVIRONMENT);
        Collection<Object> result = underTest.asCollection();

        assertThat(result, hasItem("one"));
    }

    @Test
    public void asCollectionWhenArray() throws Exception {
        underTest = value(new String[]{"test"}, VALUE_ENVIRONMENT);
        Collection<Object> result = underTest.asCollection();

        assertThat(result, hasItem("test"));
    }

    @Test
    public void undefined() throws Exception {
        underTest = value(new Object(), VALUE_ENVIRONMENT);
        assertThat(underTest.isUndefined(), is(false));
    }

    @Test
    public void asStringWhenNull() throws Exception {
        underTest = value(null, VALUE_ENVIRONMENT);
        assertThat(underTest.asString(), is(""));
    }

    @Test
    public void asCollectionWhenMap() throws Exception {
        underTest = value(new HashMap<Object, Object>() {{
            put("a", "b");
        }}, VALUE_ENVIRONMENT);
        Collection<Object> result = underTest.asCollection();

        assertThat(result, hasItem("b"));
    }

    @Test
    public void asCollectionWhenSingleValue() throws Exception {
        underTest = value(1, VALUE_ENVIRONMENT);
        Collection<Object> result = underTest.asCollection();

        assertThat(result, hasItem(1));
    }

    @Test
    public void asMapWhenNull() throws Exception {
        underTest = value(null, VALUE_ENVIRONMENT);
        Map<Object, Object> result = underTest.asMap();

        assertThat(result.isEmpty(), is(true));
    }

    @Test
    public void asMapWhenSingleValue() throws Exception {
        underTest = value("ola", VALUE_ENVIRONMENT);
        Map<Object, Object> result = underTest.asMap();

        assertThat(result.isEmpty(), is(false));
        assertThat(result.get(0), is((Object) "ola"));
    }

    @Test
    public void mandatoryNumberInvalid() throws Exception {
        expectedException.expect(IllegalArgumentException.class);

        underTest = value("a", VALUE_ENVIRONMENT);
        underTest.mandatoryNumber();
    }

    @Test
    public void asMapWhenList() throws Exception {
        underTest = value(asList("one"), VALUE_ENVIRONMENT);
        Map<Object, Object> result = underTest.asMap();

        assertThat(result.get(0), is((Object) "one"));
    }

    @Test
    public void isPresentWhenNull() throws Exception {
        underTest = value(null, VALUE_ENVIRONMENT);
        boolean result = underTest.isPresent();
        assertThat(result, is(false));
    }

    @Test
    public void isPresentWhenNonNull() throws Exception {
        underTest = value(1, VALUE_ENVIRONMENT);
        boolean result = underTest.isPresent();
        assertThat(result, is(true));
    }

    @Test
    public void asMapWhenArray() throws Exception {
        underTest = value(new Integer[]{1, 2}, VALUE_ENVIRONMENT);
        Map<Object, Object> result = underTest.asMap();

        assertThat(result.get(0), is((Object) 1));
        assertThat(result.get(1), is((Object) 2));
    }

    @Test
    public void asMapWhenMap() throws Exception {
        underTest = value(new HashMap<Object, Object>() {{
            put("a", "b");
        }}, VALUE_ENVIRONMENT);
        Map<Object, Object> result = underTest.asMap();

        assertThat(result.get("a"), is((Object) "b"));
    }

    @Test
    public void equalComparator() throws Exception {
        assertThat(value(null, VALUE_ENVIRONMENT).isEqualTo(value(true, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(null, VALUE_ENVIRONMENT).isEqualTo(value(false, VALUE_ENVIRONMENT)), is(true));
        assertThat(value(null, VALUE_ENVIRONMENT).isEqualTo(value(1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(null, VALUE_ENVIRONMENT).isEqualTo(value(0, VALUE_ENVIRONMENT)), is(true));
        assertThat(value(null, VALUE_ENVIRONMENT).isEqualTo(value(-1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(null, VALUE_ENVIRONMENT).isEqualTo(value("1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(null, VALUE_ENVIRONMENT).isEqualTo(value("0", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(null, VALUE_ENVIRONMENT).isEqualTo(value("-1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(null, VALUE_ENVIRONMENT).isEqualTo(value(null, VALUE_ENVIRONMENT)), is(true));
        assertThat(value(null, VALUE_ENVIRONMENT).isEqualTo(value("lala", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(null, VALUE_ENVIRONMENT).isEqualTo(value("", VALUE_ENVIRONMENT)), is(true));
        assertThat(value(true, VALUE_ENVIRONMENT).isEqualTo(value(true, VALUE_ENVIRONMENT)), is(true));
        assertThat(value(true, VALUE_ENVIRONMENT).isEqualTo(value(false, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(true, VALUE_ENVIRONMENT).isEqualTo(value(1, VALUE_ENVIRONMENT)), is(true));
        assertThat(value(true, VALUE_ENVIRONMENT).isEqualTo(value(0, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(true, VALUE_ENVIRONMENT).isEqualTo(value(-1, VALUE_ENVIRONMENT)), is(true));
        assertThat(value(true, VALUE_ENVIRONMENT).isEqualTo(value("1", VALUE_ENVIRONMENT)), is(true));
        assertThat(value(true, VALUE_ENVIRONMENT).isEqualTo(value("0", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(true, VALUE_ENVIRONMENT).isEqualTo(value("-1", VALUE_ENVIRONMENT)), is(true));
        assertThat(value(true, VALUE_ENVIRONMENT).isEqualTo(value(null, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(true, VALUE_ENVIRONMENT).isEqualTo(value("lala", VALUE_ENVIRONMENT)), is(true));
        assertThat(value(true, VALUE_ENVIRONMENT).isEqualTo(value("", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(false, VALUE_ENVIRONMENT).isEqualTo(value(true, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(false, VALUE_ENVIRONMENT).isEqualTo(value(false, VALUE_ENVIRONMENT)), is(true));
        assertThat(value(false, VALUE_ENVIRONMENT).isEqualTo(value(1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(false, VALUE_ENVIRONMENT).isEqualTo(value(0, VALUE_ENVIRONMENT)), is(true));
        assertThat(value(false, VALUE_ENVIRONMENT).isEqualTo(value(-1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(false, VALUE_ENVIRONMENT).isEqualTo(value("1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(false, VALUE_ENVIRONMENT).isEqualTo(value("0", VALUE_ENVIRONMENT)), is(true));
        assertThat(value(false, VALUE_ENVIRONMENT).isEqualTo(value("-1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(false, VALUE_ENVIRONMENT).isEqualTo(value(null, VALUE_ENVIRONMENT)), is(true));
        assertThat(value(false, VALUE_ENVIRONMENT).isEqualTo(value("lala", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(false, VALUE_ENVIRONMENT).isEqualTo(value("", VALUE_ENVIRONMENT)), is(true));
        assertThat(value(1, VALUE_ENVIRONMENT).isEqualTo(value(true, VALUE_ENVIRONMENT)), is(true));
        assertThat(value(1, VALUE_ENVIRONMENT).isEqualTo(value(false, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(1, VALUE_ENVIRONMENT).isEqualTo(value(1, VALUE_ENVIRONMENT)), is(true));
        assertThat(value(1, VALUE_ENVIRONMENT).isEqualTo(value(0, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(1, VALUE_ENVIRONMENT).isEqualTo(value(-1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(1, VALUE_ENVIRONMENT).isEqualTo(value("1", VALUE_ENVIRONMENT)), is(true));
        assertThat(value(1, VALUE_ENVIRONMENT).isEqualTo(value("0", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(1, VALUE_ENVIRONMENT).isEqualTo(value("-1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(1, VALUE_ENVIRONMENT).isEqualTo(value(null, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(1, VALUE_ENVIRONMENT).isEqualTo(value("lala", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(1, VALUE_ENVIRONMENT).isEqualTo(value("", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(0, VALUE_ENVIRONMENT).isEqualTo(value(true, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(0, VALUE_ENVIRONMENT).isEqualTo(value(false, VALUE_ENVIRONMENT)), is(true));
        assertThat(value(0, VALUE_ENVIRONMENT).isEqualTo(value(1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(0, VALUE_ENVIRONMENT).isEqualTo(value(0, VALUE_ENVIRONMENT)), is(true));
        assertThat(value(0, VALUE_ENVIRONMENT).isEqualTo(value(-1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(0, VALUE_ENVIRONMENT).isEqualTo(value("1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(0, VALUE_ENVIRONMENT).isEqualTo(value("0", VALUE_ENVIRONMENT)), is(true));
        assertThat(value(0, VALUE_ENVIRONMENT).isEqualTo(value("-1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(0, VALUE_ENVIRONMENT).isEqualTo(value(null, VALUE_ENVIRONMENT)), is(true));
        assertThat(value(0, VALUE_ENVIRONMENT).isEqualTo(value("lala", VALUE_ENVIRONMENT)), is(true));
        assertThat(value(0, VALUE_ENVIRONMENT).isEqualTo(value("", VALUE_ENVIRONMENT)), is(true));
        assertThat(value(-1, VALUE_ENVIRONMENT).isEqualTo(value(true, VALUE_ENVIRONMENT)), is(true));
        assertThat(value(-1, VALUE_ENVIRONMENT).isEqualTo(value(false, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(-1, VALUE_ENVIRONMENT).isEqualTo(value(1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(-1, VALUE_ENVIRONMENT).isEqualTo(value(0, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(-1, VALUE_ENVIRONMENT).isEqualTo(value(-1, VALUE_ENVIRONMENT)), is(true));
        assertThat(value(-1, VALUE_ENVIRONMENT).isEqualTo(value("1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(-1, VALUE_ENVIRONMENT).isEqualTo(value("0", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(-1, VALUE_ENVIRONMENT).isEqualTo(value("-1", VALUE_ENVIRONMENT)), is(true));
        assertThat(value(-1, VALUE_ENVIRONMENT).isEqualTo(value(null, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(-1, VALUE_ENVIRONMENT).isEqualTo(value("lala", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(-1, VALUE_ENVIRONMENT).isEqualTo(value("", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("", VALUE_ENVIRONMENT).isEqualTo(value(true, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("", VALUE_ENVIRONMENT).isEqualTo(value(false, VALUE_ENVIRONMENT)), is(true));
        assertThat(value("", VALUE_ENVIRONMENT).isEqualTo(value(1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("", VALUE_ENVIRONMENT).isEqualTo(value(0, VALUE_ENVIRONMENT)), is(true));
        assertThat(value("", VALUE_ENVIRONMENT).isEqualTo(value(-1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("", VALUE_ENVIRONMENT).isEqualTo(value("1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("", VALUE_ENVIRONMENT).isEqualTo(value("0", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("", VALUE_ENVIRONMENT).isEqualTo(value("-1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("", VALUE_ENVIRONMENT).isEqualTo(value(null, VALUE_ENVIRONMENT)), is(true));
        assertThat(value("", VALUE_ENVIRONMENT).isEqualTo(value("lala", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("", VALUE_ENVIRONMENT).isEqualTo(value("", VALUE_ENVIRONMENT)), is(true));
        assertThat(value("1", VALUE_ENVIRONMENT).isEqualTo(value(true, VALUE_ENVIRONMENT)), is(true));
        assertThat(value("1", VALUE_ENVIRONMENT).isEqualTo(value(false, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("1", VALUE_ENVIRONMENT).isEqualTo(value(1, VALUE_ENVIRONMENT)), is(true));
        assertThat(value("1", VALUE_ENVIRONMENT).isEqualTo(value(0, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("1", VALUE_ENVIRONMENT).isEqualTo(value(-1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("1", VALUE_ENVIRONMENT).isEqualTo(value("1", VALUE_ENVIRONMENT)), is(true));
        assertThat(value("1", VALUE_ENVIRONMENT).isEqualTo(value("0", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("1", VALUE_ENVIRONMENT).isEqualTo(value("-1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("1", VALUE_ENVIRONMENT).isEqualTo(value(null, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("1", VALUE_ENVIRONMENT).isEqualTo(value("lala", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("1", VALUE_ENVIRONMENT).isEqualTo(value("", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("0", VALUE_ENVIRONMENT).isEqualTo(value(true, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("0", VALUE_ENVIRONMENT).isEqualTo(value(false, VALUE_ENVIRONMENT)), is(true));
        assertThat(value("0", VALUE_ENVIRONMENT).isEqualTo(value(1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("0", VALUE_ENVIRONMENT).isEqualTo(value(0, VALUE_ENVIRONMENT)), is(true));
        assertThat(value("0", VALUE_ENVIRONMENT).isEqualTo(value(-1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("0", VALUE_ENVIRONMENT).isEqualTo(value("1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("0", VALUE_ENVIRONMENT).isEqualTo(value("0", VALUE_ENVIRONMENT)), is(true));
        assertThat(value("0", VALUE_ENVIRONMENT).isEqualTo(value("-1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("0", VALUE_ENVIRONMENT).isEqualTo(value(null, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("0", VALUE_ENVIRONMENT).isEqualTo(value("lala", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("0", VALUE_ENVIRONMENT).isEqualTo(value("", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("-1", VALUE_ENVIRONMENT).isEqualTo(value(true, VALUE_ENVIRONMENT)), is(true));
        assertThat(value("-1", VALUE_ENVIRONMENT).isEqualTo(value(false, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("-1", VALUE_ENVIRONMENT).isEqualTo(value(1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("-1", VALUE_ENVIRONMENT).isEqualTo(value(0, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("-1", VALUE_ENVIRONMENT).isEqualTo(value(-1, VALUE_ENVIRONMENT)), is(true));
        assertThat(value("-1", VALUE_ENVIRONMENT).isEqualTo(value("1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("-1", VALUE_ENVIRONMENT).isEqualTo(value("0", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("-1", VALUE_ENVIRONMENT).isEqualTo(value("-1", VALUE_ENVIRONMENT)), is(true));
        assertThat(value("-1", VALUE_ENVIRONMENT).isEqualTo(value(null, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("-1", VALUE_ENVIRONMENT).isEqualTo(value("lala", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("-1", VALUE_ENVIRONMENT).isEqualTo(value("", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("lala", VALUE_ENVIRONMENT).isEqualTo(value(true, VALUE_ENVIRONMENT)), is(true));
        assertThat(value("lala", VALUE_ENVIRONMENT).isEqualTo(value(false, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("lala", VALUE_ENVIRONMENT).isEqualTo(value(1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("lala", VALUE_ENVIRONMENT).isEqualTo(value(0, VALUE_ENVIRONMENT)), is(true));
        assertThat(value("lala", VALUE_ENVIRONMENT).isEqualTo(value(-1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("lala", VALUE_ENVIRONMENT).isEqualTo(value("1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("lala", VALUE_ENVIRONMENT).isEqualTo(value("0", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("lala", VALUE_ENVIRONMENT).isEqualTo(value("-1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("lala", VALUE_ENVIRONMENT).isEqualTo(value(null, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("lala", VALUE_ENVIRONMENT).isEqualTo(value("lala", VALUE_ENVIRONMENT)), is(true));
        assertThat(value("lala", VALUE_ENVIRONMENT).isEqualTo(value("lalaa", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("lala", VALUE_ENVIRONMENT).isEqualTo(value("", VALUE_ENVIRONMENT)), is(false));
    }

    @Test
    public void identical() throws Exception {
        assertThat(value(true, VALUE_ENVIRONMENT).isIdenticalTo(value(true, VALUE_ENVIRONMENT)), is(true));
        assertThat(value(true, VALUE_ENVIRONMENT).isIdenticalTo(value(false, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(true, VALUE_ENVIRONMENT).isIdenticalTo(value(1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(true, VALUE_ENVIRONMENT).isIdenticalTo(value(0, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(true, VALUE_ENVIRONMENT).isIdenticalTo(value(-1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(true, VALUE_ENVIRONMENT).isIdenticalTo(value("1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(true, VALUE_ENVIRONMENT).isIdenticalTo(value("0", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(true, VALUE_ENVIRONMENT).isIdenticalTo(value("-1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(true, VALUE_ENVIRONMENT).isIdenticalTo(value(null, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(true, VALUE_ENVIRONMENT).isIdenticalTo(value("lala", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(true, VALUE_ENVIRONMENT).isIdenticalTo(value("", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(true, VALUE_ENVIRONMENT).isIdenticalTo(value(1.2, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(false, VALUE_ENVIRONMENT).isIdenticalTo(value(true, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(false, VALUE_ENVIRONMENT).isIdenticalTo(value(false, VALUE_ENVIRONMENT)), is(true));
        assertThat(value(false, VALUE_ENVIRONMENT).isIdenticalTo(value(1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(false, VALUE_ENVIRONMENT).isIdenticalTo(value(0, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(false, VALUE_ENVIRONMENT).isIdenticalTo(value(-1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(false, VALUE_ENVIRONMENT).isIdenticalTo(value("1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(false, VALUE_ENVIRONMENT).isIdenticalTo(value("0", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(false, VALUE_ENVIRONMENT).isIdenticalTo(value("-1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(false, VALUE_ENVIRONMENT).isIdenticalTo(value(null, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(false, VALUE_ENVIRONMENT).isIdenticalTo(value("lala", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(false, VALUE_ENVIRONMENT).isIdenticalTo(value("", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(false, VALUE_ENVIRONMENT).isIdenticalTo(value(1.2, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(1, VALUE_ENVIRONMENT).isIdenticalTo(value(true, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(1, VALUE_ENVIRONMENT).isIdenticalTo(value(false, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(1, VALUE_ENVIRONMENT).isIdenticalTo(value(1, VALUE_ENVIRONMENT)), is(true));
        assertThat(value(1, VALUE_ENVIRONMENT).isIdenticalTo(value(0, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(1, VALUE_ENVIRONMENT).isIdenticalTo(value(-1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(1, VALUE_ENVIRONMENT).isIdenticalTo(value("1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(1, VALUE_ENVIRONMENT).isIdenticalTo(value("0", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(1, VALUE_ENVIRONMENT).isIdenticalTo(value("-1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(1, VALUE_ENVIRONMENT).isIdenticalTo(value(null, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(1, VALUE_ENVIRONMENT).isIdenticalTo(value("lala", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(1, VALUE_ENVIRONMENT).isIdenticalTo(value("", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(1, VALUE_ENVIRONMENT).isIdenticalTo(value(1.2, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(0, VALUE_ENVIRONMENT).isIdenticalTo(value(true, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(0, VALUE_ENVIRONMENT).isIdenticalTo(value(false, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(0, VALUE_ENVIRONMENT).isIdenticalTo(value(1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(0, VALUE_ENVIRONMENT).isIdenticalTo(value(0, VALUE_ENVIRONMENT)), is(true));
        assertThat(value(0, VALUE_ENVIRONMENT).isIdenticalTo(value(-1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(0, VALUE_ENVIRONMENT).isIdenticalTo(value("1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(0, VALUE_ENVIRONMENT).isIdenticalTo(value("0", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(0, VALUE_ENVIRONMENT).isIdenticalTo(value("-1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(0, VALUE_ENVIRONMENT).isIdenticalTo(value(null, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(0, VALUE_ENVIRONMENT).isIdenticalTo(value("lala", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(0, VALUE_ENVIRONMENT).isIdenticalTo(value("", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(0, VALUE_ENVIRONMENT).isIdenticalTo(value(1.2, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(-1, VALUE_ENVIRONMENT).isIdenticalTo(value(true, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(-1, VALUE_ENVIRONMENT).isIdenticalTo(value(false, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(-1, VALUE_ENVIRONMENT).isIdenticalTo(value(1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(-1, VALUE_ENVIRONMENT).isIdenticalTo(value(0, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(-1, VALUE_ENVIRONMENT).isIdenticalTo(value(-1, VALUE_ENVIRONMENT)), is(true));
        assertThat(value(-1, VALUE_ENVIRONMENT).isIdenticalTo(value("1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(-1, VALUE_ENVIRONMENT).isIdenticalTo(value("0", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(-1, VALUE_ENVIRONMENT).isIdenticalTo(value("-1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(-1, VALUE_ENVIRONMENT).isIdenticalTo(value(null, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(-1, VALUE_ENVIRONMENT).isIdenticalTo(value("lala", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(-1, VALUE_ENVIRONMENT).isIdenticalTo(value("", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(-1, VALUE_ENVIRONMENT).isIdenticalTo(value(1.2, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("1", VALUE_ENVIRONMENT).isIdenticalTo(value(true, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("1", VALUE_ENVIRONMENT).isIdenticalTo(value(false, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("1", VALUE_ENVIRONMENT).isIdenticalTo(value(1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("1", VALUE_ENVIRONMENT).isIdenticalTo(value(0, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("1", VALUE_ENVIRONMENT).isIdenticalTo(value(-1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("1", VALUE_ENVIRONMENT).isIdenticalTo(value("1", VALUE_ENVIRONMENT)), is(true));
        assertThat(value("1", VALUE_ENVIRONMENT).isIdenticalTo(value("0", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("1", VALUE_ENVIRONMENT).isIdenticalTo(value("-1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("1", VALUE_ENVIRONMENT).isIdenticalTo(value(null, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("1", VALUE_ENVIRONMENT).isIdenticalTo(value("lala", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("1", VALUE_ENVIRONMENT).isIdenticalTo(value("", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("1", VALUE_ENVIRONMENT).isIdenticalTo(value(1.2, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("0", VALUE_ENVIRONMENT).isIdenticalTo(value(true, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("0", VALUE_ENVIRONMENT).isIdenticalTo(value(false, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("0", VALUE_ENVIRONMENT).isIdenticalTo(value(1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("0", VALUE_ENVIRONMENT).isIdenticalTo(value(0, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("0", VALUE_ENVIRONMENT).isIdenticalTo(value(-1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("0", VALUE_ENVIRONMENT).isIdenticalTo(value("1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("0", VALUE_ENVIRONMENT).isIdenticalTo(value("0", VALUE_ENVIRONMENT)), is(true));
        assertThat(value("0", VALUE_ENVIRONMENT).isIdenticalTo(value("-1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("0", VALUE_ENVIRONMENT).isIdenticalTo(value(null, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("0", VALUE_ENVIRONMENT).isIdenticalTo(value("lala", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("0", VALUE_ENVIRONMENT).isIdenticalTo(value("", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("0", VALUE_ENVIRONMENT).isIdenticalTo(value(1.2, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("-1", VALUE_ENVIRONMENT).isIdenticalTo(value(true, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("-1", VALUE_ENVIRONMENT).isIdenticalTo(value(false, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("-1", VALUE_ENVIRONMENT).isIdenticalTo(value(1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("-1", VALUE_ENVIRONMENT).isIdenticalTo(value(0, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("-1", VALUE_ENVIRONMENT).isIdenticalTo(value(-1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("-1", VALUE_ENVIRONMENT).isIdenticalTo(value("1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("-1", VALUE_ENVIRONMENT).isIdenticalTo(value("0", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("-1", VALUE_ENVIRONMENT).isIdenticalTo(value("-1", VALUE_ENVIRONMENT)), is(true));
        assertThat(value("-1", VALUE_ENVIRONMENT).isIdenticalTo(value(null, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("-1", VALUE_ENVIRONMENT).isIdenticalTo(value("lala", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("-1", VALUE_ENVIRONMENT).isIdenticalTo(value("", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("-1", VALUE_ENVIRONMENT).isIdenticalTo(value(1.2, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(null, VALUE_ENVIRONMENT).isIdenticalTo(value(true, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(null, VALUE_ENVIRONMENT).isIdenticalTo(value(false, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(null, VALUE_ENVIRONMENT).isIdenticalTo(value(1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(null, VALUE_ENVIRONMENT).isIdenticalTo(value(0, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(null, VALUE_ENVIRONMENT).isIdenticalTo(value(-1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(null, VALUE_ENVIRONMENT).isIdenticalTo(value("1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(null, VALUE_ENVIRONMENT).isIdenticalTo(value("0", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(null, VALUE_ENVIRONMENT).isIdenticalTo(value("-1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(null, VALUE_ENVIRONMENT).isIdenticalTo(value(null, VALUE_ENVIRONMENT)), is(true));
        assertThat(value(null, VALUE_ENVIRONMENT).isIdenticalTo(value("lala", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(null, VALUE_ENVIRONMENT).isIdenticalTo(value("", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(null, VALUE_ENVIRONMENT).isIdenticalTo(value(1.2, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("lala", VALUE_ENVIRONMENT).isIdenticalTo(value(true, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("lala", VALUE_ENVIRONMENT).isIdenticalTo(value(false, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("lala", VALUE_ENVIRONMENT).isIdenticalTo(value(1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("lala", VALUE_ENVIRONMENT).isIdenticalTo(value(0, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("lala", VALUE_ENVIRONMENT).isIdenticalTo(value(-1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("lala", VALUE_ENVIRONMENT).isIdenticalTo(value("1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("lala", VALUE_ENVIRONMENT).isIdenticalTo(value("0", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("lala", VALUE_ENVIRONMENT).isIdenticalTo(value("-1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("lala", VALUE_ENVIRONMENT).isIdenticalTo(value(null, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("lala", VALUE_ENVIRONMENT).isIdenticalTo(value("lala", VALUE_ENVIRONMENT)), is(true));
        assertThat(value("lala", VALUE_ENVIRONMENT).isIdenticalTo(value("lalaa", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("lala", VALUE_ENVIRONMENT).isIdenticalTo(value("", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("lala", VALUE_ENVIRONMENT).isIdenticalTo(value(1.2, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("", VALUE_ENVIRONMENT).isIdenticalTo(value(true, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("", VALUE_ENVIRONMENT).isIdenticalTo(value(false, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("", VALUE_ENVIRONMENT).isIdenticalTo(value(1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("", VALUE_ENVIRONMENT).isIdenticalTo(value(0, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("", VALUE_ENVIRONMENT).isIdenticalTo(value(-1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("", VALUE_ENVIRONMENT).isIdenticalTo(value("1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("", VALUE_ENVIRONMENT).isIdenticalTo(value("0", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("", VALUE_ENVIRONMENT).isIdenticalTo(value("-1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("", VALUE_ENVIRONMENT).isIdenticalTo(value(null, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("", VALUE_ENVIRONMENT).isIdenticalTo(value("lala", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("", VALUE_ENVIRONMENT).isIdenticalTo(value("", VALUE_ENVIRONMENT)), is(true));
        assertThat(value("", VALUE_ENVIRONMENT).isIdenticalTo(value(1.2, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(1.2, VALUE_ENVIRONMENT).isIdenticalTo(value(true, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(1.2, VALUE_ENVIRONMENT).isIdenticalTo(value(false, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(1.2, VALUE_ENVIRONMENT).isIdenticalTo(value(1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(1.2, VALUE_ENVIRONMENT).isIdenticalTo(value(0, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(1.2, VALUE_ENVIRONMENT).isIdenticalTo(value(-1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(1.2, VALUE_ENVIRONMENT).isIdenticalTo(value("1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(1.2, VALUE_ENVIRONMENT).isIdenticalTo(value("0", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(1.2, VALUE_ENVIRONMENT).isIdenticalTo(value("-1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(1.2, VALUE_ENVIRONMENT).isIdenticalTo(value(null, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(1.2, VALUE_ENVIRONMENT).isIdenticalTo(value("lala", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(1.2, VALUE_ENVIRONMENT).isIdenticalTo(value("", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(1.2, VALUE_ENVIRONMENT).isIdenticalTo(value(1.2, VALUE_ENVIRONMENT)), is(true));
    }

    @Test
    public void lower() throws Exception {
        assertThat(value(true, VALUE_ENVIRONMENT).isLowerThan(value(true, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(true, VALUE_ENVIRONMENT).isLowerThan(value(false, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(true, VALUE_ENVIRONMENT).isLowerThan(value(1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(true, VALUE_ENVIRONMENT).isLowerThan(value(0, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(true, VALUE_ENVIRONMENT).isLowerThan(value(-1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(true, VALUE_ENVIRONMENT).isLowerThan(value("1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(true, VALUE_ENVIRONMENT).isLowerThan(value("0", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(true, VALUE_ENVIRONMENT).isLowerThan(value("-1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(true, VALUE_ENVIRONMENT).isLowerThan(value(null, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(true, VALUE_ENVIRONMENT).isLowerThan(value("lala", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(true, VALUE_ENVIRONMENT).isLowerThan(value("", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(true, VALUE_ENVIRONMENT).isLowerThan(value(1.2, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(false, VALUE_ENVIRONMENT).isLowerThan(value(true, VALUE_ENVIRONMENT)), is(true));
        assertThat(value(false, VALUE_ENVIRONMENT).isLowerThan(value(false, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(false, VALUE_ENVIRONMENT).isLowerThan(value(1, VALUE_ENVIRONMENT)), is(true));
        assertThat(value(false, VALUE_ENVIRONMENT).isLowerThan(value(0, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(false, VALUE_ENVIRONMENT).isLowerThan(value(-1, VALUE_ENVIRONMENT)), is(true));
        assertThat(value(false, VALUE_ENVIRONMENT).isLowerThan(value("1", VALUE_ENVIRONMENT)), is(true));
        assertThat(value(false, VALUE_ENVIRONMENT).isLowerThan(value("0", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(false, VALUE_ENVIRONMENT).isLowerThan(value("-1", VALUE_ENVIRONMENT)), is(true));
        assertThat(value(false, VALUE_ENVIRONMENT).isLowerThan(value(null, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(false, VALUE_ENVIRONMENT).isLowerThan(value("lala", VALUE_ENVIRONMENT)), is(true));
        assertThat(value(false, VALUE_ENVIRONMENT).isLowerThan(value("", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(false, VALUE_ENVIRONMENT).isLowerThan(value(1.2, VALUE_ENVIRONMENT)), is(true));
        assertThat(value(1, VALUE_ENVIRONMENT).isLowerThan(value(true, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(1, VALUE_ENVIRONMENT).isLowerThan(value(false, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(1, VALUE_ENVIRONMENT).isLowerThan(value(1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(1, VALUE_ENVIRONMENT).isLowerThan(value(0, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(1, VALUE_ENVIRONMENT).isLowerThan(value(-1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(1, VALUE_ENVIRONMENT).isLowerThan(value("1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(1, VALUE_ENVIRONMENT).isLowerThan(value("0", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(1, VALUE_ENVIRONMENT).isLowerThan(value("-1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(1, VALUE_ENVIRONMENT).isLowerThan(value(null, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(1, VALUE_ENVIRONMENT).isLowerThan(value("lala", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(1, VALUE_ENVIRONMENT).isLowerThan(value("", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(1, VALUE_ENVIRONMENT).isLowerThan(value(1.2, VALUE_ENVIRONMENT)), is(true));
        assertThat(value(0, VALUE_ENVIRONMENT).isLowerThan(value(true, VALUE_ENVIRONMENT)), is(true));
        assertThat(value(0, VALUE_ENVIRONMENT).isLowerThan(value(false, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(0, VALUE_ENVIRONMENT).isLowerThan(value(1, VALUE_ENVIRONMENT)), is(true));
        assertThat(value(0, VALUE_ENVIRONMENT).isLowerThan(value(0, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(0, VALUE_ENVIRONMENT).isLowerThan(value(-1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(0, VALUE_ENVIRONMENT).isLowerThan(value("1", VALUE_ENVIRONMENT)), is(true));
        assertThat(value(0, VALUE_ENVIRONMENT).isLowerThan(value("0", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(0, VALUE_ENVIRONMENT).isLowerThan(value("-1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(0, VALUE_ENVIRONMENT).isLowerThan(value(null, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(0, VALUE_ENVIRONMENT).isLowerThan(value("lala", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(0, VALUE_ENVIRONMENT).isLowerThan(value("", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(0, VALUE_ENVIRONMENT).isLowerThan(value(1.2, VALUE_ENVIRONMENT)), is(true));
        assertThat(value(-1, VALUE_ENVIRONMENT).isLowerThan(value(true, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(-1, VALUE_ENVIRONMENT).isLowerThan(value(false, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(-1, VALUE_ENVIRONMENT).isLowerThan(value(1, VALUE_ENVIRONMENT)), is(true));
        assertThat(value(-1, VALUE_ENVIRONMENT).isLowerThan(value(0, VALUE_ENVIRONMENT)), is(true));
        assertThat(value(-1, VALUE_ENVIRONMENT).isLowerThan(value(-1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(-1, VALUE_ENVIRONMENT).isLowerThan(value("1", VALUE_ENVIRONMENT)), is(true));
        assertThat(value(-1, VALUE_ENVIRONMENT).isLowerThan(value("0", VALUE_ENVIRONMENT)), is(true));
        assertThat(value(-1, VALUE_ENVIRONMENT).isLowerThan(value("-1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(-1, VALUE_ENVIRONMENT).isLowerThan(value(null, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(-1, VALUE_ENVIRONMENT).isLowerThan(value("lala", VALUE_ENVIRONMENT)), is(true));
        assertThat(value(-1, VALUE_ENVIRONMENT).isLowerThan(value("", VALUE_ENVIRONMENT)), is(true));
        assertThat(value(-1, VALUE_ENVIRONMENT).isLowerThan(value(1.2, VALUE_ENVIRONMENT)), is(true));
        assertThat(value("1", VALUE_ENVIRONMENT).isLowerThan(value(true, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("1", VALUE_ENVIRONMENT).isLowerThan(value(false, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("1", VALUE_ENVIRONMENT).isLowerThan(value(1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("1", VALUE_ENVIRONMENT).isLowerThan(value(0, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("1", VALUE_ENVIRONMENT).isLowerThan(value(-1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("1", VALUE_ENVIRONMENT).isLowerThan(value("1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("1", VALUE_ENVIRONMENT).isLowerThan(value("0", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("1", VALUE_ENVIRONMENT).isLowerThan(value("-1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("1", VALUE_ENVIRONMENT).isLowerThan(value(null, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("1", VALUE_ENVIRONMENT).isLowerThan(value("lala", VALUE_ENVIRONMENT)), is(true));
        assertThat(value("1", VALUE_ENVIRONMENT).isLowerThan(value("", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("1", VALUE_ENVIRONMENT).isLowerThan(value(1.2, VALUE_ENVIRONMENT)), is(true));
        assertThat(value("0", VALUE_ENVIRONMENT).isLowerThan(value(true, VALUE_ENVIRONMENT)), is(true));
        assertThat(value("0", VALUE_ENVIRONMENT).isLowerThan(value(false, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("0", VALUE_ENVIRONMENT).isLowerThan(value(1, VALUE_ENVIRONMENT)), is(true));
        assertThat(value("0", VALUE_ENVIRONMENT).isLowerThan(value(0, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("0", VALUE_ENVIRONMENT).isLowerThan(value(-1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("0", VALUE_ENVIRONMENT).isLowerThan(value("1", VALUE_ENVIRONMENT)), is(true));
        assertThat(value("0", VALUE_ENVIRONMENT).isLowerThan(value("0", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("0", VALUE_ENVIRONMENT).isLowerThan(value("-1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("0", VALUE_ENVIRONMENT).isLowerThan(value(null, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("0", VALUE_ENVIRONMENT).isLowerThan(value("lala", VALUE_ENVIRONMENT)), is(true));
        assertThat(value("0", VALUE_ENVIRONMENT).isLowerThan(value("", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("0", VALUE_ENVIRONMENT).isLowerThan(value(1.2, VALUE_ENVIRONMENT)), is(true));
        assertThat(value("-1", VALUE_ENVIRONMENT).isLowerThan(value(true, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("-1", VALUE_ENVIRONMENT).isLowerThan(value(false, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("-1", VALUE_ENVIRONMENT).isLowerThan(value(1, VALUE_ENVIRONMENT)), is(true));
        assertThat(value("-1", VALUE_ENVIRONMENT).isLowerThan(value(0, VALUE_ENVIRONMENT)), is(true));
        assertThat(value("-1", VALUE_ENVIRONMENT).isLowerThan(value(-1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("-1", VALUE_ENVIRONMENT).isLowerThan(value("1", VALUE_ENVIRONMENT)), is(true));
        assertThat(value("-1", VALUE_ENVIRONMENT).isLowerThan(value("0", VALUE_ENVIRONMENT)), is(true));
        assertThat(value("-1", VALUE_ENVIRONMENT).isLowerThan(value("-1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("-1", VALUE_ENVIRONMENT).isLowerThan(value(null, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("-1", VALUE_ENVIRONMENT).isLowerThan(value("lala", VALUE_ENVIRONMENT)), is(true));
        assertThat(value("-1", VALUE_ENVIRONMENT).isLowerThan(value("", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("-1", VALUE_ENVIRONMENT).isLowerThan(value(1.2, VALUE_ENVIRONMENT)), is(true));
        assertThat(value(null, VALUE_ENVIRONMENT).isLowerThan(value(true, VALUE_ENVIRONMENT)), is(true));
        assertThat(value(null, VALUE_ENVIRONMENT).isLowerThan(value(false, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(null, VALUE_ENVIRONMENT).isLowerThan(value(1, VALUE_ENVIRONMENT)), is(true));
        assertThat(value(null, VALUE_ENVIRONMENT).isLowerThan(value(0, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(null, VALUE_ENVIRONMENT).isLowerThan(value(-1, VALUE_ENVIRONMENT)), is(true));
        assertThat(value(null, VALUE_ENVIRONMENT).isLowerThan(value("1", VALUE_ENVIRONMENT)), is(true));
        assertThat(value(null, VALUE_ENVIRONMENT).isLowerThan(value("0", VALUE_ENVIRONMENT)), is(true));
        assertThat(value(null, VALUE_ENVIRONMENT).isLowerThan(value("-1", VALUE_ENVIRONMENT)), is(true));
        assertThat(value(null, VALUE_ENVIRONMENT).isLowerThan(value(null, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(null, VALUE_ENVIRONMENT).isLowerThan(value("lala", VALUE_ENVIRONMENT)), is(true));
        assertThat(value(null, VALUE_ENVIRONMENT).isLowerThan(value("", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(null, VALUE_ENVIRONMENT).isLowerThan(value(1.2, VALUE_ENVIRONMENT)), is(true));
        assertThat(value("lala", VALUE_ENVIRONMENT).isLowerThan(value(true, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("lala", VALUE_ENVIRONMENT).isLowerThan(value(false, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("lala", VALUE_ENVIRONMENT).isLowerThan(value(1, VALUE_ENVIRONMENT)), is(true));
        assertThat(value("lala", VALUE_ENVIRONMENT).isLowerThan(value(0, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("lala", VALUE_ENVIRONMENT).isLowerThan(value(-1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("lala", VALUE_ENVIRONMENT).isLowerThan(value("1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("lala", VALUE_ENVIRONMENT).isLowerThan(value("0", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("lala", VALUE_ENVIRONMENT).isLowerThan(value("-1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("lala", VALUE_ENVIRONMENT).isLowerThan(value(null, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("lala", VALUE_ENVIRONMENT).isLowerThan(value("lala", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("lala", VALUE_ENVIRONMENT).isLowerThan(value("", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("lala", VALUE_ENVIRONMENT).isLowerThan(value(1.2, VALUE_ENVIRONMENT)), is(true));
        assertThat(value("", VALUE_ENVIRONMENT).isLowerThan(value(true, VALUE_ENVIRONMENT)), is(true));
        assertThat(value("", VALUE_ENVIRONMENT).isLowerThan(value(false, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("", VALUE_ENVIRONMENT).isLowerThan(value(1, VALUE_ENVIRONMENT)), is(true));
        assertThat(value("", VALUE_ENVIRONMENT).isLowerThan(value(0, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("", VALUE_ENVIRONMENT).isLowerThan(value(-1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("", VALUE_ENVIRONMENT).isLowerThan(value("1", VALUE_ENVIRONMENT)), is(true));
        assertThat(value("", VALUE_ENVIRONMENT).isLowerThan(value("0", VALUE_ENVIRONMENT)), is(true));
        assertThat(value("", VALUE_ENVIRONMENT).isLowerThan(value("-1", VALUE_ENVIRONMENT)), is(true));
        assertThat(value("", VALUE_ENVIRONMENT).isLowerThan(value(null, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("", VALUE_ENVIRONMENT).isLowerThan(value("lala", VALUE_ENVIRONMENT)), is(true));
        assertThat(value("", VALUE_ENVIRONMENT).isLowerThan(value("", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("", VALUE_ENVIRONMENT).isLowerThan(value(1.2, VALUE_ENVIRONMENT)), is(true));
        assertThat(value(1.2, VALUE_ENVIRONMENT).isLowerThan(value(true, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(1.2, VALUE_ENVIRONMENT).isLowerThan(value(false, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(1.2, VALUE_ENVIRONMENT).isLowerThan(value(1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(1.2, VALUE_ENVIRONMENT).isLowerThan(value(0, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(1.2, VALUE_ENVIRONMENT).isLowerThan(value(-1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(1.2, VALUE_ENVIRONMENT).isLowerThan(value("1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(1.2, VALUE_ENVIRONMENT).isLowerThan(value("0", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(1.2, VALUE_ENVIRONMENT).isLowerThan(value("-1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(1.2, VALUE_ENVIRONMENT).isLowerThan(value(null, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(1.2, VALUE_ENVIRONMENT).isLowerThan(value("lala", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(1.2, VALUE_ENVIRONMENT).isLowerThan(value("", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(1.2, VALUE_ENVIRONMENT).isLowerThan(value(1.2, VALUE_ENVIRONMENT)), is(false));
    }

    @Test
    public void greater() throws Exception {
        assertThat(value(true, VALUE_ENVIRONMENT).isGreaterThan(value(true, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(true, VALUE_ENVIRONMENT).isGreaterThan(value(false, VALUE_ENVIRONMENT)), is(true));
        assertThat(value(true, VALUE_ENVIRONMENT).isGreaterThan(value(1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(true, VALUE_ENVIRONMENT).isGreaterThan(value(0, VALUE_ENVIRONMENT)), is(true));
        assertThat(value(true, VALUE_ENVIRONMENT).isGreaterThan(value(-1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(true, VALUE_ENVIRONMENT).isGreaterThan(value("1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(true, VALUE_ENVIRONMENT).isGreaterThan(value("0", VALUE_ENVIRONMENT)), is(true));
        assertThat(value(true, VALUE_ENVIRONMENT).isGreaterThan(value("-1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(true, VALUE_ENVIRONMENT).isGreaterThan(value(null, VALUE_ENVIRONMENT)), is(true));
        assertThat(value(true, VALUE_ENVIRONMENT).isGreaterThan(value("lala", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(true, VALUE_ENVIRONMENT).isGreaterThan(value("", VALUE_ENVIRONMENT)), is(true));
        assertThat(value(true, VALUE_ENVIRONMENT).isGreaterThan(value(1.2, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(false, VALUE_ENVIRONMENT).isGreaterThan(value(true, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(false, VALUE_ENVIRONMENT).isGreaterThan(value(false, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(false, VALUE_ENVIRONMENT).isGreaterThan(value(1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(false, VALUE_ENVIRONMENT).isGreaterThan(value(0, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(false, VALUE_ENVIRONMENT).isGreaterThan(value(-1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(false, VALUE_ENVIRONMENT).isGreaterThan(value("1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(false, VALUE_ENVIRONMENT).isGreaterThan(value("0", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(false, VALUE_ENVIRONMENT).isGreaterThan(value("-1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(false, VALUE_ENVIRONMENT).isGreaterThan(value(null, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(false, VALUE_ENVIRONMENT).isGreaterThan(value("lala", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(false, VALUE_ENVIRONMENT).isGreaterThan(value("", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(false, VALUE_ENVIRONMENT).isGreaterThan(value(1.2, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(1, VALUE_ENVIRONMENT).isGreaterThan(value(true, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(1, VALUE_ENVIRONMENT).isGreaterThan(value(false, VALUE_ENVIRONMENT)), is(true));
        assertThat(value(1, VALUE_ENVIRONMENT).isGreaterThan(value(1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(1, VALUE_ENVIRONMENT).isGreaterThan(value(0, VALUE_ENVIRONMENT)), is(true));
        assertThat(value(1, VALUE_ENVIRONMENT).isGreaterThan(value(-1, VALUE_ENVIRONMENT)), is(true));
        assertThat(value(1, VALUE_ENVIRONMENT).isGreaterThan(value("1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(1, VALUE_ENVIRONMENT).isGreaterThan(value("0", VALUE_ENVIRONMENT)), is(true));
        assertThat(value(1, VALUE_ENVIRONMENT).isGreaterThan(value("-1", VALUE_ENVIRONMENT)), is(true));
        assertThat(value(1, VALUE_ENVIRONMENT).isGreaterThan(value(null, VALUE_ENVIRONMENT)), is(true));
        assertThat(value(1, VALUE_ENVIRONMENT).isGreaterThan(value("lala", VALUE_ENVIRONMENT)), is(true));
        assertThat(value(1, VALUE_ENVIRONMENT).isGreaterThan(value("", VALUE_ENVIRONMENT)), is(true));
        assertThat(value(1, VALUE_ENVIRONMENT).isGreaterThan(value(1.2, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(0, VALUE_ENVIRONMENT).isGreaterThan(value(true, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(0, VALUE_ENVIRONMENT).isGreaterThan(value(false, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(0, VALUE_ENVIRONMENT).isGreaterThan(value(1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(0, VALUE_ENVIRONMENT).isGreaterThan(value(0, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(0, VALUE_ENVIRONMENT).isGreaterThan(value(-1, VALUE_ENVIRONMENT)), is(true));
        assertThat(value(0, VALUE_ENVIRONMENT).isGreaterThan(value("1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(0, VALUE_ENVIRONMENT).isGreaterThan(value("0", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(0, VALUE_ENVIRONMENT).isGreaterThan(value("-1", VALUE_ENVIRONMENT)), is(true));
        assertThat(value(0, VALUE_ENVIRONMENT).isGreaterThan(value(null, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(0, VALUE_ENVIRONMENT).isGreaterThan(value("lala", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(0, VALUE_ENVIRONMENT).isGreaterThan(value("", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(0, VALUE_ENVIRONMENT).isGreaterThan(value(1.2, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(-1, VALUE_ENVIRONMENT).isGreaterThan(value(true, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(-1, VALUE_ENVIRONMENT).isGreaterThan(value(false, VALUE_ENVIRONMENT)), is(true));
        assertThat(value(-1, VALUE_ENVIRONMENT).isGreaterThan(value(1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(-1, VALUE_ENVIRONMENT).isGreaterThan(value(0, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(-1, VALUE_ENVIRONMENT).isGreaterThan(value(-1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(-1, VALUE_ENVIRONMENT).isGreaterThan(value("1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(-1, VALUE_ENVIRONMENT).isGreaterThan(value("0", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(-1, VALUE_ENVIRONMENT).isGreaterThan(value("-1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(-1, VALUE_ENVIRONMENT).isGreaterThan(value(null, VALUE_ENVIRONMENT)), is(true));
        assertThat(value(-1, VALUE_ENVIRONMENT).isGreaterThan(value("lala", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(-1, VALUE_ENVIRONMENT).isGreaterThan(value("", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(-1, VALUE_ENVIRONMENT).isGreaterThan(value(1.2, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("1", VALUE_ENVIRONMENT).isGreaterThan(value(true, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("1", VALUE_ENVIRONMENT).isGreaterThan(value(false, VALUE_ENVIRONMENT)), is(true));
        assertThat(value("1", VALUE_ENVIRONMENT).isGreaterThan(value(1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("1", VALUE_ENVIRONMENT).isGreaterThan(value(0, VALUE_ENVIRONMENT)), is(true));
        assertThat(value("1", VALUE_ENVIRONMENT).isGreaterThan(value(-1, VALUE_ENVIRONMENT)), is(true));
        assertThat(value("1", VALUE_ENVIRONMENT).isGreaterThan(value("1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("1", VALUE_ENVIRONMENT).isGreaterThan(value("0", VALUE_ENVIRONMENT)), is(true));
        assertThat(value("1", VALUE_ENVIRONMENT).isGreaterThan(value("-1", VALUE_ENVIRONMENT)), is(true));
        assertThat(value("1", VALUE_ENVIRONMENT).isGreaterThan(value(null, VALUE_ENVIRONMENT)), is(true));
        assertThat(value("1", VALUE_ENVIRONMENT).isGreaterThan(value("lala", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("1", VALUE_ENVIRONMENT).isGreaterThan(value("", VALUE_ENVIRONMENT)), is(true));
        assertThat(value("1", VALUE_ENVIRONMENT).isGreaterThan(value(1.2, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("0", VALUE_ENVIRONMENT).isGreaterThan(value(true, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("0", VALUE_ENVIRONMENT).isGreaterThan(value(false, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("0", VALUE_ENVIRONMENT).isGreaterThan(value(1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("0", VALUE_ENVIRONMENT).isGreaterThan(value(0, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("0", VALUE_ENVIRONMENT).isGreaterThan(value(-1, VALUE_ENVIRONMENT)), is(true));
        assertThat(value("0", VALUE_ENVIRONMENT).isGreaterThan(value("1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("0", VALUE_ENVIRONMENT).isGreaterThan(value("0", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("0", VALUE_ENVIRONMENT).isGreaterThan(value("-1", VALUE_ENVIRONMENT)), is(true));
        assertThat(value("0", VALUE_ENVIRONMENT).isGreaterThan(value(null, VALUE_ENVIRONMENT)), is(true));
        assertThat(value("0", VALUE_ENVIRONMENT).isGreaterThan(value("lala", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("0", VALUE_ENVIRONMENT).isGreaterThan(value("", VALUE_ENVIRONMENT)), is(true));
        assertThat(value("0", VALUE_ENVIRONMENT).isGreaterThan(value(1.2, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("-1", VALUE_ENVIRONMENT).isGreaterThan(value(true, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("-1", VALUE_ENVIRONMENT).isGreaterThan(value(false, VALUE_ENVIRONMENT)), is(true));
        assertThat(value("-1", VALUE_ENVIRONMENT).isGreaterThan(value(1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("-1", VALUE_ENVIRONMENT).isGreaterThan(value(0, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("-1", VALUE_ENVIRONMENT).isGreaterThan(value(-1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("-1", VALUE_ENVIRONMENT).isGreaterThan(value("1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("-1", VALUE_ENVIRONMENT).isGreaterThan(value("0", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("-1", VALUE_ENVIRONMENT).isGreaterThan(value("-1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("-1", VALUE_ENVIRONMENT).isGreaterThan(value(null, VALUE_ENVIRONMENT)), is(true));
        assertThat(value("-1", VALUE_ENVIRONMENT).isGreaterThan(value("lala", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("-1", VALUE_ENVIRONMENT).isGreaterThan(value("", VALUE_ENVIRONMENT)), is(true));
        assertThat(value("-1", VALUE_ENVIRONMENT).isGreaterThan(value(1.2, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(null, VALUE_ENVIRONMENT).isGreaterThan(value(true, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(null, VALUE_ENVIRONMENT).isGreaterThan(value(false, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(null, VALUE_ENVIRONMENT).isGreaterThan(value(1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(null, VALUE_ENVIRONMENT).isGreaterThan(value(0, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(null, VALUE_ENVIRONMENT).isGreaterThan(value(-1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(null, VALUE_ENVIRONMENT).isGreaterThan(value("1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(null, VALUE_ENVIRONMENT).isGreaterThan(value("0", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(null, VALUE_ENVIRONMENT).isGreaterThan(value("-1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(null, VALUE_ENVIRONMENT).isGreaterThan(value(null, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(null, VALUE_ENVIRONMENT).isGreaterThan(value("lala", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(null, VALUE_ENVIRONMENT).isGreaterThan(value("", VALUE_ENVIRONMENT)), is(false));
        assertThat(value(null, VALUE_ENVIRONMENT).isGreaterThan(value(1.2, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("lala", VALUE_ENVIRONMENT).isGreaterThan(value(true, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("lala", VALUE_ENVIRONMENT).isGreaterThan(value(false, VALUE_ENVIRONMENT)), is(true));
        assertThat(value("lala", VALUE_ENVIRONMENT).isGreaterThan(value(1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("lala", VALUE_ENVIRONMENT).isGreaterThan(value(0, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("lala", VALUE_ENVIRONMENT).isGreaterThan(value(-1, VALUE_ENVIRONMENT)), is(true));
        assertThat(value("lala", VALUE_ENVIRONMENT).isGreaterThan(value("1", VALUE_ENVIRONMENT)), is(true));
        assertThat(value("lala", VALUE_ENVIRONMENT).isGreaterThan(value("0", VALUE_ENVIRONMENT)), is(true));
        assertThat(value("lala", VALUE_ENVIRONMENT).isGreaterThan(value("-1", VALUE_ENVIRONMENT)), is(true));
        assertThat(value("lala", VALUE_ENVIRONMENT).isGreaterThan(value(null, VALUE_ENVIRONMENT)), is(true));
        assertThat(value("lala", VALUE_ENVIRONMENT).isGreaterThan(value("lala", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("lala", VALUE_ENVIRONMENT).isGreaterThan(value("", VALUE_ENVIRONMENT)), is(true));
        assertThat(value("lala", VALUE_ENVIRONMENT).isGreaterThan(value(1.2, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("", VALUE_ENVIRONMENT).isGreaterThan(value(true, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("", VALUE_ENVIRONMENT).isGreaterThan(value(false, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("", VALUE_ENVIRONMENT).isGreaterThan(value(1, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("", VALUE_ENVIRONMENT).isGreaterThan(value(0, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("", VALUE_ENVIRONMENT).isGreaterThan(value(-1, VALUE_ENVIRONMENT)), is(true));
        assertThat(value("", VALUE_ENVIRONMENT).isGreaterThan(value("1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("", VALUE_ENVIRONMENT).isGreaterThan(value("0", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("", VALUE_ENVIRONMENT).isGreaterThan(value("-1", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("", VALUE_ENVIRONMENT).isGreaterThan(value(null, VALUE_ENVIRONMENT)), is(false));
        assertThat(value("", VALUE_ENVIRONMENT).isGreaterThan(value("lala", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("", VALUE_ENVIRONMENT).isGreaterThan(value("", VALUE_ENVIRONMENT)), is(false));
        assertThat(value("", VALUE_ENVIRONMENT).isGreaterThan(value(1.2, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(1.2, VALUE_ENVIRONMENT).isGreaterThan(value(true, VALUE_ENVIRONMENT)), is(false));
        assertThat(value(1.2, VALUE_ENVIRONMENT).isGreaterThan(value(false, VALUE_ENVIRONMENT)), is(true));
        assertThat(value(1.2, VALUE_ENVIRONMENT).isGreaterThan(value(1, VALUE_ENVIRONMENT)), is(true));
        assertThat(value(1.2, VALUE_ENVIRONMENT).isGreaterThan(value(0, VALUE_ENVIRONMENT)), is(true));
        assertThat(value(1.2, VALUE_ENVIRONMENT).isGreaterThan(value(-1, VALUE_ENVIRONMENT)), is(true));
        assertThat(value(1.2, VALUE_ENVIRONMENT).isGreaterThan(value("1", VALUE_ENVIRONMENT)), is(true));
        assertThat(value(1.2, VALUE_ENVIRONMENT).isGreaterThan(value("0", VALUE_ENVIRONMENT)), is(true));
        assertThat(value(1.2, VALUE_ENVIRONMENT).isGreaterThan(value("-1", VALUE_ENVIRONMENT)), is(true));
        assertThat(value(1.2, VALUE_ENVIRONMENT).isGreaterThan(value(null, VALUE_ENVIRONMENT)), is(true));
        assertThat(value(1.2, VALUE_ENVIRONMENT).isGreaterThan(value("lala", VALUE_ENVIRONMENT)), is(true));
        assertThat(value(1.2, VALUE_ENVIRONMENT).isGreaterThan(value("", VALUE_ENVIRONMENT)), is(true));
        assertThat(value(1.2, VALUE_ENVIRONMENT).isGreaterThan(value(1.2, VALUE_ENVIRONMENT)), is(false));
    }
}