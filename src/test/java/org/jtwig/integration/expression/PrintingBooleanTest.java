package org.jtwig.integration.expression;

import org.jtwig.integration.AbstractIntegrationTest;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PrintingBooleanTest extends AbstractIntegrationTest {
    @Test
    public void printTrue() throws Exception {
        String result = printTemplate("{{ true }}");

        assertThat(result, is("1"));
    }

    @Test
    public void printFalse() throws Exception {
        String result = printTemplate("{{ false }}");

        assertThat(result, is(""));
    }
}
