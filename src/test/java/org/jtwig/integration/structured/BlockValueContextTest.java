package org.jtwig.integration.structured;

import org.jtwig.JtwigTemplate;
import org.jtwig.integration.AbstractIntegrationTest;
import org.jtwig.resource.loader.InMemoryResourceLoader;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.jtwig.JtwigModel.newModel;
import static org.jtwig.environment.EnvironmentConfigurationBuilder.configuration;
import static org.jtwig.resource.reference.ResourceReference.MEMORY;

public class BlockValueContextTest extends AbstractIntegrationTest {
    @Test
    public void extendLogic() throws Exception {

        JtwigTemplate template = JtwigTemplate.inlineTemplate("{% extends 'memory:a' %}{% block name %}{{ var }}{% endblock %}",
                configuration()
                        .resources().resourceLoaders().add(MEMORY, InMemoryResourceLoader.builder()
                        .withResource("a", "{% for a in [1..2] %}{% block name %}this{% endblock %}{% endfor %}")
                        .build()).and().and()
                        .build()
        );

        String result = template.render(newModel().with("var", "hello"));

        assertThat(result, is("hellohello"));
    }

    @Test
    public void blockInheritance() throws Exception {
        JtwigTemplate template = JtwigTemplate.inlineTemplate("{% extends 'memory:a' %}{% block a %}a{% endblock %}",
                configuration()
                        .resources().resourceLoaders().add(MEMORY, InMemoryResourceLoader.builder()
                        .withResource("a", "{% extends 'memory:b' %}")
                        .withResource("b", "{% block a %}b{% endblock %}")
                        .build()).and().and()
                        .build()
        );

        String result = template.render(newModel());

        assertThat(result, is("a"));
    }

    @Test
    public void secondExample() throws Exception {
        Collection<Presentation> list = new ArrayList<>();
        list.add(new Presentation("asd", "1", "2"));
        list.add(new Presentation("test", "3", "4"));

        String result = JtwigTemplate.classpathTemplate("/example/structure/index-jtwig.twig")
                .render(newModel().with("presentations", list));

        assertThat(result, equalTo("asd - 1test - 3"));
    }

    public static class Presentation {
        private final String title;
        private final String speakerName;
        private final String summary;

        public Presentation(String title, String speakerName, String summary) {
            this.title = title;
            this.speakerName = speakerName;
            this.summary = summary;
        }

        public String getTitle() {
            return title;
        }

        public String getSpeakerName() {
            return speakerName;
        }

        public String getSummary() {
            return summary;
        }
    }
}
