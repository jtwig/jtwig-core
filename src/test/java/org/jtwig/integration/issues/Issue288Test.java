package org.jtwig.integration.issues;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class Issue288Test {
    @Test
    public void valueAccessTest() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ [1,2,3][0] }}").render(JtwigModel.newModel());

        assertThat(result, is("1"));
    }

    @Test
    public void valueAccessSplitTest() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ ('te st'|split(' '))[0] }}").render(JtwigModel.newModel());

        assertThat(result, is("te"));
    }
}
