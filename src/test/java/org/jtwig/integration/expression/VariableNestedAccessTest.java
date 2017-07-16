package org.jtwig.integration.expression;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(Parameterized.class)
public class VariableNestedAccessTest {
    private static final int EXPECTED_VALUE = 32;

    /**
     * List of named Tests. Each Test lists a set of Terms that, when interpreted in the Context of the prepared Template,
     * are expected to produce an instance of the supplied TermType.
     * <p>
     * For example: the Test "map" has expressions to generate a MAP, a LIST or an OBJECT by accessing map.
     */
    private static LeftTerms leftTerms = new LeftTerms()
            .add("map", new AccessTerms()
                    .add(TermType.MAP, "map['map']")
                    .add(TermType.LIST, "map['list']")
                    .add(TermType.OBJECT, "map['object']")
            )
            .add("mapWithParenthesis", new AccessTerms()
                    .add(TermType.MAP, "(map['map'])")
                    .add(TermType.LIST, "(map['list'])")
                    .add(TermType.OBJECT, "(map['object'])")
            )
            .add("mapDot", new AccessTerms()
                    .add(TermType.MAP, "map.map")
                    .add(TermType.LIST, "map.list")
                    .add(TermType.OBJECT, "map.object")
            )
            .add("mapGet", new AccessTerms()
                    .add(TermType.MAP, "map.get('map')")
                    .add(TermType.LIST, "map.get('list')")
                    .add(TermType.OBJECT, "map.get('object')")
            )
            .add("list", new AccessTerms()
                    .add(TermType.MAP, "list[0]")
                    .add(TermType.LIST, "list[1]")
                    .add(TermType.OBJECT, "list[2]")
            )
            .add("listWithParenthesis", new AccessTerms()
                    .add(TermType.MAP, "(list[0])")
                    .add(TermType.LIST, "(list[1])")
                    .add(TermType.OBJECT, "(list[2])")
            )
            .add("objectMethod", new AccessTerms()
                    .add(TermType.MAP, "object.mapMethod()")
                    .add(TermType.LIST, "object.listMethod()")
                    .add(TermType.OBJECT, "object.nestedMethod()")
            )
            .add("objectField", new AccessTerms()
                    .add(TermType.MAP, "object.map_field")
                    .add(TermType.LIST, "object.list_field")
                    .add(TermType.OBJECT, "object.nested_field")
            )
            .add("objectGetter", new AccessTerms()
                    .add(TermType.MAP, "object.mapGetter")
                    .add(TermType.LIST, "object.listGetter")
                    .add(TermType.OBJECT, "object.nestedGetter")
            );

    /**
     * List of named Tests. Each Test lists a Terms that is added to each term from the leftTerms list. For each
     * leftTerm the correct expression for the required TermType is choosen and prepended to the rightTerm.
     * <p>
     * For example: the leftTerm "map" combined with the rightTerm "map" produces the "mapMapTest", which evaluates
     * the expression map['map']['key']
     * <p>
     * the leftTerm "list" combined with the rightTerm "map" produces the "listMapTest", which evaluates
     * the expression list[0]['key']
     * <p>
     * the leftTerm "map" combined with the rightTerm "list" produces the "naoListTest", which evaluates
     * the expression map['list'][0]
     * <p>
     * Additionally each Test is ran pnce without and once with an arithmetic expression following
     * (" + 1 - 1" is appended)
     */
    private static RightTerms rightTerms = new RightTerms()
            .add("map", TermType.MAP, "['key']")
            .add("mapDot", TermType.MAP, ".key")
            .add("list", TermType.LIST, "[0]")
            .add("objectMethod", TermType.OBJECT, ".valueMethod()")
            .add("objectField", TermType.OBJECT, ".value_field")
            .add("objectGetter", TermType.OBJECT, ".valueGetter");

    private NestedTestClass testobject;
    private ImmutableMap<String, Object> testmap;
    private ImmutableList<Object> teslist;
    private TestCase testCase;

