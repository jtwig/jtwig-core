package org.jtwig.integration.expression;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.integration.AbstractIntegrationTest;
import org.junit.Test;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class IsTest extends AbstractIntegrationTest {
    public static final String HELLO = "one";

    @Test
    public void isOperatorEmptyFunction () throws Exception {
        JtwigModel model = new JtwigModel();

        String result = JtwigTemplate.inlineTemplate("{% if ([] is empty) %}Hi{% endif %}")
                .render(model);

        assertThat(result, is(equalTo("Hi")));
    }

    @Test
    public void isOperatorConstantFunction () throws Exception {
        JtwigModel model = new JtwigModel();

        String result = JtwigTemplate.inlineTemplate("{% if ('one' is constant('org.jtwig.integration.expression.IsTest.HELLO')) %}Hi{% endif %}")
                .render(model);

        assertThat(result, is(equalTo("Hi")));
    }

    @Test
    public void isDefinedFunction () throws Exception {
        JtwigModel model = new JtwigModel();

        String result = JtwigTemplate.inlineTemplate("{% if (variable is not defined) %}Hi{% endif %}")
                .render(model);

        assertThat(result, is(equalTo("Hi")));
    }
    @Test
    public void isEvenFunction () throws Exception {
        JtwigModel model = new JtwigModel();

        String result = JtwigTemplate.inlineTemplate("{% if (1 is not even) %}Hi{% endif %}")
                .render(model);

        assertThat(result, is(equalTo("Hi")));
    }
    @Test
    public void isNotOddFunction () throws Exception {
        JtwigModel model = new JtwigModel();

        String result = JtwigTemplate.inlineTemplate("{% if (1 is not odd) %}Hi{% endif %}")
                .render(model);

        assertThat(result, is(equalTo("")));
    }
    @Test
    public void isOddFunction () throws Exception {
        JtwigModel model = new JtwigModel();

        String result = JtwigTemplate.inlineTemplate("{% if (1 is odd) %}Hi{% endif %}")
                .render(model);

        assertThat(result, is(equalTo("Hi")));
    }
    @Test
    public void isIterableFunction () throws Exception {
        JtwigModel model = new JtwigModel();

        String result = JtwigTemplate.inlineTemplate("{% if ([] is iterable) %}Hi{% endif %}")
                .render(model);

        assertThat(result, is(equalTo("Hi")));
    }
    @Test
    public void isIterableArrayFunction () throws Exception {
        JtwigModel model = new JtwigModel();
        model.add("value", new Object[0]);

        String result = JtwigTemplate.inlineTemplate("{% if (value is iterable) %}Hi{% endif %}")
                .render(model);

        assertThat(result, is(equalTo("Hi")));
    }
    @Test
    public void isIterableMapFunction () throws Exception {
        JtwigModel model = new JtwigModel();
        model.with("value", Collections.EMPTY_MAP);

        String result = JtwigTemplate.inlineTemplate("{% if (value is iterable) %}Hi{% endif %}")
                .render(model);

        assertThat(result, is(equalTo("Hi")));
    }

    @Test
    public void isNullFunction () throws Exception {
        JtwigModel model = new JtwigModel();
        model.with("value", null);

        String result = JtwigTemplate.inlineTemplate("{% if (value is null) %}Hi{% endif %}")
                .render(model);

        assertThat(result, is(equalTo("Hi")));
    }

    @Test
    public void isDivisibleFunction () throws Exception {
        JtwigModel model = new JtwigModel();

        String result = JtwigTemplate.inlineTemplate("{% if (3 is divisible by 1) %}Hi{% else %}OH{% endif %}")
                .render(model);

        assertThat(result, is(equalTo("Hi")));
    }
    
}
