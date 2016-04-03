package org.jtwig.render.expression.calculator.enumerated;

import com.google.common.base.Optional;
import org.jtwig.render.RenderRequest;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CompositeEnumerationListStrategyTest {
    private final ArrayList<EnumerationListStrategy> strategies = new ArrayList<>();
    private CompositeEnumerationListStrategy underTest = new CompositeEnumerationListStrategy(strategies);

    @Before
    public void setUp() throws Exception {
        strategies.clear();
    }

    @Test
    public void enumerateWhenNoStrategy() throws Exception {
        RenderRequest renderRequest = mock(RenderRequest.class);

        Optional<List<Object>> result = underTest.enumerate(renderRequest, new Object(), new Object());

        assertFalse(result.isPresent());
    }

    @Test
    public void enumerateWhenStrategy() throws Exception {
        Object left = new Object();
        Object right = new Object();
        Optional<List<Object>> listOptional = Optional.of((List<Object>) mock(List.class));
        RenderRequest renderRequest = mock(RenderRequest.class);
        EnumerationListStrategy enumerationListStrategy = mock(EnumerationListStrategy.class);

        strategies.add(enumerationListStrategy);
        when(enumerationListStrategy.enumerate(renderRequest, left, right)).thenReturn(listOptional);

        Optional<List<Object>> result = underTest.enumerate(renderRequest, left, right);

        assertSame(listOptional, result);
    }
}