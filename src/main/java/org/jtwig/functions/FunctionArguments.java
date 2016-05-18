package org.jtwig.functions;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import org.jtwig.model.expression.Expression;
import org.jtwig.reflection.model.Value;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FunctionArguments {
    public static FunctionArguments empty() {
        return new FunctionArguments(EmptyExpressionResolver.instance(), Collections.<Expression>emptyList());
    }

    private final Function<Expression, Object> expressionResolver;
    private final List<Expression> expressions;
    private final Value[] values;

    public FunctionArguments(Function<Expression, Object> expressionResolver, List<Expression> expressions) {
        this.expressionResolver = expressionResolver;
        this.expressions = expressions;
        this.values = new Value[expressions.size()];
    }

    public Expression getExpression (int index) {
        return expressions.get(index);
    }


    public Object getValue (int index) {
        if (values[index] == null) {
            values[index] = new Value(expressionResolver.apply(expressions.get(index)));
        }

        return values[index].getValue();
    }

    public Object[] getRemainingArguments(int start) {
        if (expressions.size() <= 0) return new Object[0];
        else {
            Object[] objects = new Object[expressions.size() - start];
            for (int i = start; i < expressions.size(); i++) {
                objects[i-start] = getValue(i);
            }
            return objects;
        }
    }

    public int size () {
        return expressions.size();
    }

    public List<Object> getValues() {
        List<Object> values = new ArrayList<>();
        for (int i = 0; i < expressions.size(); i++) {
            values.add(getValue(i));
        }
        return values;
    }

    public List<Expression> getExpressions() {
        return ImmutableList.copyOf(expressions);
    }
}
