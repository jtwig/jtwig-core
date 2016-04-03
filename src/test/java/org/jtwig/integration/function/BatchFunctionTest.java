package org.jtwig.integration.function;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class BatchFunctionTest {
    @Test
    public void simpleBatch() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ [1,2,3,4] | batch(2) }}").render(JtwigModel.newModel());

        assertThat(result, is("[[1, 2], [3, 4]]"));
    }

    @Test
    public void batchWithPadding() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ [1,2,3] | batch(2, 0) }}").render(JtwigModel.newModel());

        assertThat(result, is("[[1, 2], [3, 0]]"));
    }

    @Test
    public void batchWithoutPadding() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ [1,2,3] | batch(2) }}").render(JtwigModel.newModel());

        assertThat(result, is("[[1, 2], [3]]"));
    }
}
