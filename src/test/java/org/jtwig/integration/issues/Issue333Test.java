package org.jtwig.integration.issues;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.jtwig.environment.EnvironmentConfigurationBuilder.configuration;

public class Issue333Test {
    @Test
    public void reproduceIssue() throws Exception {
        String result = JtwigTemplate.classpathTemplate("issues/333.twig", configuration()
                .render().withStrictMode(true).and()
                .build())
                .render(JtwigModel.newModel());

        assertThat(result.trim(), is("Hello world!"));
    }
}
