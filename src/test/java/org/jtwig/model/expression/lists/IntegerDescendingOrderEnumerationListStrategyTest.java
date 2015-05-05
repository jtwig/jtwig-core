package org.jtwig.model.expression.lists;

import com.google.common.base.Optional;
import org.jtwig.value.JtwigValue;
import org.jtwig.value.JtwigValueFactory;
import org.junit.Test;

import java.util.Collection;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class IntegerDescendingOrderEnumerationListStrategyTest {
    private final IntegerDescendingOrderEnumerationListStrategy underTest = new IntegerDescendingOrderEnumerationListStrategy();

    @Test
    public void enumerateWhenDifferentTypes() throws Exception {
        JtwigValue left = JtwigValueFactory.create(1);
        JtwigValue right = JtwigValueFactory.create('a');

        Optional<Collection<Object>> result = underTest.enumerate(left, right);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void enumerateWhenNotNumber() throws Exception {
        JtwigValue left = JtwigValueFactory.create('b');
        JtwigValue right = JtwigValueFactory.create('a');

        Optional<Collection<Object>> result = underTest.enumerate(left, right);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void enumerateWhenLeftLowerThanRight() throws Exception {
        JtwigValue left = JtwigValueFactory.create(1);
        JtwigValue right = JtwigValueFactory.create(3);

        Optional<Collection<Object>> result = underTest.enumerate(left, right);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void enumerateWhenLeftGreaterThanRight() throws Exception {
        JtwigValue left = JtwigValueFactory.create(3);
        JtwigValue right = JtwigValueFactory.create(1);

        Optional<Collection<Object>> result = underTest.enumerate(left, right);

        assertThat(result.isPresent(), is(true));
    }
}