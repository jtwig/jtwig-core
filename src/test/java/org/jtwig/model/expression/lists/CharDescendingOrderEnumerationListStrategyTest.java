package org.jtwig.model.expression.lists;

import com.google.common.base.Optional;
import org.jtwig.util.JtwigValue;
import org.junit.Test;

import java.util.Collection;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CharDescendingOrderEnumerationListStrategyTest {
    private CharDescendingOrderEnumerationListStrategy underTest = new CharDescendingOrderEnumerationListStrategy();


    @Test
    public void enumerateWhenDifferentTypes() throws Exception {
        JtwigValue left = new JtwigValue(1);
        JtwigValue right = new JtwigValue('a');

        Optional<Collection<Object>> result = underTest.enumerate(left, right);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void enumerateWhenNotChar() throws Exception {
        JtwigValue left = new JtwigValue(1);
        JtwigValue right = new JtwigValue(2);

        Optional<Collection<Object>> result = underTest.enumerate(left, right);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void enumerateWhenLeftLowerThanRight() throws Exception {
        JtwigValue left = new JtwigValue('a');
        JtwigValue right = new JtwigValue('c');

        Optional<Collection<Object>> result = underTest.enumerate(left, right);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void enumerateWhenLeftGreaterThanRight() throws Exception {
        JtwigValue left = new JtwigValue('c');
        JtwigValue right = new JtwigValue('a');

        Optional<Collection<Object>> result = underTest.enumerate(left, right);

        assertThat(result.isPresent(), is(true));
    }
}