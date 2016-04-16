package org.jtwig.render.config;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.test.TestExpression;
import org.jtwig.model.tree.Node;
import org.jtwig.render.escape.EscapeEngine;
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
    private final EscapeEngine initialEscapeEngine;
    private final String defaultEscapeEngineName;
    private final Map<String, EscapeEngine> escapeEngines;
    private final Map<Class<? extends Node>, NodeRender> renders;
    private final Map<Class<? extends Expression>, ExpressionCalculator> calculators;
    private final Map<Class<? extends BinaryOperator>, BinaryOperationCalculator> binaryCalculators;
    private final Map<Class<? extends UnaryOperator>, UnaryOperationCalculator> unaryCalculators;
    private final Map<Class<? extends TestExpression>, TestExpressionCalculator> testExpressionCalculators;

    public RenderConfiguration(boolean strictMode, Charset defaultOutputCharset, EscapeEngine initialEscapeEngine, String defaultEscapeEngineName, Map<String, EscapeEngine> escapeEngines, Map<Class<? extends Node>, NodeRender> renders, Map<Class<? extends Expression>, ExpressionCalculator> calculators, Map<Class<? extends BinaryOperator>, BinaryOperationCalculator> binaryCalculators, Map<Class<? extends UnaryOperator>, UnaryOperationCalculator> unaryCalculators, Map<Class<? extends TestExpression>, TestExpressionCalculator> testExpressionCalculators) {
        this.strictMode = strictMode;
        this.defaultOutputCharset = defaultOutputCharset;
        this.initialEscapeEngine = initialEscapeEngine;
        this.defaultEscapeEngineName = defaultEscapeEngineName;
        this.escapeEngines = escapeEngines;
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

    public EscapeEngine getInitialEscapeEngine() {
        return initialEscapeEngine;
    }

    public String getDefaultEscapeEngineName() {
        return defaultEscapeEngineName;
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

    public Map<String, EscapeEngine> getEscapeEngines() {
        return escapeEngines;
    }
}
