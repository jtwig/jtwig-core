package org.jtwig.integration.issues;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.exceptions.CalculationException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.core.StringContains.containsString;
import static org.jtwig.environment.EnvironmentConfigurationBuilder.configuration;

public class Issue076Test {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldGiveNiceExplanationForNonExistingFunctionsWithParams() throws Exception {
        expectedException.expect(CalculationException.class);
        expectedException.expectMessage(containsString("Unable to resolve function 'nonExistingFunction' with arguments [<test> (class java.lang.String), <string> (class java.lang.String), <2> (class java.math.BigDecimal)]"));

        JtwigTemplate
                .inlineTemplate("{{ nonExistingFunction('test', 'string', 2) }}")
                .render(new JtwigModel());
    }

    @Test
    public void shouldGiveNiceExplanationForNonExistingFunctions() throws Exception {
        expectedException.expect(CalculationException.class);
        expectedException.expectMessage(containsString("Unable to resolve function 'nonExistingFunction' with arguments [<test> (class java.lang.String)]"));

        JtwigTemplate
                .inlineTemplate("{{ nonExistingFunction('test') }}")
                .render(new JtwigModel());

    }

    @Test
    public void shouldGiveNiceExplanationForNonExistingVariables() throws Exception {
        expectedException.expect(CalculationException.class);
        expectedException.expectMessage(containsString("Variable 'nonExistingVariable' undefined"));


        JtwigTemplate
                .inlineTemplate("{{ nonExistingVariable.size }}", configuration()
                        .render().withStrictMode(true).and()
                        .build())
                .render(new JtwigModel());
    }
}
