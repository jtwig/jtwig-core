package org.jtwig.integration.issues;

import org.jtwig.JtwigModel;
import org.jtwig.exceptions.CalculationException;
import org.jtwig.integration.AbstractIntegrationTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.StringContains.containsString;
import static org.jtwig.configuration.ConfigurationBuilder.configuration;

public class Issue140Test extends AbstractIntegrationTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    /**
     * Evaluating an undefined variable in an "is null" statement should throw
     * a calculate exception in strict mode.
     * @throws Exception
     */
    @Test
    public void undefinedVarThrowsExceptionOnEvaluation() throws Exception {
        expectedException.expect(CalculationException.class);
        expectedException.expectMessage(containsString("Variable 'var' undefined"));

        defaultStringTemplate("{{ var is null }}", configuration()
                .withStrictMode(true)
                .build())
                .render(JtwigModel.newModel());
    }

    /**
     * Evaluating an undefined variable in an "is null" statement should return
     * true when strict mode is disabled.
     * @throws Exception
     */
    @Test
    public void outputNonexistentVarThrowsException() throws Exception {
        String result = defaultStringTemplate("{{ var is null }}", configuration()
                .withStrictMode(false)
                .build())
                .render(JtwigModel.newModel());

        assertThat(result, is(equalTo("1")));
    }
}
