package org.jtwig.model.expression.lists;

import com.google.common.base.Optional;
import org.jtwig.value.JtwigValue;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CompositeEnumerationListStrategyTest {
    private final ArrayList<EnumerationListStrategy> strategies = new ArrayList<>();
    private final JtwigValue left = mock(JtwigValue.class);
    private final JtwigValue right = mock(JtwigValue.class);
    private CompositeEnumerationListStrategy underTest = new CompositeEnumerationListStrategy(strategies);

    @Before
    public void setUp() throws Exception {
        strategies.clear();
    }

    @Test
    public void compositeWhenFound() throws Exception {
        Collection<Object> list = Collections.emptyList();
        EnumerationListStrategy strategy = mock(EnumerationListStrategy.class);
        when(strategy.enumerate(left, right)).thenReturn(Optional.of(list));
        strategies.add(strategy);

        Optional<Collection<Object>> result = underTest.enumerate(left, right);

        assertThat(result.get(), is(list));
    }

    @Test
    public void compositeWhenNotFound() throws Exception {
        EnumerationListStrategy strategy = mock(EnumerationListStrategy.class);
        when(strategy.enumerate(left, right)).thenReturn(Optional.<Collection<Object>>absent());
        strategies.add(strategy);

        Optional<Collection<Object>> result = underTest.enumerate(left, right);

        assertThat(result.isPresent(), is(false));
    }
}