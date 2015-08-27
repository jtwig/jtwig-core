package org.jtwig.functions.impl;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

public class StringFunctionsTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    StringFunctions underTest = new StringFunctions();


    @Test
    public void first() throws Exception {
        assertEquals(underTest.first("test"), (Character) 't');
    }
    @Test
    public void firstWithEmptyString() throws Exception {
        assertNull(underTest.first(""));
    }

    @Test
    public void last() throws Exception {
        assertEquals(underTest.last("test"), (Character) 't');
    }

    @Test
    public void lastWithEmptyString() throws Exception {
        assertNull(underTest.last(""));
    }


    @Test
    public void capitalize() throws Exception {
        assertEquals(underTest.capitalize("my first car"), "My first car");
    }

    @Test
    public void capitalizeWithEmptyString() throws Exception {
        assertEquals("", underTest.capitalize(""));
    }


    @Test
    public void formatExecute() throws Exception {
        assertEquals("I like foo and bar.", underTest.format("I like %s and %s.", "foo", "bar"));
        assertEquals("I like it.", underTest.format("I like it."));
    }


    @Test
    public void shouldGiveStringInLowercase() throws Exception {
        assertEquals(underTest.lower("abc"), "abc");
    }

    @Test
    public void shouldGiveStringInLowercase2() throws Exception {
        assertEquals(underTest.lower("ABC"), "abc");
    }

    @Test
    public void nl2brExecute() throws Exception {
        assertEquals("a<br />b", underTest.nl2br("a\nb"));
    }


    @Test
    public void replaceExecute() throws Exception {
        Object result = underTest.replace("I like %this% and %that%.", (Map) new HashMap<String, String>() {{
            put("%this%", "foo");
            put("%that%", "bar");
        }});

        assertEquals("I like foo and bar.", result);
    }
    @Test
    public void replaceReplace() throws Exception {
        Object result = underTest.replace("I like %this% and %that%.", (Map) new HashMap<String, String>() {{
            put("%this%", "foo");
        }});

        assertEquals("I like foo and %that%.", result);
    }

    @Test
    public void replaceWithoutReplacementExecute() throws Exception {
        Object result = underTest.replace("I like %this%.", (Map) new HashMap<String, String>() {});

        assertEquals("I like %this%.", result);
    }

    @Test
    public void replaceWhenNotFoundReplacement() throws Exception {
        Object result = underTest.replace("I like %this% and %hello%.", (Map) new HashMap<String, String>() {{
            put("%hello%", "two");
        }});

        assertEquals("I like %this% and two.", result);
    }

    @Test
    public void splitExecute() throws Exception {
        assertThat(underTest.split("a,b,c", ","), allOf(
                hasItem("a"),
                hasItem("b"),
                hasItem("c"),
                not(hasItem("d"))
        ));
    }

    @Test
    public void stripExecute() throws Exception {
        assertEquals("ab", underTest.stripTags("a<br />b"));
    }

    @Test
    public void titleExecute() throws Exception {
        assertEquals(underTest.title("my first car"), "My First Car");
    }


    @Test
    public void trimShouldGiveTrimmedString() throws Exception {
        assertEquals(underTest.trim(" abc "), "abc");
    }

    @Test
    public void trimShouldGiveStringInUppercase2() throws Exception {
        assertEquals(underTest.trim("abc"), "abc");
    }

    @Test
    public void nullPointer() throws Exception {
        assertNull(underTest.trim(null));
    }


    @Test
    public void shouldGiveStringInUppercase() throws Exception {
        assertEquals(underTest.upper("abc"), "ABC");
    }

    @Test
    public void shouldGiveStringInUppercase2() throws Exception {
        assertEquals(underTest.upper("ABC"), "ABC");
    }


    @Test
    public void urlEncodeExecute() throws Exception {
        assertEquals("path-seg%2Fment", underTest.urlEncode("path-seg/ment"));
        assertEquals("foo+bar", underTest.urlEncode("foo bar"));
        assertEquals("bar=a&foo=b", underTest.urlEncode(new TreeMap<String, String>() {{
            put("bar", "a");
            put("foo", "b");
        }}));
    }

    @Test
    public void reverse() throws Exception {
        assertEquals("cba", underTest.reverse("abc"));
    }

    @Test
    public void convertFromUtf8ToIso8859_1() throws Exception {
        assertArrayEquals(
                new String("hello".getBytes(), "ISO-8859-1").getBytes(),
                underTest.convertEncoding("hello", "UTF-8", "ISO-8859-1").getBytes());
    }
}