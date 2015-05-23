package org.jtwig.content.json;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import org.jtwig.util.ClasspathFinder;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JacksonJsonMapperProviderTest {
    private final ClasspathFinder classpathFinder = mock(ClasspathFinder.class);
    private JacksonJsonMapperProvider underTest = new JacksonJsonMapperProvider(classpathFinder);

    @Test
    public void createWhenExists() throws Exception {
        when(classpathFinder.exists(JacksonJsonMapperProvider.CLASS_NAME)).thenReturn(true);

        boolean result = underTest.isFound();

        assertThat(result, is(true));
    }

    @Test
    public void createWhenNotExists() throws Exception {
        when(classpathFinder.exists(Jackson2JsonMapperProvider.CLASS_NAME)).thenReturn(false);

        boolean result = underTest.isFound();

        assertThat(result, is(false));
    }


    @Test
    public void instantiate() throws Exception {
        Function<Object, String> result = underTest.jsonMapper();

        assertThat(result, notNullValue());
    }
}