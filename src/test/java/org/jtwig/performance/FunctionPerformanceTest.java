package org.jtwig.performance;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.functions.JtwigFunction;
import org.jtwig.functions.JtwigFunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;
import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;

import static org.jtwig.environment.EnvironmentConfigurationBuilder.configuration;

public class FunctionPerformanceTest {
    private final int TRIES = 1000;

    @Test
    public void functionTest() throws Exception {
        String template = "{{ first('12') }}";

        Instant start = Instant.now();

        for (int i = 0; i < TRIES; i++) {
            render(template);
        }

        System.out.println(Duration.between(start, Instant.now()));
    }

    @Test
    public void simpleFunctionTest() throws Exception {
        String template = "{{ firstSimple('12') }}";
        Instant start = Instant.now();

        for (int i = 0; i < TRIES; i++) {
            JtwigTemplate.inlineTemplate(template, configuration()
                    .functions()
                    .withFunctions(Collections.singleton(simpleFunction()))
                    .and()
                    .build())
                    .render(JtwigModel.newModel());
        }

        System.out.println(Duration.between(start, Instant.now()));
    }

    private JtwigFunction simpleFunction() {
        return new SimpleJtwigFunction() {
            @Override
            public String name() {
                return "firstSimple";
            }

            @Override
            public Object execute(JtwigFunctionRequest request) {
                return request.get(0).asString().charAt(0);
            }
        };
    }

    private void render(String template) {
        JtwigTemplate.inlineTemplate(template)
                .render(JtwigModel.newModel());
    }
}
