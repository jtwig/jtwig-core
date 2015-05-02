package org.jtwig.integration.expression;

import org.jtwig.JtwigModel;
import org.jtwig.integration.AbstractIntegrationTest;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class MapTest extends AbstractIntegrationTest {
    @Test
    public void map() throws Exception {
        String result = defaultStringTemplate("{{ { one: 'two' } }}")
                .render(JtwigModel.newModel());

        assertThat(result, is("{one=two}"));
    }
}
