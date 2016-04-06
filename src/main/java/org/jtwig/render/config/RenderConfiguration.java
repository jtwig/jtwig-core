package org.jtwig.render.config;

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
import java.util.Map;

public class RenderConfiguration {
    private final boolean strictMode;
    private final Charset defaultOutputCharset;
    private final EscapeMode initialEscapeMode;
    private final Map<Class<? extends Node>, NodeRender> renders;
    private final Map<Class<? extends Expression>, ExpressionCalculator> calculators;
    private final Map<Class<? extends BinaryOperator>, BinaryOperationCalculator> binaryCalculators;
    private final Map<Class<? extends UnaryOperator>, UnaryOperationCalculator> unaryCalculators;
    private final Map<Class<? extends TestExpression>, TestExpressionCalculator> testExpressionCalculators;

    public RenderConfiguration(boolean strictMode, Charset defaultOutputCharset, EscapeMode initialEscapeMode, Map<Class<? extends Node>, NodeRender> renders, Map<Class<? extends Expression>, ExpressionCalculator> calculators, Map<Class<? extends BinaryOperator>, BinaryOperationCalculator> binaryCalculators, Map<Class<? extends UnaryOperator>, UnaryOperationCalculator> unaryCalculators, Map<Class<? extends TestExpression>, TestExpressionCalculator> testExpressionCalculators) {
        this.strictMode = strictMode;
        this.defaultOutputCharset = defaultOutputCharset;
        this.initialEscapeMode = initialEscapeMode;
        this.renders = renders;
        this.calculators = calculators;
        this.binaryCalculators = binaryCalculators;
        this.unaryCalculators = unaryCalculators;
        this.testExpressionCalculators = testExpressionCalculators;
    }

    public boolean getStrictMode() {
        return strictMode;
    }

    public Charset getDefaultOutputCharset() {
        return defaultOutputCharset;
    }

    public EscapeMode getInitialEscapeMode() {
        return initialEscapeMode;
    }

    public Map<Class<? extends Node>, NodeRender> getNodeRenders() {
        return renders;
    }

    public Map<Class<? extends Expression>, ExpressionCalculator> getExpressionCalculators() {
        return calculators;
    }

    public Map<Class<? extends BinaryOperator>, BinaryOperationCalculator> getBinaryExpressionCalculators() {
        return binaryCalculators;
    }

    public Map<Class<? extends UnaryOperator>, UnaryOperationCalculator> getUnaryExpressionCalculators() {
        return unaryCalculators;
    }

    public Map<Class<? extends TestExpression>, TestExpressionCalculator> getTestExpressionCalculators() {
        return testExpressionCalculators;
    }
}
