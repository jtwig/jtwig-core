package org.jtwig.integration.expression;

import org.jtwig.JtwigModel;
import org.jtwig.integration.AbstractIntegrationTest;
import org.jtwig.parser.ParseException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ListTest extends AbstractIntegrationTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void enumeratedList() throws Exception {

        String result = defaultStringTemplate("{{ [1, 10] }}").render(JtwigModel.newModel());

        assertThat(result, is("[1, 10]"));

    }

    @Test
    public void comprehensionListAscendingIntegers() throws Exception {

        String result = defaultStringTemplate("{{ [1..3] }}").render(JtwigModel.newModel());

        assertThat(result, is("[1, 2, 3]"));

    }

    @Test
    public void comprehensionListDescendingIntegers() throws Exception {

        String result = defaultStringTemplate("{{ [3..1] }}").render(JtwigModel.newModel());

        assertThat(result, is("[3, 2, 1]"));
    }

    @Test
    public void comprehensionListDescendingChars() throws Exception {

        String result = defaultStringTemplate("{{ ['c'..'a'] }}").render(JtwigModel.newModel());

        assertThat(result, is("[c, b, a]"));
    }

    @Test
    public void comprehensionListAscendingChars() throws Exception {
        String result = defaultStringTemplate("{{ ['a'..'c'] }}").render(JtwigModel.newModel());

        assertThat(result, is("[a, b, c]"));
    }

    @Test
    public void invalidListMissingClosingParenthesis() throws Exception {
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Expecting end bracket"));

        defaultStringTemplate("{{ ['a'..'c' }}").render(JtwigModel.newModel());
    }

    @Test
    public void invalidListMissingClosingParenthesisEnumeration() throws Exception {
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Expecting end bracket"));

        defaultStringTemplate("{{ ['a', 'c' }}").render(JtwigModel.newModel());
    }
}
