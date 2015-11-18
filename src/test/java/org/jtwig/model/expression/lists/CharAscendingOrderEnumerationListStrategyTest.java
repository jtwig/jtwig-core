package org.jtwig.model.expression.lists;

import com.google.common.base.Optional;
import org.jtwig.value.JtwigValue;
import org.jtwig.value.JtwigValueFactory;
import org.jtwig.value.configuration.DefaultValueConfiguration;
import org.jtwig.value.environment.ValueEnvironment;
import org.jtwig.value.environment.ValueEnvironmentFactory;
import org.junit.Test;

import java.util.Collection;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CharAscendingOrderEnumerationListStrategyTest {
    public static final ValueEnvironment VALUE_ENVIRONMENT = new ValueEnvironmentFactory().crete(new DefaultValueConfiguration());
    private CharAscendingOrderEnumerationListStrategy underTest = new CharAscendingOrderEnumerationListStrategy();

    @Test
    public void enumerateWhenDifferentTypes() throws Exception {
        JtwigValue left = JtwigValueFactory.value(1, VALUE_ENVIRONMENT);
        JtwigValue right = JtwigValueFactory.value('a', VALUE_ENVIRONMENT);

        Optional<Collection<Object>> result = underTest.enumerate(left, right);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void enumerateWhenNotChar() throws Exception {
        JtwigValue left = JtwigValueFactory.value(1, VALUE_ENVIRONMENT);
        JtwigValue right = JtwigValueFactory.value(2, VALUE_ENVIRONMENT);

        Optional<Collection<Object>> result = underTest.enumerate(left, right);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void enumerateWhenLeftLowerThanRight() throws Exception {
        JtwigValue left = JtwigValueFactory.value('a', VALUE_ENVIRONMENT);
        JtwigValue right = JtwigValueFactory.value('c', VALUE_ENVIRONMENT);

        Optional<Collection<Object>> result = underTest.enumerate(left, right);

        assertThat(result.isPresent(), is(true));
    }

    @Test
    public void enumerateWhenLeftGreaterThanRight() throws Exception {
        JtwigValue left = JtwigValueFactory.value('c', VALUE_ENVIRONMENT);
        JtwigValue right = JtwigValueFactory.value('a', VALUE_ENVIRONMENT);

        Optional<Collection<Object>> result = underTest.enumerate(left, right);

        assertThat(result.isPresent(), is(false));
    }
}