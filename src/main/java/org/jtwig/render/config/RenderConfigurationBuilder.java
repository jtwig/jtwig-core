package org.jtwig.render.config;

import org.apache.commons.lang3.builder.Builder;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.test.TestExpression;
import org.jtwig.model.tree.Node;
import org.jtwig.render.context.model.EscapeMode;
import org.jtwig.render.expression.calculator.ExpressionCalculator;
import org.jtwig.render.expression.calculator.operation.binary.BinaryOperator;
import org.jtwig.render.expression.calculator.operation.binary.calculators.BinaryOperationCalculator;
import org.jtwig.render.expression.calculator.operation.unary.UnaryOperator;
import org.jtwig.render.expression.calculator.operation.unary.calculators.UnaryOperationCalculator;
import org.jtwig.render.expression.test.calculator.TestExpressionCalculator;
import org.jtwig.render.node.renderer.NodeRender;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class RenderConfigurationBuilder<B extends RenderConfigurationBuilder> implements Builder<RenderConfiguration> {
    private boolean strictMode;
    private Charset outputCharset;
    private EscapeMode initialEscapeMode;
    private Map<Class<? extends Node>, NodeRender> renders = new HashMap<>();
    private Map<Class<? extends Expression>, ExpressionCalculator> calculators = new HashMap<>();
    private Map<Class<? extends BinaryOperator>, BinaryOperationCalculator> binaryCalculators = new HashMap<>();
    private Map<Class<? extends UnaryOperator>, UnaryOperationCalculator> unaryCalculators = new HashMap<>();
    private Map<Class<? extends TestExpression>, TestExpressionCalculator> testExpressionCalculators = new HashMap<>();

    public RenderConfigurationBuilder() {}
    public RenderConfigurationBuilder(RenderConfiguration prototype) {
        this
                .withInitialEscapeMode(prototype.getInitialEscapeMode())
                .withOutputCharset(prototype.getDefaultOutputCharset())
                .withStrictMode(prototype.getStrictMode())
                .withRenders(prototype.getRenders())
                .withCalculators(prototype.getCalculators())
                .withBinaryCalculators(prototype.getBinaryCalculators())
                .withUnaryCalculators(prototype.getUnaryCalculators())
                .withTestExpressionCalculators(prototype.getTestExpressionCalculators())
        ;
    }

    public B withStrictMode(boolean strictMode) {
        this.strictMode = strictMode;
        return self();
    }

    public B withOutputCharset(Charset outputCharset) {
        this.outputCharset = outputCharset;
        return self();
    }

    public B withInitialEscapeMode(EscapeMode initialEscapeMode) {
        this.initialEscapeMode = initialEscapeMode;
        return self();
    }

    public B withRenders(Map<Class<? extends Node>, NodeRender> renders) {
        this.renders.putAll(renders);
        return self();
    }

    public B withRender(Class<? extends Node> type, NodeRender render) {
        this.renders.put(type, render);
        return self();
    }

    public B withCalculators(Map<Class<? extends Expression>, ExpressionCalculator> calculators) {
        this.calculators.putAll(calculators);
        return self();
    }

    public B withCalculator(Class<? extends Expression> type, ExpressionCalculator calculator) {
        this.calculators.put(type, calculator);
        return self();
    }

    public B withBinaryCalculators(Map<Class<? extends BinaryOperator>, BinaryOperationCalculator> calculators) {
        this.binaryCalculators.putAll(calculators);
        return self();
    }

    public B withBinaryCalculator(Class<? extends BinaryOperator> type, BinaryOperationCalculator calculator) {
        this.binaryCalculators.put(type, calculator);
        return self();
    }

    public B withUnaryCalculators(Map<Class<? extends UnaryOperator>, UnaryOperationCalculator> calculators) {
        this.unaryCalculators.putAll(calculators);
        return self();
    }

    public B withUnaryCalculator(Class<? extends UnaryOperator> type, UnaryOperationCalculator calculator) {
        this.unaryCalculators.put(type, calculator);
        return self();
    }

    public B withTestExpressionCalculators(Map<Class<? extends TestExpression>, TestExpressionCalculator> calculators) {
        this.testExpressionCalculators.putAll(calculators);
        return self();
    }

    public B withTestExpressionCalculator(Class<? extends TestExpression> type, TestExpressionCalculator calculator) {
        this.testExpressionCalculators.put(type, calculator);
        return self();
    }

    protected B self () {
        return (B) this;
    }

    @Override
    public RenderConfiguration build() {
        return new RenderConfiguration(strictMode, outputCharset, initialEscapeMode, renders, calculators, binaryCalculators, unaryCalculators, testExpressionCalculators);
    }
}
