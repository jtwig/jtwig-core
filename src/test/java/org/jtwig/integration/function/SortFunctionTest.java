package org.jtwig.integration.function;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.Is.is;

public class SortFunctionTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void simpleSort() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ [3, 2, 1] | sort }}")
                .render(JtwigModel.newModel());

        assertThat(result, is("[1, 2, 3]"));
    }

    @Test
    public void sortEmpty() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ [] | sort }}")
                .render(JtwigModel.newModel());

        assertThat(result, is("[]"));
    }

    @Test
    public void sortSingleton() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ [1] | sort }}")
                .render(JtwigModel.newModel());

        assertThat(result, is("[1]"));
    }

    @Test
    public void sortUnComparable() throws Exception {
        expectedException.expectMessage(containsString("Function sort error: Cannot sort list of uncomparable items"));

        JtwigTemplate.inlineTemplate("{{ var | sort }}")
                .render(JtwigModel.newModel().with("var", asList(new Object(), new Object())));
    }
}
