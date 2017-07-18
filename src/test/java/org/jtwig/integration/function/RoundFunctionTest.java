package org.jtwig.integration.function;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.integration.AbstractIntegrationTest;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class RoundFunctionTest extends AbstractIntegrationTest {
    @Test
    public void round() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ round(1.1) }}").render(JtwigModel.newModel());
        assertThat(result, is("1"));
    }
    @Test
    public void round2() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ round(1.5) }}").render(JtwigModel.newModel());
        assertThat(result, is("2"));
    }
    @Test
    public void roundWithPrecisionCeil() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ 1.51 | round(1, 'ceil') }}").render(JtwigModel.newModel());
        assertThat(result, is("1.6"));
    }
    @Test
    public void roundWithPrecisionFloor() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ 1.57 | round(1, 'floor') }}").render(JtwigModel.newModel());
        assertThat(result, is("1.5"));
    }
    @Test
    public void roundWithPrecisionCommon() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ 1.51 | round(1, 'common') }}").render(JtwigModel.newModel());
        assertThat(result, is("1.5"));
    }
    @Test
    public void round3() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ round(1.6) }}").render(JtwigModel.newModel());
        assertThat(result, is("2"));
    }
    @Test
    public void roundCommon() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ round(1.1, 'common') }}").render(JtwigModel.newModel());
        assertThat(result, is("1"));
    }
    @Test
    public void roundCommon2() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ round(1.5, 'common') }}").render(JtwigModel.newModel());
        assertThat(result, is("2"));
    }
    @Test
    public void roundCommon3() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ round(-1.5, 'common') }}").render(JtwigModel.newModel());
        assertThat(result, is("-2"));
    }
    @Test
    public void roundCeiling() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ round(1.1, 'ceil') }}").render(JtwigModel.newModel());
        assertThat(result, is("2"));
    }
    @Test
    public void roundFloor() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ round(1.7, 'floor') }}").render(JtwigModel.newModel());
        assertThat(result, is("1"));
    }
}
