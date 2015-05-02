package org.jtwig.model.expression.lists;

import com.google.common.base.Optional;
import org.jtwig.util.JtwigValue;
import org.junit.Test;

import java.util.Collection;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class IntegerAscendingOrderEnumerationListStrategyTest {
    private IntegerAscendingOrderEnumerationListStrategy underTest = new IntegerAscendingOrderEnumerationListStrategy();

    @Test
    public void enumerateWhenDifferentTypes() throws Exception {
        JtwigValue left = new JtwigValue(1);
        JtwigValue right = new JtwigValue('a');

        Optional<Collection<Object>> result = underTest.enumerate(left, right);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void enumerateWhenNotNumber() throws Exception {
        JtwigValue left = new JtwigValue('b');
        JtwigValue right = new JtwigValue('a');

        Optional<Collection<Object>> result = underTest.enumerate(left, right);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void enumerateWhenLeftLowerThanRight() throws Exception {
        JtwigValue left = new JtwigValue(1);
        JtwigValue right = new JtwigValue(3);

        Optional<Collection<Object>> result = underTest.enumerate(left, right);

        assertThat(result.isPresent(), is(true));
    }

    @Test
    public void enumerateWhenLeftGreaterThanRight() throws Exception {
        JtwigValue left = new JtwigValue(3);
        JtwigValue right = new JtwigValue(1);

        Optional<Collection<Object>> result = underTest.enumerate(left, right);

        assertThat(result.isPresent(), is(false));
    }
}