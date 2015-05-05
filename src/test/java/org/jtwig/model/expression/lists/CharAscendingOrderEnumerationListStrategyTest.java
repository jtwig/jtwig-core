package org.jtwig.model.expression.lists;

import com.google.common.base.Optional;
import org.jtwig.value.JtwigValue;
import org.jtwig.value.JtwigValueFactory;
import org.junit.Test;

import java.util.Collection;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CharAscendingOrderEnumerationListStrategyTest {
    private CharAscendingOrderEnumerationListStrategy underTest = new CharAscendingOrderEnumerationListStrategy();

    @Test
    public void enumerateWhenDifferentTypes() throws Exception {
        JtwigValue left = JtwigValueFactory.create(1);
        JtwigValue right = JtwigValueFactory.create('a');

        Optional<Collection<Object>> result = underTest.enumerate(left, right);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void enumerateWhenNotChar() throws Exception {
        JtwigValue left = JtwigValueFactory.create(1);
        JtwigValue right = JtwigValueFactory.create(2);

        Optional<Collection<Object>> result = underTest.enumerate(left, right);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void enumerateWhenLeftLowerThanRight() throws Exception {
        JtwigValue left = JtwigValueFactory.create('a');
        JtwigValue right = JtwigValueFactory.create('c');

        Optional<Collection<Object>> result = underTest.enumerate(left, right);

        assertThat(result.isPresent(), is(true));
    }

    @Test
    public void enumerateWhenLeftGreaterThanRight() throws Exception {
        JtwigValue left = JtwigValueFactory.create('c');
        JtwigValue right = JtwigValueFactory.create('a');

        Optional<Collection<Object>> result = underTest.enumerate(left, right);

        assertThat(result.isPresent(), is(false));
    }
}