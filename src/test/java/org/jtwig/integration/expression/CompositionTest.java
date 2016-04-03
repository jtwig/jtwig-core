package org.jtwig.integration.expression;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.JtwigFunction;
import org.jtwig.functions.SimpleJtwigFunction;
import org.jtwig.integration.AbstractIntegrationTest;
import org.jtwig.value.WrappedCollection;
import org.jtwig.value.convert.Converter;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;

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
            public Object execute(FunctionRequest request) {
                return ((BigDecimal) request.get(0)).add(BigDecimal.ONE);
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
            public Object execute(FunctionRequest request) {
                BigDecimal result = BigDecimal.ZERO;
                Converter<WrappedCollection> collectionConverter = request.getEnvironment().getValueEnvironment().getCollectionConverter();
                Converter<BigDecimal> numberConverter = request.getEnvironment().getValueEnvironment().getNumberConverter();

                Object list = request.get(0);
                WrappedCollection collection = collectionConverter
                        .convert(list).or(WrappedCollection.singleton(list));

                Iterator<Map.Entry<String, Object>> iterator = collection.iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, Object> next = iterator.next();
                    BigDecimal number = numberConverter.convert(next.getValue()).orThrow(request.getPosition(), String.format("Cannot convert '%s' to number", next.getValue()));
                    result = result.add(number);
                }
                return result;
            }
        };
    }
}
