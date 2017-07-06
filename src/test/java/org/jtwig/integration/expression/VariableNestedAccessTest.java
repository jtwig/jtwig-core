package org.jtwig.integration.expression;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class VariableNestedAccessTest {

    public static final int EXPECTED_VALUE = 32;
    private NestedTestClass testobject;
    private ImmutableMap<String, Object> testmap;
    private ImmutableList<Object> teslist;

    private void testWith(String template) {
        JtwigModel model = JtwigModel.newModel()
                .with("map", testmap)
                .with("list", teslist)
                .with("object", testobject);

        String result = JtwigTemplate.inlineTemplate(template).render(model);

        assertThat(result, is(String.valueOf(EXPECTED_VALUE)));
    }

    @Before
    public void prepareTestClass() {
        testobject = new NestedTestClass();
    }

    @Before
    public void prepareTestMap() {
        testmap = ImmutableMap.of(
                "map", ImmutableMap.of(
                        "key", EXPECTED_VALUE
                ),
                "list", ImmutableList.of(
                        EXPECTED_VALUE
                ),
                "object", new NestedTestClass()
        );
    }

    @Before
    public void prepareTestList() {
        teslist = ImmutableList.of(
                ImmutableList.of(
                        EXPECTED_VALUE
                ),
                ImmutableMap.of(
                        "key", EXPECTED_VALUE
                ),
                new NestedTestClass()
        );
    }

    @Test
    public void mapMapTest() {
        testWith("{{ map['map']['key'] }}");
    }

    @Test
    public void mapMapDotTest() {
        testWith("{{ map['map'].key }}");
    }

    @Test
    public void mapListTest() {
        testWith("{{ map['list'][0] }}");
    }

    @Test
    public void mapMethodTest() {
        testWith("{{ map['object'].valueMethod() }}");
    }

    @Test
    public void mapFieldTest() {
        testWith("{{ map['object'].value_field }}");
    }

    @Test
    public void mapGetterTest() {
        testWith("{{ map['object'].valueGetter }}");
    }


    @Test
    public void mapDotMapTest() {
        testWith("{{ map.map['key'] }}");
    }

    @Test
    public void mapDotMapDotTest() {
        testWith("{{ map.map.key }}");
    }

    @Test
    public void mapDotListTest() {
        testWith("{{ map.list[0] }}");
    }

    @Test
    public void mapDotMethodTest() {
        testWith("{{ map.object.valueMethod() }}");
    }

    @Test
    public void mapDotFieldTest() {
        testWith("{{ map.object.value_field }}");
    }

    @Test
    public void mapDotGetterTest() {
        testWith("{{ map.object.valueGetter }}");
    }


    @Test
    public void listMapTest() {
        testWith("{{ list[1]['key'] }}");
    }

    @Test
    public void listMapDotTest() {
        testWith("{{ list[1].key }}");
    }

    @Test
    public void listListTest() {
        testWith("{{ list[0][0] }}");
    }

    @Test
    public void listMethodTest() {
        testWith("{{ list[2].valueMethod() }}");
    }

    @Test
    public void listFieldTest() {
        testWith("{{ list[2].value_field }}");
    }

    @Test
    public void listGetterTest() {
        testWith("{{ list[2].valueGetter }}");
    }


    @Test
    public void methodMapTest() {
        testWith("{{ object.mapMethod()['key'] }}");
    }

    @Test
    public void methodMapDotTest() {
        testWith("{{ object.mapMethod().key }}");
    }

    @Test
    public void methodListTest() {
        testWith("{{ object.listMethod()[0] }}");
    }

    @Test
    public void methodMethodTest() {
        testWith("{{ object.nestedMethod().valueMethod() }}");
    }

    @Test
    public void methodFieldTest() {
        testWith("{{ object.nestedMethod().value_field }}");
    }

    @Test
    public void methodGetterTest() {
        testWith("{{ object.nestedMethod().valueGetter }}");
    }


    @Test
    public void fieldMapTest() {
        testWith("{{ object.map_field['key'] }}");
    }

    @Test
    public void fieldMapDotTest() {
        testWith("{{ object.map_field.key }}");
    }

    @Test
    public void fieldListTest() {
        testWith("{{ object.list_field[0] }}");
    }

    @Test
    public void fieldMethodTest() {
        testWith("{{ object.nested_field.valueMethod() }}");
    }

    @Test
    public void fieldFieldTest() {
        testWith("{{ object.nested_field.value_field }}");
    }

    @Test
    public void fieldGetterTest() {
        testWith("{{ object.nested_field.valueGetter }}");
    }


    @Test
    public void getterMapTest() {
        testWith("{{ object.mapGetter['key'] }}");
    }

    @Test
    public void getterMapDotTest() {
        testWith("{{ object.mapGetter.key }}");
    }

    @Test
    public void getterListTest() {
        testWith("{{ object.listGetter[0] }}");
    }

    @Test
    public void getterMethodTest() {
        testWith("{{ object.nestedGetter.valueMethod() }}");
    }

    @Test
    public void getterFieldTest() {
        testWith("{{ object.nestedGetter.value_field }}");
    }

    @Test
    public void getterGetterTest() {
        testWith("{{ object.nestedGetter.valueGetter }}");
    }


    private class NestedTestClass {
        private static final int MAX_DEPTH = 2;

        public final int value_field = EXPECTED_VALUE;
        public final ImmutableMap<String, Integer> map_field = ImmutableMap.of("key", EXPECTED_VALUE);
        public final ImmutableList<Integer> list_field = ImmutableList.of(EXPECTED_VALUE);
        public NestedTestClass nested_field;

        private NestedTestClass() {
            this(0);
        }

        private NestedTestClass(int depth) {
            if (depth >= MAX_DEPTH) {
                return;
            }

            nested_field = new NestedTestClass(depth + 1);
            ;
        }

        public NestedTestClass getNestedGetter() {
            return nested_field;
        }

        public NestedTestClass nestedMethod() {
            return nested_field;
        }

        public int getValueGetter() {
            return value_field;
        }

        public int valueMethod() {
            return value_field;
        }

        public Map<String, Integer> getMapGetter() {
            return map_field;
        }

        public Map<String, Integer> mapMethod() {
            return map_field;
        }


        public List<Integer> getListGetter() {
            return list_field;
        }

        public List<Integer> listMethod() {
            return list_field;
        }
    }
}
