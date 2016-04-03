package org.jtwig.model.expression.test;

public class NotTestExpression extends TestExpression {
    public static TestExpression create(Boolean isNot, TestExpression nested) {
        if (isNot) {
            return new NotTestExpression(nested);
        }
        return nested;
    }

    private final TestExpression testExpression;

    public NotTestExpression(TestExpression testExpression) {
        this.testExpression = testExpression;
    }

    public TestExpression getTestExpression() {
        return testExpression;
    }
}
