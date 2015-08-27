package org.jtwig.integration.issues;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class Issue082Test {
    @Test
    public void issue82() throws Exception {
        JtwigModel model = new JtwigModel();

        String result = JtwigTemplate
                .inlineTemplate("{{ notice }}")
                .render(model);

        assertThat(result, is(equalTo("")));
    }
}
