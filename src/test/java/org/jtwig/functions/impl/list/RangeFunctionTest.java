package org.jtwig.functions.impl.list;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RangeFunctionTest {

    @Test
    public void shouldPrintNumbersInRangeWithoutStep() {
        String renderedTemplate = JtwigTemplate.inlineTemplate("{% for item in range(0, 10) %}{{ item }}{% endfor %}").render(JtwigModel.newModel());

        assertEquals("0123456789", renderedTemplate);
    }

    @Test
    public void shouldPrintNumbersInRangeWithStep() {
        String renderedTemplate = JtwigTemplate.inlineTemplate("{% for item in range(0, 10, 2) %}{{ item }}{% endfor %}").render(JtwigModel.newModel());

        assertEquals("02468", renderedTemplate);
    }

    @Test
    public void shouldPrintNumbersWithNegativeRange() {
        String renderedTemplate = JtwigTemplate.inlineTemplate("{% for item in range(0, 10, -1) %}{{ item }}{% endfor %}").render(JtwigModel.newModel());

        assertEquals("10987654321", renderedTemplate);
    }

    @Test
    public void shouldPrintNumbersWithNegativeRangeGreaterThanNegativeOne() {
        String renderedTemplate = JtwigTemplate.inlineTemplate("{% for item in range(0, 10, -2) %}{{ item }}{% endfor %}").render(JtwigModel.newModel());

        assertEquals("108642", renderedTemplate);
    }

}
