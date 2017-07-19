package org.jtwig.functions.impl.mixed;

import com.google.common.collect.ImmutableMap;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DumpFunctionTest {
    @Test
    public void dumpEnvironment() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ dump() }}")
                .render(JtwigModel.newModel().with("test", "one"));

        assertThat(result, is("{parent:{jtwigModel:{values:{test:{value:one}}}},current:{values:{}}}"));
    }


    @Test
    public void dumpVariable() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ dump(test) }}")
                .render(JtwigModel.newModel().with("test", ImmutableMap.builder().put("test", "one").put("test2", "two").build()));

        assertThat(result, containsString("{entries:[{key:test,value:one}"));
    }
}