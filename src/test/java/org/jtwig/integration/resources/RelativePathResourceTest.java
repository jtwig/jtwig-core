package org.jtwig.integration.resources;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class RelativePathResourceTest {
    @Test
    public void relativePath() throws Exception {

        String result = JtwigTemplate.classpathTemplate("/example/base/nested/file.twig")
                .render(JtwigModel.newModel());

        assertThat(result, is("Hello"));
    }


}
