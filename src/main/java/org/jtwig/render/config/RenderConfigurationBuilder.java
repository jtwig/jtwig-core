package org.jtwig.render.config;

import org.apache.commons.lang3.builder.Builder;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.test.TestExpression;
import org.jtwig.model.tree.Node;
import org.jtwig.render.expression.calculator.ExpressionCalculator;
import org.jtwig.render.expression.calculator.operation.binary.BinaryOperator;
import org.jtwig.render.expression.calculator.operation.binary.calculators.BinaryOperationCalculator;
import org.jtwig.render.expression.calculator.operation.unary.UnaryOperator;
import org.jtwig.render.expression.calculator.operation.unary.calculators.UnaryOperationCalculator;
import org.jtwig.render.expression.test.calculator.TestExpressionCalculator;
import org.jtwig.render.listeners.StagedRenderListener;
import org.jtwig.render.node.renderer.NodeRender;
import org.jtwig.util.builder.ListBuilder;
import org.jtwig.util.builder.MapBuilder;

import java.nio.charset.Charset;

public class RenderConfigurationBuilder<B extends RenderConfigurationBuilder> implements Builder<RenderConfiguration> {
    private boolean strictMode;
    private Charset outputCharset;
    private final MapBuilder<B, Class<? extends Node>, NodeRender> nodeRenders;
    private final MapBuilder<B, Class<? extends Expression>, ExpressionCalculator> expressionCalculators;
    private final MapBuilder<B, Class<? extends BinaryOperator>, BinaryOperationCalculator> binaryExpressionCalculators;
    private final MapBuilder<B, Class<? extends UnaryOperator>, UnaryOperationCalculator> unaryExpressionCalculators;
    private final MapBuilder<B, Class<? extends TestExpression>, TestExpressionCalculator> testExpressionCalculators;
    private final ListBuilder<B, StagedRenderListener> renderListeners;

    public RenderConfigurationBuilder() {
        nodeRenders = new MapBuilder<>(self());
        this.expressionCalculators = new MapBuilder<>(self());
        this.binaryExpressionCalculators = new MapBuilder<>(self());
        this.unaryExpressionCalculators = new MapBuilder<>(self());
        this.testExpressionCalculators = new MapBuilder<>(self());
        this.renderListeners = new ListBuilder<B, StagedRenderListener>(self());
    }
    public RenderConfigurationBuilder(RenderConfiguration prototype) {
        this.strictMode = prototype.getStrictMode();
        this.outputCharset = prototype.getDefaultOutputCharset();
        this.nodeRenders = new MapBuilder<>(self(), prototype.getNodeRenders());
        this.expressionCalculators = new MapBuilder<>(self(), prototype.getExpressionCalculators());
        this.binaryExpressionCalculators = new MapBuilder<>(self(), prototype.getBinaryExpressionCalculators());
        this.unaryExpressionCalculators = new MapBuilder<>(self(), prototype.getUnaryExpressionCalculators());
        this.testExpressionCalculators = new MapBuilder<>(self(), prototype.getTestExpressionCalculators());
        this.renderListeners = new ListBuilder<>(self(), prototype.getRenderListeners());
    }

    public B withStrictMode(boolean strictMode) {
        this.strictMode = strictMode;
        return self();
    }

    public B withOutputCharset(Charset outputCharset) {
        this.outputCharset = outputCharset;
        return self();
    }

    public MapBuilder<B, Class<? extends Node>, NodeRender> nodeRenders () {
        return nodeRenders;
    }

    public MapBuilder<B, Class<? extends Expression>, ExpressionCalculator> expressionCalculators() {
        return expressionCalculators;
    }

    public MapBuilder<B, Class<? extends BinaryOperator>, BinaryOperationCalculator> binaryExpressionCalculators() {
        return binaryExpressionCalculators;
    }

    public MapBuilder<B, Class<? extends UnaryOperator>, UnaryOperationCalculator> unaryExpressionCalculators() {
        return unaryExpressionCalculators;
    }

    public MapBuilder<B, Class<? extends TestExpression>, TestExpressionCalculator> testExpressionCalculators() {
        return testExpressionCalculators;
    }

    public ListBuilder<B, StagedRenderListener> renderListeners() {
        return renderListeners;
    }

    protected B self () {
        return (B) this;
    }

    @Override
    public RenderConfiguration build() {
        return new RenderConfiguration(strictMode, outputCharset,
                nodeRenders.build(),
                expressionCalculators.build(),
                binaryExpressionCalculators.build(),
                unaryExpressionCalculators.build(),
                testExpressionCalculators.build(), renderListeners.build());
    }
}
