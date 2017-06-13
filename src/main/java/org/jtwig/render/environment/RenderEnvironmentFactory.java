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
import org.jtwig.render.listeners.RenderListener;
import org.jtwig.render.listeners.RenderListenerRegistry;
import org.jtwig.render.listeners.RenderStage;
import org.jtwig.render.listeners.StagedRenderListener;
import org.jtwig.render.node.NodeRenderSelector;
import org.jtwig.render.node.RenderNodeService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        Map<RenderStage, List<RenderListener>> renderListenerMap = new HashMap<>();
        for (StagedRenderListener listener : renderConfiguration.getRenderListeners()) {
            if (!renderListenerMap.containsKey(listener.getStage())) renderListenerMap.put(listener.getStage(), new ArrayList<RenderListener>());

            renderListenerMap.get(listener.getStage()).add(listener.getListener());
        }
        RenderListenerRegistry renderListeners = new RenderListenerRegistry(renderListenerMap);

        return new RenderEnvironment(renderConfiguration.getStrictMode(),
                renderConfiguration.getDefaultOutputCharset(),
                renderResourceService, renderNodeService,
                calculateExpressionService, binaryOperationService, unaryOperationService,
                calculateTestExpressionService,
                renderListeners);
    }
}
