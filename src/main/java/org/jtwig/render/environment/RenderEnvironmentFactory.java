package org.jtwig.render.environment;

import org.jtwig.render.RenderResourceService;
import org.jtwig.render.config.RenderConfiguration;
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
        NodeRenderSelector nodeRenderSelector = new NodeRenderSelector(renderConfiguration.getRenders());
        RenderNodeService renderNodeService = new RenderNodeService(nodeRenderSelector);
        RenderResourceService renderResourceService = new RenderResourceService();
        ExpressionCalculatorSelector calculatorSelector = new ExpressionCalculatorSelector(renderConfiguration.getCalculators());
        CalculateExpressionService calculateExpressionService = new CalculateExpressionService(calculatorSelector);
        BinaryOperationService binaryOperationService = new BinaryOperationService(new BinaryOperationCalculatorSelector(renderConfiguration.getBinaryCalculators()));
        UnaryOperationService unaryOperationService = new UnaryOperationService(new UnaryOperationCalculatorSelector(renderConfiguration.getUnaryCalculators()));
        CalculateTestExpressionService calculateTestExpressionService = new CalculateTestExpressionService(new TestExpressionCalculatorSelector(renderConfiguration.getTestExpressionCalculators()));
        return new RenderEnvironment(renderConfiguration.getStrictMode(), renderConfiguration.getDefaultOutputCharset(), renderConfiguration.getInitialEscapeMode(),
                renderResourceService, renderNodeService, calculateExpressionService, binaryOperationService, unaryOperationService, calculateTestExpressionService);
    }
}
