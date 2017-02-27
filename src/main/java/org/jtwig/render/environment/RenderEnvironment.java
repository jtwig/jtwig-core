package org.jtwig.render.environment;

import org.jtwig.render.RenderResourceService;
import org.jtwig.render.expression.CalculateExpressionService;
import org.jtwig.render.expression.calculator.operation.binary.BinaryOperationService;
import org.jtwig.render.expression.calculator.operation.unary.UnaryOperationService;
import org.jtwig.render.expression.test.CalculateTestExpressionService;
import org.jtwig.render.listeners.RenderListenerRegistry;
import org.jtwig.render.node.RenderNodeService;

import java.nio.charset.Charset;

public class RenderEnvironment {
    private final boolean strictMode;
    private final Charset defaultOutputCharset;
    private final RenderResourceService renderResourceService;
    private final RenderNodeService renderNodeService;
    private final CalculateExpressionService calculateExpressionService;
    private final BinaryOperationService binaryOperationService;
    private final UnaryOperationService unaryOperationService;
    private final CalculateTestExpressionService calculateTestExpressionService;
    private final RenderListenerRegistry renderListeners;

    public RenderEnvironment(boolean strictMode, Charset defaultOutputCharset, RenderResourceService renderResourceService, RenderNodeService renderNodeService, CalculateExpressionService calculateExpressionService, BinaryOperationService binaryOperationService, UnaryOperationService unaryOperationService, CalculateTestExpressionService calculateTestExpressionService, RenderListenerRegistry renderListeners) {
        this.strictMode = strictMode;
        this.defaultOutputCharset = defaultOutputCharset;
        this.renderResourceService = renderResourceService;
        this.renderNodeService = renderNodeService;
        this.calculateExpressionService = calculateExpressionService;
        this.binaryOperationService = binaryOperationService;
        this.unaryOperationService = unaryOperationService;
        this.calculateTestExpressionService = calculateTestExpressionService;
        this.renderListeners = renderListeners;
    }

    public boolean getStrictMode() {
        return strictMode;
    }

    public Charset getDefaultOutputCharset() {
        return defaultOutputCharset;
    }

    public RenderResourceService getRenderResourceService() {
        return renderResourceService;
    }

    public RenderNodeService getRenderNodeService() {
        return renderNodeService;
    }

    public CalculateExpressionService getCalculateExpressionService() {
        return calculateExpressionService;
    }

    public BinaryOperationService getBinaryOperationService() {
        return binaryOperationService;
    }

    public UnaryOperationService getUnaryOperationService() {
        return unaryOperationService;
    }

    public CalculateTestExpressionService getCalculateTestExpressionService() {
        return calculateTestExpressionService;
    }

    public RenderListenerRegistry getRenderListeners() {
        return renderListeners;
    }
}
