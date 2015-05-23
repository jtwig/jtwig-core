package org.jtwig.integration.function;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.exceptions.CalculationException;
import org.jtwig.integration.AbstractIntegrationTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringContains.containsString;

public class ParentFunctionTest extends AbstractIntegrationTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void parentFunction() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{% extends 'classpath:/example/extends/extendable-template.twig' %}" +
                "{% block one %}One {{ parent() }}{% endblock %}").render(JtwigModel.newModel());

        assertThat(result, is("One Two"));
    }

    @Test
    public void parentFunctionFail() throws Exception {
        expectedException.expect(CalculationException.class);
        expectedException.expectMessage(containsString("Parent function can only be called inside a block element"));

        JtwigTemplate.inlineTemplate("{{ parent() }}").render(JtwigModel.newModel());
    }
}
