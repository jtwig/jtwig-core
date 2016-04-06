package org.jtwig.integration.structured;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import org.jtwig.JtwigTemplate;
import org.jtwig.environment.Environment;
import org.jtwig.environment.EnvironmentConfigurationBuilder;
import org.jtwig.integration.AbstractIntegrationTest;
import org.jtwig.resource.Resource;
import org.jtwig.resource.StringResource;
import org.jtwig.resource.resolver.InMemoryResourceResolver;
import org.jtwig.resource.resolver.ResourceResolver;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.jtwig.JtwigModel.newModel;

public class BlockValueContextTest extends AbstractIntegrationTest {
    @Test
    public void extendLogic() throws Exception {

        JtwigTemplate template = JtwigTemplate.inlineTemplate("{% extends 'a' %}{% block name %}{{ var }}{% endblock %}", EnvironmentConfigurationBuilder.configuration()
                .resources().resourceResolvers().add(new ResourceResolver() {
                    @Override
                    public Optional<Resource> resolve(Environment environment, Resource resource, String path) {
                        return Optional.of((Resource) new StringResource("{% for a in [1..2] %}{% block name %}this{% endblock %}{% endfor %}"));
                    }
                }).and()
                .and()
                .build());

        String result = template.render(newModel().with("var", "hello"));

        assertThat(result, is("hellohello"));
    }

    @Test
    public void blockInheritance() throws Exception {
        JtwigTemplate template = JtwigTemplate.inlineTemplate("{% extends 'a' %}{% block a %}a{% endblock %}", EnvironmentConfigurationBuilder.configuration().resources()
                .resourceResolvers().add(new InMemoryResourceResolver(
                        ImmutableMap.<String, Resource>builder()
                                .put("a", new StringResource("{% extends 'b' %}"))
                                .put("b", new StringResource("{% block a %}b{% endblock %}"))
                                .build()
                )).and()
                .and().build());

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
