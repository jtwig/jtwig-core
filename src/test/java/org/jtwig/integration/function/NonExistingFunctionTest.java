package org.jtwig.integration.function;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.exceptions.CalculationException;
import org.jtwig.integration.AbstractIntegrationTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.core.StringContains.containsString;

public class NonExistingFunctionTest extends AbstractIntegrationTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void nonExistingWithNames() throws Exception {
        JtwigTemplate template = defaultStringTemplate("{{ callMeBaby(one = 'test', two = 1) }}");

        expectedException.expect(CalculationException.class);
        expectedException.expectMessage(containsString("Unable to resolve function 'callMeBaby' with arguments [<test> (one), <1> (two)]"));

        template.render(JtwigModel.newModel());
    }

    @Test
    public void nonExistingWithoutNames() throws Exception {
        JtwigTemplate template = defaultStringTemplate("{{ callMeBaby('test', 1) }}");

        expectedException.expect(CalculationException.class);
        expectedException.expectMessage(containsString("Unable to resolve function 'callMeBaby' with arguments [<test>, <1>]"));

        template.render(JtwigModel.newModel());
    }
}
