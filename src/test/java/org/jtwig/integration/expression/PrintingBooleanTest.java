package org.jtwig.integration.expression;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.integration.AbstractIntegrationTest;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PrintingBooleanTest extends AbstractIntegrationTest {
    @Test
    public void printTrue() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ true }}").render(JtwigModel.newModel());

        assertThat(result, is("1"));
    }

    @Test
    public void printFalse() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ false }}").render(JtwigModel.newModel());

        assertThat(result, is(""));
    }
}
