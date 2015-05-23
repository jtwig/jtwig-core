package org.jtwig.integration.node;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.functions.SimpleFunction;
import org.jtwig.integration.AbstractIntegrationTest;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.jtwig.environment.EnvironmentConfigurationBuilder.configuration;

public class FilterTest extends AbstractIntegrationTest {
    @Test
    public void filterWithVariable() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{% filter var1 %}1{% endfilter %}", configuration()
                .include(var1Function())
                .build()).render(JtwigModel.newModel());

        assertThat(result, is("12"));
    }

    @Test
    public void filterWithFunction() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{% filter var2(2) %}1{% endfilter %}", configuration()
                .include(var2Function())
                .build()).render(JtwigModel.newModel());

        assertThat(result, is("123"));
    }

    @Test
    public void filterWithComposition() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{% filter var1 | var3 %}1{% endfilter %}", configuration()
                .include(var1Function())
                .include(var3Function())
                .build()).render(JtwigModel.newModel());

        assertThat(result, is("123"));
    }

    private SimpleFunction var3Function() {
        return new SimpleFunction() {
            @Override
            public String name() {
                return "var3";
            }

            @Override
            public Object execute(Object... arguments) {
                return arguments[0].toString() + "3";
            }
        };
    }

    private SimpleFunction var2Function() {
        return new SimpleFunction() {
            @Override
            public String name() {
                return "var2";
            }

            @Override
            public Object execute(Object... arguments) {
                return arguments[0].toString() + arguments[1].toString() + "3";
            }
        };
    }

    private SimpleFunction var1Function() {
        return new SimpleFunction() {
            @Override
            public String name() {
                return "var1";
            }

            @Override
            public Object execute(Object... arguments) {
                return arguments[0].toString() + "2";
            }
        };
    }
}
