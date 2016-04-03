package org.jtwig.integration.function;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class NumberFormatFunctionTest {
    @Test
    public void simpleNumberFormat() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ number_format(1234.567) }}").render(JtwigModel.newModel());

        assertThat(result, is("1234.567"));
    }
    @Test
    public void numberFormatScale() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ number_format(1234.567, 2) }}").render(JtwigModel.newModel());

        assertThat(result, is("1234.57"));
    }
    @Test
    public void numberFormatScaleSignal() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ number_format(1234.567, 2, ',') }}").render(JtwigModel.newModel());

        assertThat(result, is("1234,57"));
    }
    @Test
    public void numberFormatScaleSignalThousands() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ number_format(1234.567, 2, ',', ' ') }}").render(JtwigModel.newModel());

        assertThat(result, is("1 234,57"));
    }
    @Test
    public void numberFormatScaleSignalEmpty() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ number_format(1234.567, 2, '') }}").render(JtwigModel.newModel());

        assertThat(result, is("1234.57"));
    }
    @Test
    public void numberFormatScaleSignalThousandsEmpty() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ number_format(1234.567, 2, '', '') }}").render(JtwigModel.newModel());

        assertThat(result, is("1234.57"));
    }
}
