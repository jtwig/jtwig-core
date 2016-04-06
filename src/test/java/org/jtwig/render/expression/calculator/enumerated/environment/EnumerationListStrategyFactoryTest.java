package org.jtwig.render.expression.calculator.enumerated.environment;

import org.jtwig.render.expression.calculator.enumerated.CompositeEnumerationListStrategy;
import org.jtwig.render.expression.calculator.enumerated.EnumerationListStrategy;
import org.jtwig.support.MatcherUtils;
import org.junit.Test;

import java.util.Collection;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class EnumerationListStrategyFactoryTest {
    private EnumerationListStrategyFactory underTest = new EnumerationListStrategyFactory();

    @Test
    public void create() throws Exception {
        Collection<EnumerationListStrategy> strategies = asList(mock(EnumerationListStrategy.class));

        EnumerationListStrategy result = underTest.create(strategies);

        assertThat(result, is(MatcherUtils.<EnumerationListStrategy>theSameBean(new CompositeEnumerationListStrategy(strategies))));
    }
}