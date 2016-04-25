package org.jtwig.resource.loader;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ClasspathResourceLoaderIntegrationTest {
    private ClasspathResourceLoader underTest = new ClasspathResourceLoader(getClass().getClassLoader());

    @Test
    public void exists() throws Exception {
        boolean result = underTest.exists("/example/classpath-error.twig");

        assertThat(result, is(true));
    }
}