package org.jtwig.integration.expression;

import org.jtwig.JtwigModel;
import org.jtwig.functions.SimpleFunction;
import org.jtwig.integration.AbstractIntegrationTest;
import org.jtwig.value.JtwigValue;
import org.jtwig.value.JtwigValueFactory;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.jtwig.configuration.ConfigurationBuilder.configuration;

public class CompositionTest extends AbstractIntegrationTest {
    @Test
    public void compose() throws Exception {
        String result = defaultStringTemplate("{{ [1..10] | sum }}", configuration()
                .include(sumFunction()).build())
                .render(JtwigModel.newModel());

        assertThat(result, is("55"));
    }

    @Test
    public void composeWithFunctionWithParenthesis() throws Exception {
        String result = defaultStringTemplate("{{ [1..10] | sum() }}", configuration()
                .include(sumFunction()).build())
                .render(JtwigModel.newModel());

        assertThat(result, is("55"));
    }

    @Test
    public void composeWithFunction() throws Exception {
        String result = defaultStringTemplate("{{ 1 | plus(1) }}", configuration()
                .include(plusFunction()).build())
                .render(JtwigModel.newModel());

        assertThat(result, is("2"));
    }

    private SimpleFunction plusFunction() {
        return new SimpleFunction() {
            @Override
            public String name() {
                return "plus";
            }

            @Override
            public Object execute(Object... arguments) {
                return JtwigValueFactory.create(arguments[0]).mandatoryNumber();
            }
        };
    }

    private SimpleFunction sumFunction() {
        return new SimpleFunction() {
            @Override
            public String name() {
                return "sum";
            }

            @Override
            public Object execute(Object... arguments) {
                BigDecimal result = BigDecimal.ZERO;
                Collection<Object> objects = JtwigValueFactory.create(arguments[0]).asCollection();
                for (Object object : objects) {
                    result = result.add(JtwigValueFactory.create(object).mandatoryNumber());
                }
                return result;
            }
        };
    }
}
