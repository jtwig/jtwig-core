package org.jtwig.integration.function;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.Is.is;

public class ConstantFunctionTest {
    public static final String HELLO = "hello";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void constantValue() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ constant('org.jtwig.integration.function.ConstantFunctionTest.HELLO') }}").render(JtwigModel.newModel());

        assertThat(result, is(HELLO));
    }
    @Test
    public void constantCompareValue() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ constant('oi','org.jtwig.integration.function.ConstantFunctionTest.HELLO') }}").render(JtwigModel.newModel());

        assertThat(result, is("false"));
    }
    @Test
    public void constantCompareValueTrue() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ constant('hello','org.jtwig.integration.function.ConstantFunctionTest.HELLO') }}").render(JtwigModel.newModel());

        assertThat(result, is("true"));
    }

    @Test
    public void constantInvalid() throws Exception {
        expectedException.expectMessage(containsString("Function constant error: Invalid constant specified 'orgConstantFunctionTestHELLO'"));

        JtwigTemplate.inlineTemplate("{{ constant('orgConstantFunctionTestHELLO') }}").render(JtwigModel.newModel());
    }

    @Test
    public void constantClassNotFound() throws Exception {
        expectedException.expectMessage(containsString("Function constant error: Class org.ConstantFunctionTest not found"));

        JtwigTemplate.inlineTemplate("{{ constant('org.ConstantFunctionTest.HELLO') }}").render(JtwigModel.newModel());
    }

    @Test
    public void constantFieldNotFound() throws Exception {
        expectedException.expectMessage(containsString("Function constant error: Class org.jtwig.integration.function.ConstantFunctionTest does not expose constant OLA"));

        JtwigTemplate.inlineTemplate("{{ constant('org.jtwig.integration.function.ConstantFunctionTest.OLA') }}").render(JtwigModel.newModel());
    }
}
