package org.jtwig.resource.config;

import org.jtwig.resource.resolver.ResourceResolver;
import org.junit.Test;

import java.nio.charset.Charset;

import static java.util.Arrays.asList;
import static org.jtwig.support.MatcherUtils.theSameBean;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class ResourceConfigurationBuilderTest {

    @Test
    public void cloneConstruct() throws Exception {
        ResourceConfiguration prototype = new ResourceConfiguration(
                asList(mock(ResourceResolver.class)),
                Charset.defaultCharset()
        );

        ResourceConfiguration result = new ResourceConfigurationBuilder<>(prototype).build();
        
        assertThat(result, theSameBean(prototype));
    }
}