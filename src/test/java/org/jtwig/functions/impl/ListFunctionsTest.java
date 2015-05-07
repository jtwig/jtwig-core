package org.jtwig.functions.impl;

import org.jtwig.value.configuration.NamedValueConfiguration;
import org.jtwig.value.configuration.ValueConfiguration;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.*;

public class ListFunctionsTest {
    ListFunctions underTest = new ListFunctions() {
        @Override
        protected ValueConfiguration getConfiguration() {
            return NamedValueConfiguration.COMPATIBLE_MODE;
        }
    };

    @Test
    public void reverseList() throws Exception {
        List result = underTest.reverse(asList("a", "b", "c"));
        assertEquals("c", result.get(0));
        assertEquals("b", result.get(1));
        assertEquals("a", result.get(2));
    }


    @Test
    public void first() throws Exception {
        assertEquals(underTest.first(asList(new String[]{"a", "b"})), "a");
    }

    @Test
    public void last() throws Exception {
        assertEquals(underTest.last(asList(new String[]{"a", "b"})), "b");
    }

    @Test
    public void testExecuteWithList() throws Exception {
        Integer length = underTest.length(asList(new String[]{"a", "b"}));
        assertThat(length, is(2));
    }


    @Test
    public void batchTest() throws Exception {
        List<List<Object>> result = underTest.batch(new String[]{"a", "b", "c", "d", "e", "f"}, 3);
        assertThat(result.get(0).size(), is(3));
        assertThat(result.get(0), hasItem("a"));
        assertThat(result.get(0), hasItem("b"));
        assertThat(result.get(0), hasItem("c"));
        assertThat(result.get(1).size(), is(3));
        assertThat(result.get(1), hasItem("d"));
        assertThat(result.get(1), hasItem("e"));
        assertThat(result.get(1), hasItem("f"));
    }


    @Test
    public void batchWithNotMultipleSize() throws Exception {
        List<List<Object>> result = underTest.batch(new String[]{"a", "b", "c", "d", "e", "f", "g"}, 3);
        assertThat(result.get(0).size(), is(3));
        assertThat(result.get(0), hasItem("a"));
        assertThat(result.get(0), hasItem("b"));
        assertThat(result.get(0), hasItem("c"));
        assertThat(result.get(1).size(), is(3));
        assertThat(result.get(1), hasItem("d"));
        assertThat(result.get(1), hasItem("e"));
        assertThat(result.get(1), hasItem("f"));
        assertThat(result.get(2).size(), is(1));
    }

    @Test
    public void batchWithNotMultipleSizeWithDefault() throws Exception {
        List<List<Object>> result = underTest.batch(new String[]{"a", "b", "c", "d", "e", "f", "g"}, 3, "h");
        assertThat(result.get(0).size(), is(3));
        assertThat(result.get(0), hasItem("a"));
        assertThat(result.get(0), hasItem("b"));
        assertThat(result.get(0), hasItem("c"));
        assertThat(result.get(1).size(), is(3));
        assertThat(result.get(1), hasItem("d"));
        assertThat(result.get(1), hasItem("e"));
        assertThat(result.get(1), hasItem("f"));
        assertThat(result.get(2).size(), is(3));
    }


    @Test
    public void concatenateShouldNotIncludeNullValues () throws Exception {
        String result = underTest.concatenate("Hi! ", null, "My name is Twig!");
        assertThat(result, is("Hi! My name is Twig!"));
    }

    @Test
    public void concatenateShouldGiveEmptyStringIfExecutedWithoutArguments () throws Exception {
        String result = underTest.concatenate();
        assertThat(result, is(""));
    }


    @Test
    public void shouldNotIgnoreNullValues () throws Exception {
        String result = underTest.join(asList(null, "1", "2"), ", ");
        assertThat(result, is(", 1, 2"));
    }

    @Test
    public void shouldThrowExceptionIfInvalidArgumentsAreGiven () throws Exception {
        String execute = underTest.join(null, ", ");
        assertThat(execute, is(""));
    }

    @Test
    public void shouldUseEmptyStringIfSecondArgumentIsNotGiven () throws Exception {
        String result = underTest.join(asList(null, "1", "2"));
        assertThat(result, is("12"));
    }
    @Test
    public void mergeList() throws Exception {
        List result = (List) underTest.merge(asList("a", "b"), asList(1, 2));
        assertEquals("a", result.get(0));
        assertEquals("b", result.get(1));
        assertEquals(1, result.get(2));
        assertEquals(2, result.get(3));
    }
    @Test
    public void mergeArrays() throws Exception {
        Object firstHalf = new String[]{"a", "b"};
        Object secondHalf = new Integer[]{1, 2};
        Object[] result = (Object[]) underTest.merge(firstHalf, secondHalf);
        assertEquals("a", result[0]);
        assertEquals("b", result[1]);
        assertEquals(1, result[2]);
        assertEquals(2, result[3]);
    }
    @Test
    public void mergeMaps() throws Exception {
        Object firstHalf = new HashMap<String, Object>(){{
            put("one", 1);
            put("two", 2);
        }};
        Object secondHalf = new HashMap<String, Object>(){{
            put("three", 3);
            put("four", 4);
        }};
        Map result = (Map) underTest.merge(firstHalf, secondHalf);
        assertEquals(1, result.get("one"));
        assertEquals(2, result.get("two"));
        assertEquals(3, result.get("three"));
        assertEquals(4, result.get("four"));
    }

    @Test
    public void sliceList() throws Exception {
        List result = (List) underTest.slice(asList(1, 2, 3, 4, 5), 1, 2);
        assertEquals(2, result.get(0));
        assertEquals(3, result.get(1));
        assertEquals(2, result.size());
    }
    @Test
    public void sliceString() throws Exception {
        String result = (String) underTest.slice("abc", 1, 2);
        assertEquals("bc", result);
    }
    @Test
    public void sliceStringIncomplete() throws Exception {
        String result = (String) underTest.slice("ab", 1, 2);
        assertEquals("b", result);
    }
    @Test
    public void sliceStringIncompleteBegin() throws Exception {
        String result = (String) underTest.slice("ab", 3, 2);
        assertEquals("", result);
    }
    @Test
    public void maxPrefersIntegersOverCharacters() throws Exception {
        Object result = underTest.max(1,2,'a','e');
        assertEquals(2, result);
    }
    @Test
    public void maxPrefersLowercase() throws Exception {
        Object result = underTest.max('a','A','b');
        assertEquals('b', result);
    }
    @Test
    public void maxWorksAlphabetically() throws Exception {
        Object result = underTest.max("hello","help",'z');
        assertEquals('z', result);
    }
    @Test
    public void minPrefersCharsOverIntegers() throws Exception {
        Object result = underTest.min(1, 2, 'a','e');
        assertEquals('a', result);
    }
    @Test
    public void minPrefersUppercase() throws Exception {
        Object result = underTest.min('a','A');
        assertEquals('A', result);
    }
    @Test
    public void minWorksAlphabetically() throws Exception {
        Object result = underTest.min("hello","help",'z');
        assertEquals("hello", result);
    }
}