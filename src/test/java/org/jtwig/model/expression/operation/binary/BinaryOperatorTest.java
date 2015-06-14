package org.jtwig.model.expression.operation.binary;

import org.hamcrest.Description;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.jtwig.model.expression.operation.binary.impl.*;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class BinaryOperatorTest {
    @Test
    public void binaryOperatorPrecedence() throws Exception {
        assertThat(SelectionOperator.class, precedence(is(1)));

        assertThat(MultiplyOperator.class, precedence(is(5)));
        assertThat(IntMultiplyOperator.class, precedence(is(5)));
        assertThat(DivideOperator.class, precedence(is(5)));
        assertThat(IntDivideOperator.class, precedence(is(5)));
        assertThat(ModOperator.class, precedence(is(5)));

        assertThat(SumOperator.class, precedence(is(10)));
        assertThat(SubtractOperator.class, precedence(is(10)));
        assertThat(ConcatOperator.class, precedence(is(10)));

        assertThat(LessOperator.class, precedence(is(15)));
        assertThat(LessOrEqualOperator.class, precedence(is(15)));
        assertThat(GreaterOperator.class, precedence(is(15)));
        assertThat(GreaterOrEqualOperator.class, precedence(is(15)));
        assertThat(InOperator.class, precedence(is(15)));

        assertThat(EquivalentOperator.class, precedence(is(20)));
        assertThat(DifferentOperator.class, precedence(is(20)));

        assertThat(AndOperator.class, precedence(is(25)));
        assertThat(OrOperator.class, precedence(is(25)));

        assertThat(CompositionOperator.class, precedence(is(30)));
    }

    private <T extends BinaryOperator> Matcher precedence(Matcher<? super Integer> precedenceMatcher) {
        return new FeatureMatcher<Class<T>, Integer>(precedenceMatcher, "precedence", "precedence") {
            @Override
            protected Integer featureValueOf(Class<T> actual) {
                try {
                    return actual.newInstance().precedence();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            protected boolean matchesSafely(Class<T> actual, Description mismatch) {
                mismatch.appendText(String.format("%s ", actual.getSimpleName()));
                return super.matchesSafely(actual, mismatch);
            }
        };
    }

    private int checkPrecedenceOf(Class<? extends BinaryOperator> type) {
        try {
            return type.newInstance().precedence();
        } catch (InstantiationException e) {
            return -1;
        } catch (IllegalAccessException e) {
            return -1;
        }
    }
}