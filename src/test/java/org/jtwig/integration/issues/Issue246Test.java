package org.jtwig.integration.issues;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.integration.AbstractIntegrationTest;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class Issue246Test extends AbstractIntegrationTest {
    @Test
    public void outputEscapedDoubleQuoteTest() throws Exception {
        JtwigModel model = new JtwigModel();

        String result = JtwigTemplate
                .inlineTemplate("{{ \"\\\"\" }}")
                .render(model);

        assertThat(result, is(equalTo("\"")));
    }

    @Test
    public void outputEscapedSingleQuoteTest() throws Exception {
        JtwigModel model = new JtwigModel();

        String result = JtwigTemplate
                .inlineTemplate("{{ '\\'' }}")
                .render(model);

        assertThat(result, is(equalTo("'")));
    }
}
