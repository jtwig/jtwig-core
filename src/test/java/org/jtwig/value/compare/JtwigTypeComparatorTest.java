package org.jtwig.value.compare;

import org.jtwig.value.JtwigType;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class JtwigTypeComparatorTest {
    private JtwigTypeComparator underTest = JtwigTypeComparator.instance();

    @Test
    public void compareWhenLeftIsAny() throws Exception {
        assertThat(underTest.compare(JtwigType.ANY, JtwigType.BOOLEAN), is(1));
        assertThat(underTest.compare(JtwigType.ANY, JtwigType.STRING), is(1));
        assertThat(underTest.compare(JtwigType.ANY, JtwigType.NUMBER), is(1));
        assertThat(underTest.compare(JtwigType.ANY, JtwigType.OBJECT), is(1));
    }

    @Test
    public void compareWhenRightIsAny() throws Exception {
        assertThat(underTest.compare(JtwigType.BOOLEAN, JtwigType.ANY), is(-1));
        assertThat(underTest.compare(JtwigType.STRING, JtwigType.ANY), is(-1));
        assertThat(underTest.compare(JtwigType.NUMBER, JtwigType.ANY), is(-1));
        assertThat(underTest.compare(JtwigType.OBJECT, JtwigType.ANY), is(-1));
    }

    @Test
    public void compareWhenBothSame() throws Exception {
        assertThat(underTest.compare(JtwigType.ANY, JtwigType.ANY), is(0));
        assertThat(underTest.compare(JtwigType.NUMBER, JtwigType.NUMBER), is(0));
        assertThat(underTest.compare(JtwigType.STRING, JtwigType.STRING), is(0));
        assertThat(underTest.compare(JtwigType.OBJECT, JtwigType.OBJECT), is(0));
    }

    @Test
    public void compareStringAndNumber() throws Exception {
        assertThat(underTest.compare(JtwigType.STRING, JtwigType.NUMBER), is(1));
        assertThat(underTest.compare(JtwigType.NUMBER, JtwigType.STRING), is(-1));
    }

    @Test
    public void compareStringAndBoolean() throws Exception {
        assertThat(underTest.compare(JtwigType.STRING, JtwigType.BOOLEAN), is(1));
        assertThat(underTest.compare(JtwigType.BOOLEAN, JtwigType.STRING), is(-1));
    }

    @Test
    public void compareNumberAndBoolean() throws Exception {
        assertThat(underTest.compare(JtwigType.NUMBER, JtwigType.BOOLEAN), is(1));
        assertThat(underTest.compare(JtwigType.BOOLEAN, JtwigType.NUMBER), is(-1));
    }

    @Test
    public void compareNumberAndObject() throws Exception {
        assertThat(underTest.compare(JtwigType.NUMBER, JtwigType.OBJECT), is(-1));
        assertThat(underTest.compare(JtwigType.OBJECT, JtwigType.NUMBER), is(1));
    }

    @Test
    public void compareBooleanAndObject() throws Exception {
        assertThat(underTest.compare(JtwigType.BOOLEAN, JtwigType.OBJECT), is(-1));
        assertThat(underTest.compare(JtwigType.OBJECT, JtwigType.BOOLEAN), is(1));
    }

    @Test
    public void compareStringAndObject() throws Exception {
        assertThat(underTest.compare(JtwigType.STRING, JtwigType.OBJECT), is(1));
        assertThat(underTest.compare(JtwigType.OBJECT, JtwigType.STRING), is(-1));
    }
}