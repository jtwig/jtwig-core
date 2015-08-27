package org.jtwig.integration.node;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.integration.AbstractIntegrationTest;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class DoTest extends AbstractIntegrationTest {
    @Test
    public void simpleDo() throws Exception {
        JtwigTemplate template = JtwigTemplate.inlineTemplate("{% do 1 + 2 %}");
        String result = template.render(JtwigModel.newModel());
        assertThat(result, is(""));
    }
}
