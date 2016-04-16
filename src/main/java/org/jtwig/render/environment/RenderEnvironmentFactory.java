package org.jtwig.render.environment;

import org.jtwig.render.RenderResourceService;
import org.jtwig.render.config.RenderConfiguration;
import org.jtwig.render.escape.EscapeEngineSelector;
import org.jtwig.render.expression.CalculateExpressionService;
import org.jtwig.render.expression.ExpressionCalculatorSelector;
import org.jtwig.render.expression.calculator.operation.binary.BinaryOperationCalculatorSelector;
import org.jtwig.render.expression.calculator.operation.binary.BinaryOperationService;
import org.jtwig.render.expression.calculator.operation.unary.UnaryOperationCalculatorSelector;
import org.jtwig.render.expression.calculator.operation.unary.UnaryOperationService;
import org.jtwig.render.expression.test.CalculateTestExpressionService;
import org.jtwig.render.expression.test.TestExpressionCalculatorSelector;
import org.jtwig.render.node.NodeRenderSelector;
import org.jtwig.render.node.RenderNodeService;

public class RenderEnvironmentFactory {
    public RenderEnvironment create (RenderConfiguration renderConfiguration) {
        NodeRenderSelector nodeRenderSelector = new NodeRenderSelector(renderConfiguration.getNodeRenders());
        RenderNodeService renderNodeService = new RenderNodeService(nodeRenderSelector);
        RenderResourceService renderResourceService = new RenderResourceService();
        ExpressionCalculatorSelector calculatorSelector = new ExpressionCalculatorSelector(renderConfiguration.getExpressionCalculators());
        CalculateExpressionService calculateExpressionService = new CalculateExpressionService(calculatorSelector);
        BinaryOperationService binaryOperationService = new BinaryOperationService(new BinaryOperationCalculatorSelector(renderConfiguration.getBinaryExpressionCalculators()));
        UnaryOperationService unaryOperationService = new UnaryOperationService(new UnaryOperationCalculatorSelector(renderConfiguration.getUnaryExpressionCalculators()));
        CalculateTestExpressionService calculateTestExpressionService = new CalculateTestExpressionService(new TestExpressionCalculatorSelector(renderConfiguration.getTestExpressionCalculators()));
        EscapeEngineSelector escapeEngineSelector = EscapeEngineSelector.newInstance(renderConfiguration.getEscapeEngines());
        return new RenderEnvironment(renderConfiguration.getStrictMode(), renderConfiguration.getDefaultOutputCharset(),
                renderConfiguration.getInitialEscapeEngine(),
                renderConfiguration.getDefaultEscapeEngineName(),
                escapeEngineSelector, renderResourceService, renderNodeService,
                calculateExpressionService, binaryOperationService, unaryOperationService,
                calculateTestExpressionService);
    }
}
