package org.jtwig.resource.classpath;

import org.junit.Test;

import java.io.InputStream;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

public class ResourceLoaderTest {
    private ResourceLoader underTest = new ResourceLoader(ResourceLoaderTest.class.getClassLoader());

    @Test
    public void existsWhenResourceExists() throws Exception {
        String existentResource = "/example/classpath-template.twig";

        boolean result = underTest.exists(existentResource);

        assertThat(result, is(true));
    }

    @Test
    public void existsWhenResourceNotExists() throws Exception {
        String existentResource = "example/nonexistent";

        boolean result = underTest.exists(existentResource);

        assertThat(result, is(false));
    }

    @Test
    public void loadWhenResourceExists() throws Exception {
        String existentResource = "/example/classpath-template.twig";

        InputStream result = underTest.load(existentResource);

        assertThat(result, notNullValue());
    }

    @Test
    public void loadWhenResourceNotExists() throws Exception {
        String existentResource = "/example/baf";

        InputStream result = underTest.load(existentResource);

        assertThat(result, nullValue());
    }
}