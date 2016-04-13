package org.jtwig.integration.expression;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.integration.AbstractIntegrationTest;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class MapSelectionTest extends AbstractIntegrationTest {
    @Test
    public void mapSelectionWithArray() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ [1, 2][0] }}")
                .render(JtwigModel.newModel());

        assertThat(result, is("1"));
    }

    @Test
    public void mapSelectionUndefined() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{% if (defined([1, 2][5])) %}KO{% else %}OK{% endif %}")
                .render(JtwigModel.newModel());

        assertThat(result, is("OK"));
    }
}
