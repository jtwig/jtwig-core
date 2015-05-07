package org.jtwig.model.expression.lists;

import com.google.common.base.Optional;
import org.jtwig.value.JtwigValue;
import org.jtwig.value.JtwigValueFactory;
import org.jtwig.value.configuration.NamedValueConfiguration;
import org.junit.Test;

import java.util.Collection;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CharDescendingOrderEnumerationListStrategyTest {
    private CharDescendingOrderEnumerationListStrategy underTest = new CharDescendingOrderEnumerationListStrategy();


    @Test
    public void enumerateWhenDifferentTypes() throws Exception {
        JtwigValue left = JtwigValueFactory.value(1, NamedValueConfiguration.COMPATIBLE_MODE);
        JtwigValue right = JtwigValueFactory.value('a', NamedValueConfiguration.COMPATIBLE_MODE);

        Optional<Collection<Object>> result = underTest.enumerate(left, right);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void enumerateWhenNotChar() throws Exception {
        JtwigValue left = JtwigValueFactory.value(1, NamedValueConfiguration.COMPATIBLE_MODE);
        JtwigValue right = JtwigValueFactory.value(2, NamedValueConfiguration.COMPATIBLE_MODE);

        Optional<Collection<Object>> result = underTest.enumerate(left, right);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void enumerateWhenLeftLowerThanRight() throws Exception {
        JtwigValue left = JtwigValueFactory.value('a', NamedValueConfiguration.COMPATIBLE_MODE);
        JtwigValue right = JtwigValueFactory.value('c', NamedValueConfiguration.COMPATIBLE_MODE);

        Optional<Collection<Object>> result = underTest.enumerate(left, right);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void enumerateWhenLeftGreaterThanRight() throws Exception {
        JtwigValue left = JtwigValueFactory.value('c', NamedValueConfiguration.COMPATIBLE_MODE);
        JtwigValue right = JtwigValueFactory.value('a', NamedValueConfiguration.COMPATIBLE_MODE);

        Optional<Collection<Object>> result = underTest.enumerate(left, right);

        assertThat(result.isPresent(), is(true));
    }
}