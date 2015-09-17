package org.jtwig.integration.expression;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.functions.JtwigFunction;
import org.jtwig.functions.JtwigFunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;
import org.jtwig.integration.AbstractIntegrationTest;
import org.jtwig.value.JtwigValue;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.jtwig.environment.EnvironmentConfigurationBuilder.configuration;

public class CompositionTest extends AbstractIntegrationTest {
    @Test
    public void compose() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ [1..10] | sum }}", configuration()
                .functions().withFunction(sumFunction()).and().build())
                .render(JtwigModel.newModel());

        assertThat(result, is("55"));
    }

    @Test
    public void composeWithFunctionWithParenthesis() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ [1..10] | sum() }}", configuration()
                .functions().withFunction(sumFunction()).and().build())
                .render(JtwigModel.newModel());

        assertThat(result, is("55"));
    }

    @Test
    public void composeWithFunction() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ 1 | plus(1) }}", configuration()
                .functions().withFunction(plusFunction()).and().build())
                .render(JtwigModel.newModel());

        assertThat(result, is("2"));
    }

    private JtwigFunction plusFunction() {
        return new SimpleJtwigFunction() {
            @Override
            public String name() {
                return "plus";
            }

            @Override
            public Object execute(JtwigFunctionRequest request) {
                return request.get(0).mandatoryNumber().add(BigDecimal.ONE);
            }
        };
    }

    private JtwigFunction sumFunction() {
        return new SimpleJtwigFunction() {
            @Override
            public String name() {
                return "sum";
            }

            @Override
            public Object execute(JtwigFunctionRequest request) {
                BigDecimal result = BigDecimal.ZERO;
                Collection<JtwigValue> objects = request.get(0).asCollectionOfValues();
                for (JtwigValue object : objects) {
                    result = result.add(object.mandatoryNumber());
                }
                return result;
            }
        };
    }
}
