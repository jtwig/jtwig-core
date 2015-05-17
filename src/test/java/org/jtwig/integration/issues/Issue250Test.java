package org.jtwig.integration.issues;

import org.hamcrest.MatcherAssert;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.integration.AbstractIntegrationTest;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class Issue250Test extends AbstractIntegrationTest {
    @Test
    public void numberFormatShouldHandleNullAsZero() throws Exception {
        JtwigModel model = new JtwigModel();

        String result = JtwigTemplate
                .inlineTemplate("{{ null | number_format(2, ',', '.') }}")
                .render(model);

        MatcherAssert.assertThat(result, is(equalTo("0,00")));
    }
}
