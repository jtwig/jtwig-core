package org.jtwig.integration.function;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.Is.is;

public class SliceFunctionTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void simpleSlice() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ [1,2,3,4,5] | slice(2,3) }}").render(JtwigModel.newModel());

        assertThat(result, is("[3, 4, 5]"));
    }

    @Test
    public void sliceStartBigger() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ [1,2,3,4,5] | slice(5,3) }}").render(JtwigModel.newModel());

        assertThat(result, is("[]"));
    }

    @Test
    public void sliceStartLower() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ [1,2,3,4,5] | slice(-5,3) }}").render(JtwigModel.newModel());

        assertThat(result, is("[]"));
    }

    @Test
    public void simpleSliceString() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ '12345' | slice(2,3) }}").render(JtwigModel.newModel());

        assertThat(result, is("345"));
    }

    @Test
    public void simpleSliceStringStartBiggerThanString() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ '12345' | slice(10,3) }}").render(JtwigModel.newModel());

        assertThat(result, is(""));
    }

    @Test
    public void sliceWrongNumberOfArguments() throws Exception {
        expectedException.expectMessage(containsString("Function slice error: Expected at most 3 arguments"));

        JtwigTemplate.inlineTemplate("{{ '12345' | slice(10,3,4) }}").render(JtwigModel.newModel());
    }

    @Test
    public void sliceWrongNumberOfArgumentsBelowMinimum() throws Exception {
        expectedException.expectMessage(containsString("Function slice error: Expected at least 3 arguments"));

        JtwigTemplate.inlineTemplate("{{ '12345' | slice(10) }}").render(JtwigModel.newModel());
    }
}
