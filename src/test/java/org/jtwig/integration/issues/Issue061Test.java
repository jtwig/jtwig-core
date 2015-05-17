package org.jtwig.integration.issues;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

public class Issue061Test {

    @Test
    public void issue61() throws Exception {
        JtwigModel model = new JtwigModel();

        String result = JtwigTemplate
                .classpathTemplate("/example/classpath-template.twig")
                .render(model);

        assertThat(result, containsString("Hello"));
    }
}
