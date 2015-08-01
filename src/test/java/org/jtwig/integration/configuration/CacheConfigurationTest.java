package org.jtwig.integration.configuration;

import org.jtwig.JtwigTemplate;
import org.jtwig.environment.EnvironmentConfiguration;
import org.jtwig.parser.cache.LimitedTemplateCacheProvider;
import org.jtwig.parser.cache.NoTemplateCacheProvider;
import org.jtwig.parser.cache.PersistentTemplateCacheProvider;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.jtwig.JtwigModel.newModel;
import static org.jtwig.environment.EnvironmentConfigurationBuilder.configuration;

public class CacheConfigurationTest {
    @Test
    public void noCache() throws Exception {
        String template = "{{ 'Hello' }}";

        EnvironmentConfiguration configuration = configuration()
                .parser().withCacheProvider(new NoTemplateCacheProvider())
                .and().build();

        String result1 = JtwigTemplate.inlineTemplate(template, configuration)
                .render(newModel());

        String result2 = JtwigTemplate.inlineTemplate(template, configuration)
                .render(newModel());

        assertThat(result1, is("Hello"));
        assertThat(result2, is("Hello"));
    }

    @Test
    public void persistentCache() throws Exception {
        String template = "{{ 'Hello' }}";

        EnvironmentConfiguration configuration = configuration()
                .parser().withCacheProvider(new PersistentTemplateCacheProvider())
                .and().build();

        String result1 = JtwigTemplate.inlineTemplate(template, configuration)
                .render(newModel());

        String result2 = JtwigTemplate.inlineTemplate(template, configuration)
                .render(newModel());

        assertThat(result1, is("Hello"));
        assertThat(result2, is("Hello"));
    }

    @Test
    public void limitedCache() throws Exception {
        String template = "{{ 'Hello' }}";

        EnvironmentConfiguration configuration = configuration()
                .parser().withCacheProvider(new LimitedTemplateCacheProvider(1))
                .and().build();

        String result1 = JtwigTemplate.inlineTemplate(template, configuration)
                .render(newModel());

        String result2 = JtwigTemplate.inlineTemplate(template, configuration)
                .render(newModel());

        assertThat(result1, is("Hello"));
        assertThat(result2, is("Hello"));
    }
}
