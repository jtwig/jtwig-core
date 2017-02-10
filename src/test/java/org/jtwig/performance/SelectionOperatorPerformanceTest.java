package org.jtwig.performance;

import net.jperf.GroupedTimingStatistics;
import net.jperf.StopWatch;
import org.apache.commons.lang3.RandomStringUtils;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.integration.expression.SelectionTest;
import org.jtwig.resource.loader.InMemoryResourceLoader;
import org.jtwig.resource.loader.TypedResourceLoader;
import org.junit.Test;

import static org.jtwig.JtwigModel.newModel;
import static org.jtwig.environment.EnvironmentConfigurationBuilder.configuration;

public class SelectionOperatorPerformanceTest {
    public static final int SIZE = 10000;

    @Test
    public void select() throws Exception {
        GroupedTimingStatistics groupedTimingStatistics = new GroupedTimingStatistics();
        groupedTimingStatistics.setStartTime(System.currentTimeMillis());

        JtwigTemplate jtwigTemplate = JtwigTemplate.inlineTemplate("{{ var.nested.field[0] }}");
        for (int i = 0; i < SIZE; i++) {
            StopWatch stopWatch = new StopWatch("selection");
            String random = RandomStringUtils.random(6);
            JtwigModel jtwigModel = newModel().with("var", new SelectionTest.TestClass(random));
            stopWatch.start();
            jtwigTemplate.render(jtwigModel);
            stopWatch.stop();
            groupedTimingStatistics.addStopWatch(stopWatch);
        }

        groupedTimingStatistics.setStopTime(System.currentTimeMillis());
        System.out.println(groupedTimingStatistics.toString());
    }

    public static void main(String[] args) {
        JtwigTemplate jtwigTemplate = JtwigTemplate.inlineTemplate("{% include 'm:a' %}" +
                "\n" +
                "{% if (var is defined) %}" +
                "{{ var.nested.field[0] }}" +
                "{% endif %}" +
                "\n" +
                "{% for k, v in { test: 'one', test1: 'false' } %}" +
                "{{ k }} => {{ v }}\n" +
                "{% endfor %}" +
                "\n" +
                "{% include 'm:b' %}", configuration()
                .resources().resourceLoaders().add(new TypedResourceLoader("m", InMemoryResourceLoader.builder()
                        .withResource("a", "This is something cool")
                        .withResource("b", "This is something boring")
                        .build()))
                .and().and()
                .build());

        System.out.println(jtwigTemplate.render(newModel().with("var", new SelectionTest.TestClass(RandomStringUtils.randomAlphabetic(6)))));

        while (true) {
            String random = RandomStringUtils.random(6);
            JtwigModel jtwigModel = newModel().with("var", new SelectionTest.TestClass(random));
            jtwigTemplate.render(jtwigModel);
        }
    }
}
