package org.jtwig.functions.impl.json;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import org.jtwig.util.ClasspathFinder;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class Jackson2JsonMapperFactoryTest {
    private final ClasspathFinder classpathFinder = mock(ClasspathFinder.class);
    private Jackson2JsonMapperFactory underTest = new Jackson2JsonMapperFactory(classpathFinder);

    @Test
    public void createWhenExists() throws Exception {
        when(classpathFinder.exists(Jackson2JsonMapperFactory.CLASS_NAME)).thenReturn(true);

        Optional<Function<Object, String>> result = underTest.create();

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), instanceOf(Jackson2JsonMapper.class));
    }

    @Test
    public void createWhenNotExists() throws Exception {
        when(classpathFinder.exists(Jackson2JsonMapperFactory.CLASS_NAME)).thenReturn(false);

        Optional<Function<Object, String>> result = underTest.create();

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void name() throws Exception {
        String result = underTest.name();

        assertThat(result, is("Jackson 2"));
    }
}