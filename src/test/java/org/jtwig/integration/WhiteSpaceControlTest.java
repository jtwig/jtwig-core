package org.jtwig.integration;

import org.jtwig.JtwigModel;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class WhiteSpaceControlTest extends AbstractIntegrationTest {
    @Test
    public void ifSpaceControl() throws Exception {
        JtwigModel model = new JtwigModel();

        String result = defaultStringTemplate(" {%- if true -%} A {%- endif -%} \n\t")
                .render(model);

        assertThat(result, is(equalTo("A")));
    }

    @Test
    public void elseSpaceControl() throws Exception {
        JtwigModel model = new JtwigModel();

        String result = defaultStringTemplate(" {%- if false -%}{% else -%} A {% endif -%} \n\t")
                .render(model);

        assertThat(result, is(equalTo("A ")));
    }

    @Test
    public void elseIfSpaceControl() throws Exception {
        JtwigModel model = new JtwigModel();

        String result = defaultStringTemplate(" {%- if false -%}{% elseif true -%} A {% endif -%} \n\t")
                .render(model);

        assertThat(result, is(equalTo("A ")));
    }

    @Test
    public void setTest() throws Exception {
        JtwigModel model = new JtwigModel();

        String result = defaultStringTemplate(" {%- set a = 1 %}")
                .render(model);

        assertThat(result, is(equalTo("")));
    }

    @Test
    public void testOutput() throws Exception {
        JtwigModel model = new JtwigModel();

        String result = defaultStringTemplate(" {{- 'hello' -}} ")
                .render(model);

        assertThat(result, is(equalTo("hello")));
    }

    @Test
    public void testComment() throws Exception {
        JtwigModel model = new JtwigModel();

        String result = defaultStringTemplate(" {#- 'hello' -#} ")
                .render(model);

        assertThat(result, is(equalTo("")));
    }

    @Test
    public void testCommentEmpty() throws Exception {
        JtwigModel model = new JtwigModel();

        String result = defaultStringTemplate(" {#--#} ")
                .render(model);

        assertThat(result, is(equalTo("")));
    }

    @Test
    public void testCommentRight() throws Exception {
        JtwigModel model = new JtwigModel();

        String result = defaultStringTemplate(" {# Hola -#} ")
                .render(model);

        assertThat(result, is(equalTo(" ")));
    }

    @Test
    public void testCommentLeft() throws Exception {
        JtwigModel model = new JtwigModel();

        String result = defaultStringTemplate(" {#- Hola #} ")
                .render(model);

        assertThat(result, is(equalTo(" ")));
    }
}
