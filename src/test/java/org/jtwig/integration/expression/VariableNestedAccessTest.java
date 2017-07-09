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
    public void mapWithParenthesisMapTest() {
        testWith("{{ (map['map'])['key'] }}");
    }

    @Test
    public void mapWithParenthesisMapDotTest() {
        testWith("{{ (map['map']).key }}");
    }

    @Test
    public void mapWithParenthesisListTest() {
        testWith("{{ (map['list'])[0] }}");
    }

    @Test
    public void mapWithParenthesisMethodTest() {
        testWith("{{ (map['object']).valueMethod() }}");
    }

    @Test
    public void mapWithParenthesisFieldTest() {
        testWith("{{ (map['object']).value_field }}");
    }

    @Test
    public void mapWithParenthesisGetterTest() {
        testWith("{{ (map['object']).valueGetter }}");
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
    public void listWithParenthesisMapTest() {
        testWith("{{ (list[1])['key'] }}");
    }

    @Test
    public void listWithParenthesisMapDotTest() {
        testWith("{{ (list[1]).key }}");
    }

    @Test
    public void listWithParenthesisListTest() {
        testWith("{{ (list[0])[0] }}");
    }

    @Test
    public void listWithParenthesisMethodTest() {
        testWith("{{ (list[2]).valueMethod() }}");
    }

    @Test
    public void listWithParenthesisFieldTest() {
        testWith("{{ (list[2]).value_field }}");
    }

    @Test
    public void listWithParenthesisGetterTest() {
        testWith("{{ (list[2]).valueGetter }}");
    }


    @Test
    public void mapWithGetMapTest() {
        testWith("{{ map.get('map')['key'] }}");
    }

    @Test
    public void mapWithGetMapDotTest() {
        testWith("{{ map.get('map').key }}");
    }

    @Test
    public void mapWithGetListTest() {
        testWith("{{ map.get('list')[0] }}");
    }

    @Test
    public void mapWithGetMethodTest() {
        testWith("{{ map.get('object').valueMethod() }}");
    }

    @Test
    public void mapWithGetFieldTest() {
        testWith("{{ map.get('object').value_field }}");
    }

    @Test
    public void mapWithGetGetterTest() {
        testWith("{{ map.get('object').valueGetter }}");
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


    @Test
    public void mapMapWithArithmeticTest() {
        testWith("{{ map['map']['key'] + 1 - 1 }}");
    }

    @Test
    public void mapMapDotWithArithmeticTest() {
        testWith("{{ map['map'].key + 1 - 1 }}");
    }

    @Test
    public void mapListWithArithmeticTest() {
        testWith("{{ map['list'][0] + 1 - 1 }}");
    }

    @Test
    public void mapMethodWithArithmeticTest() {
        testWith("{{ map['object'].valueMethod() + 1 - 1 }}");
    }

    @Test
    public void mapFieldWithArithmeticTest() {
        testWith("{{ map['object'].value_field + 1 - 1 }}");
    }

    @Test
    public void mapGetterWithArithmeticTest() {
        testWith("{{ map['object'].valueGetter + 1 - 1 }}");
    }


    @Test
    public void mapWithParenthesisMapWithArithmeticTest() {
        testWith("{{ (map['map'])['key'] + 1 - 1 }}");
    }

    @Test
    public void mapWithParenthesisMapDWithArithmeticotTest() {
        testWith("{{ (map['map']).key + 1 - 1 }}");
    }

    @Test
    public void mapWithParenthesisListWithArithmeticTest() {
        testWith("{{ (map['list'])[0] + 1 - 1 }}");
    }

    @Test
    public void mapWithParenthesisMethodWithArithmeticTest() {
        testWith("{{ (map['object']).valueMethod() + 1 - 1 }}");
    }

    @Test
    public void mapWithParenthesisFieldWithArithmeticTest() {
        testWith("{{ (map['object']).value_field + 1 - 1 }}");
    }

    @Test
    public void mapWithParenthesisGetterWithArithmeticTest() {
        testWith("{{ (map['object']).valueGetter + 1 - 1 }}");
    }


    @Test
    public void mapDotMapWithArithmeticTest() {
        testWith("{{ map.map['key'] + 1 - 1 }}");
    }

    @Test
    public void mapDotMapDotWithArithmeticTest() {
        testWith("{{ map.map.key + 1 - 1 }}");
    }

    @Test
    public void mapDotListWithArithmeticTest() {
        testWith("{{ map.list[0] + 1 - 1 }}");
    }

    @Test
    public void mapDotMethodWithArithmeticTest() {
        testWith("{{ map.object.valueMethod() + 1 - 1 }}");
    }

    @Test
    public void mapDotFieldWithArithmeticTest() {
        testWith("{{ map.object.value_field + 1 - 1 }}");
    }

    @Test
    public void mapDotGetterWithArithmeticTest() {
        testWith("{{ map.object.valueGetter + 1 - 1 }}");
    }


    @Test
    public void listMapWithArithmeticTest() {
        testWith("{{ list[1]['key'] + 1 - 1 }}");
    }

    @Test
    public void listMapDotWithArithmeticTest() {
        testWith("{{ list[1].key + 1 - 1 }}");
    }

    @Test
    public void listListWithArithmeticTest() {
        testWith("{{ list[0][0] + 1 - 1 }}");
    }

    @Test
    public void listMethodWithArithmeticTest() {
        testWith("{{ list[2].valueMethod() + 1 - 1 }}");
    }

    @Test
    public void listFieldWithArithmeticTest() {
        testWith("{{ list[2].value_field + 1 - 1 }}");
    }

    @Test
    public void listGetterWithArithmeticTest() {
        testWith("{{ list[2].valueGetter + 1 - 1 }}");
    }


    @Test
    public void listWithParenthesisMapWithArithmeticTest() {
        testWith("{{ (list[1])['key'] + 1 - 1 }}");
    }

    @Test
    public void listWithParenthesisWithArithmeticMapDotTest() {
        testWith("{{ (list[1]).key + 1 - 1 }}");
    }

    @Test
    public void listWithParenthesiWithArithmeticsListTest() {
        testWith("{{ (list[0])[0] + 1 - 1 }}");
    }

    @Test
    public void listWithParenthesisMethodWithArithmeticTest() {
        testWith("{{ (list[2]).valueMethod() + 1 - 1 }}");
    }

    @Test
    public void listWithParenthesisFieldWithArithmeticTest() {
        testWith("{{ (list[2]).value_field + 1 - 1 }}");
    }

    @Test
    public void listWithParenthesisGetterWithArithmeticTest() {
        testWith("{{ (list[2]).valueGetter + 1 - 1 }}");
    }


    @Test
    public void mapWithGetMapWithArithmeticTest() {
        testWith("{{ map.get('map')['key'] + 1 - 1 }}");
    }

    @Test
    public void mapWithGetMapDotWithArithmeticTest() {
        testWith("{{ map.get('map').key + 1 - 1 }}");
    }

    @Test
    public void mapWithGetListWithArithmeticTest() {
        testWith("{{ map.get('list')[0] + 1 - 1 }}");
    }

    @Test
    public void mapWithGetMethodWithArithmeticTest() {
        testWith("{{ map.get('object').valueMethod() + 1 - 1 }}");
    }

    @Test
    public void mapWithGetFieldWithArithmeticTest() {
        testWith("{{ map.get('object').value_field + 1 - 1 }}");
    }

    @Test
    public void mapWithGetGetterWithArithmeticTest() {
        testWith("{{ map.get('object').valueGetter + 1 - 1 }}");
    }


    @Test
    public void methodMapWithArithmeticTest() {
        testWith("{{ object.mapMethod()['key'] + 1 - 1 }}");
    }

    @Test
    public void methodMapDotWithArithmeticTest() {
        testWith("{{ object.mapMethod().key + 1 - 1 }}");
    }

    @Test
    public void methodListWithArithmeticTest() {
        testWith("{{ object.listMethod()[0] + 1 - 1 }}");
    }

    @Test
    public void methodMethodWithArithmeticTest() {
        testWith("{{ object.nestedMethod().valueMethod() + 1 - 1 }}");
    }

    @Test
    public void methodFieldWithArithmeticTest() {
        testWith("{{ object.nestedMethod().value_field + 1 - 1 }}");
    }

    @Test
    public void methodGetterWithArithmeticTest() {
        testWith("{{ object.nestedMethod().valueGetter + 1 - 1 }}");
    }


    @Test
    public void fieldMapWithArithmeticTest() {
        testWith("{{ object.map_field['key'] + 1 - 1 }}");
    }

    @Test
    public void fieldMapDotWithArithmeticTest() {
        testWith("{{ object.map_field.key + 1 - 1 }}");
    }

    @Test
    public void fieldListWithArithmeticTest() {
        testWith("{{ object.list_field[0] + 1 - 1 }}");
    }

    @Test
    public void fieldMethodWithArithmeticTest() {
        testWith("{{ object.nested_field.valueMethod() + 1 - 1 }}");
    }

    @Test
    public void fieldFieldWithArithmeticTest() {
        testWith("{{ object.nested_field.value_field + 1 - 1 }}");
    }

    @Test
    public void fieldGetterWithArithmeticTest() {
        testWith("{{ object.nested_field.valueGetter + 1 - 1 }}");
    }


    @Test
    public void getterMapWithArithmeticTest() {
        testWith("{{ object.mapGetter['key'] + 1 - 1 }}");
    }

    @Test
    public void getterMapDotWithArithmeticTest() {
        testWith("{{ object.mapGetter.key + 1 - 1 }}");
    }

    @Test
    public void getterListWithArithmeticTest() {
        testWith("{{ object.listGetter[0] + 1 - 1 }}");
    }

    @Test
    public void getterMethodWithArithmeticTest() {
        testWith("{{ object.nestedGetter.valueMethod() + 1 - 1 }}");
    }

    @Test
    public void getterFieldWithArithmeticTest() {
        testWith("{{ object.nestedGetter.value_field + 1 - 1 }}");
    }

    @Test
    public void getterGetterWithArithmeticTest() {
        testWith("{{ object.nestedGetter.valueGetter + 1 - 1 }}");
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
