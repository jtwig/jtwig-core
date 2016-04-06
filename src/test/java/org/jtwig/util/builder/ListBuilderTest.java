package org.jtwig.util.builder;

import com.google.common.base.Predicate;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;

public class ListBuilderTest {
    @Test
    public void setOverrides() throws Exception {
        List<String> strings = Collections.singletonList("preAdded");
        ListBuilder<Object, String> underTest = new ListBuilder<>(new Object(), strings);

        List<String> result = underTest.set(Collections.<String>emptyList()).build();

        assertThat(result.size(), is(0));
    }

    @Test
    public void addAppends() throws Exception {
        String preAdded = "preAdded";
        List<String> strings = Collections.singletonList(preAdded);
        ListBuilder<Object, String> underTest = new ListBuilder<>(new Object(), strings);

        String item = "item";
        List<String> result = underTest.add(item).build();

        assertThat(result.size(), is(2));
        assertThat(result.contains(preAdded), is(true));
        assertThat(result.contains(item), is(true));
    }

    @Test
    public void addListAppends() throws Exception {
        String preAdded = "preAdded";
        List<String> strings = Collections.singletonList(preAdded);
        ListBuilder<Object, String> underTest = new ListBuilder<>(new Object(), strings);

        String item1 = "item1";
        String item2 = "item2";
        List<String> result = underTest.add(asList(item1, item2)).build();

        assertThat(result.size(), is(3));
        assertThat(result.contains(preAdded), is(true));
        assertThat(result.contains(item1), is(true));
        assertThat(result.contains(item2), is(true));
    }

    @Test
    public void filter() throws Exception {
        String preAdded1 = "preAdded1";
        final String preAdded2 = "preAdded2";
        List<String> strings = asList(preAdded1, preAdded2);
        ListBuilder<Object, String> underTest = new ListBuilder<>(new Object(), strings);

        String item1 = "item1";
        String item2 = "item2";
        List<String> result = underTest.filter(new Predicate<String>() {
            @Override
            public boolean apply(String input) {
                return input.equals(preAdded2);
            }
        }).build();

        assertThat(result.size(), is(1));
        assertThat(result.contains(preAdded1), is(false));
        assertThat(result.contains(preAdded2), is(true));
    }


    @Test
    public void andReturnsArgument() throws Exception {
        Object parent = new Object();
        ListBuilder<Object, String> underTest = new ListBuilder<>(parent);

        assertSame(underTest.and(), parent);
    }
}