    public VariableNestedAccessTest(TestCase testCase) {
        this.testCase = testCase;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Iterable<TestCase> data() {
        ArrayList<TestCase> testCases = new ArrayList<>();

        for (Map.Entry<String, AccessTerms> leftEntry : leftTerms.entrySet()) {
            for (Map.Entry<String, AccessTerm> rightEntry : rightTerms.entrySet()) {
                testCases.add(new TestCase(
                        leftEntry.getValue(),
                        rightEntry.getValue(),
                        leftEntry.getKey(),
                        rightEntry.getKey()
                ));

                testCases.add(new ArithmericTestCase(
                        leftEntry.getValue(),
                        rightEntry.getValue(),
                        leftEntry.getKey(),
                        rightEntry.getKey()
                ));

            }
        }

        return testCases;
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
                ImmutableMap.of(
                        "key", EXPECTED_VALUE
                ),
                ImmutableList.of(
                        EXPECTED_VALUE
                ),
                new NestedTestClass()
        );
    }

    @Test
    public void test() {
        String template = "{{ " + testCase.getExpression() + " }}";
        System.out.println(testCase+":");
        System.out.println(template);

        JtwigModel model = JtwigModel.newModel()
                .with("map", testmap)
                .with("list", teslist)
                .with("object", testobject);

        String result = JtwigTemplate.inlineTemplate(template).render(model);

        assertThat(result, is(String.valueOf(EXPECTED_VALUE)));
    }

    private enum TermType {
        MAP,
        LIST,
        OBJECT
    }

    private static class TestCase {
        private AccessTerms leftAccessTerms;
        private AccessTerm rightAccessTerm;
        private String testName = "";

        private TestCase(AccessTerms leftAccessTerms, AccessTerm rightAccessTerm, String leftTestName, String rightTestName) {
            this.leftAccessTerms = leftAccessTerms;
            this.rightAccessTerm = rightAccessTerm;
            setTestName(leftTestName, rightTestName);
        }

        private static String uppercaseFirstchar(String s) {
            return s.substring(0, 1).toUpperCase() + s.substring(1);
        }

        public String getExpression() {
            return leftAccessTerms.get(rightAccessTerm.termType) + rightAccessTerm.term;
        }

        public TestCase setTestName(String leftTestName, String rightTestName) {
            setTestName(leftTestName + uppercaseFirstchar(rightTestName));
            return this;
        }

        public String getTestName() {
            return testName;
        }

        public TestCase setTestName(String testName) {
            this.testName = testName;
            return this;
        }

        @Override
        public String toString() {
            return getTestName() + "Test";
        }
    }

    private static class ArithmericTestCase extends TestCase {
        public ArithmericTestCase(AccessTerms leftAccessTerms, AccessTerm rightAccessTerm, String leftTestName, String rightTestName) {
            super(leftAccessTerms, rightAccessTerm, leftTestName, rightTestName);
        }

        @Override
        public String getExpression() {
            return super.getExpression() + " * 6 / (3 + 3) + 4 - (1 + 3)";
        }

        @Override
        public String getTestName() {
            return super.getTestName() + "WithArithmetic";
        }
    }

    private static class AccessTerm {
        private final TermType termType;
        private final String term;

        public AccessTerm(TermType termType, String term) {
            this.termType = termType;
            this.term = term;
        }
    }

    private static class AccessTerms extends HashMap<TermType, String> {
        public AccessTerms add(TermType termType, String term) {
            put(termType, term);
            return this;
        }
    }

    private static class LeftTerms extends HashMap<String, AccessTerms> {
        public LeftTerms add(String testName, AccessTerms accessTerms) {
            put(testName, accessTerms);
            return this;
        }
    }

    private static class RightTerms extends HashMap<String, AccessTerm> {
        public RightTerms add(String testName, AccessTerm accessTerm) {
            put(testName, accessTerm);
            return this;
        }

        public RightTerms add(String testName, TermType requiredLeftTermType, String term) {
            add(testName, new AccessTerm(requiredLeftTermType, term));
            return this;
        }
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
