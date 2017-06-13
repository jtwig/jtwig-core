package org.jtwig.render.config;

import com.google.common.collect.ImmutableMap;
import org.jtwig.macro.render.ImportRender;
import org.jtwig.model.expression.*;
import org.jtwig.model.expression.test.*;
import org.jtwig.model.tree.*;
import org.jtwig.render.expression.calculator.*;
import org.jtwig.render.expression.calculator.operation.binary.BinaryOperator;
import org.jtwig.render.expression.calculator.operation.binary.calculators.*;
import org.jtwig.render.expression.calculator.operation.binary.calculators.selection.SelectionErrorMessageGenerator;
import org.jtwig.render.expression.calculator.operation.binary.calculators.selection.SelectionOperationCalculator;
import org.jtwig.render.expression.calculator.operation.binary.impl.*;
import org.jtwig.render.expression.calculator.operation.unary.UnaryOperator;
import org.jtwig.render.expression.calculator.operation.unary.calculators.NegativeOperationCalculator;
import org.jtwig.render.expression.calculator.operation.unary.calculators.NotOperationCalculator;
import org.jtwig.render.expression.calculator.operation.unary.calculators.UnaryOperationCalculator;
import org.jtwig.render.expression.calculator.operation.unary.impl.NegativeUnaryOperator;
import org.jtwig.render.expression.calculator.operation.unary.impl.NotUnaryOperator;
import org.jtwig.render.expression.test.calculator.*;
import org.jtwig.render.listeners.StagedRenderListener;
import org.jtwig.render.node.renderer.*;

import java.nio.charset.Charset;
import java.util.ArrayList;

public class DefaultRenderConfiguration extends RenderConfiguration {
    public DefaultRenderConfiguration() {
        super(false,
                Charset.defaultCharset(),
                ImmutableMap.<Class<? extends Node>, NodeRender>builder()
                        .put(AutoEscapeNode.class, new AutoEscapeNodeRender())
                        .put(ContentEscapeNode.class, new ContentEscapeNodeRender())
                        .put(BlockNode.class, new BlockNodeRender())
                        .put(CompositeNode.class, new CompositeNodeRender())
                        .put(DoNode.class, new DoNodeRender())
                        .put(ExtendsNode.class, new ExtendsNodeRender())
                        .put(FilterNode.class, new FilterNodeRender())
                        .put(FlushNode.class, new FlushNodeRender())
                        .put(ForLoopNode.class, new ForLoopNodeRender())
                        .put(IfNode.class, new IfNodeRender())
                        .put(ImportSelfNode.class, new ImportSelfNodeRender(ImportRender.instance()))
                        .put(ImportNode.class, new ImportNodeRender(ImportRender.instance()))
                        .put(IncludeNode.class, new IncludeNodeRender())
                        .put(MacroNode.class, new MacroNodeRender())
                        .put(OutputNode.class, new OutputNodeRender())
                        .put(OverrideBlockNode.class, new OverrideBlockNodeRender())
                        .put(SetNode.class, new SetNodeRender())
                        .put(TextNode.class, new TextNodeRender())
                        .put(VerbatimNode.class, new VerbatimNodeRender())
                        .put(EmbedNode.class, new EmbedNodeRender())
                        .build(),

                ImmutableMap.<Class<? extends Expression>, ExpressionCalculator>builder()
                        .put(ConstantExpression.class, new ConstantExpressionCalculator())
                        .put(VariableExpression.class, new VariableExpressionCalculator())
                        .put(BinaryOperationExpression.class, new BinaryOperationExpressionCalculator())
                        .put(FunctionExpression.class, new FunctionExpressionCalculator(new FunctionArgumentsFactory()))
                        .put(MapExpression.class, new MapExpressionCalculator())
                        .put(ComprehensionListExpression.class, new ComprehensionListExpressionCalculator())
                        .put(EnumeratedListExpression.class, new EnumeratedListExpressionCalculator())
                        .put(MapSelectionExpression.class, new MapSelectionExpressionCalculator())
                        .put(UnaryOperationExpression.class, new UnaryOperationExpressionCalculator())
                        .put(TernaryOperationExpression.class, new TernaryExpressionCalculator())
                        .put(TestOperationExpression.class, new TestOperationExpressionCalculator())
                        .build(),

                ImmutableMap.<Class<? extends BinaryOperator>, BinaryOperationCalculator>builder()
                        .put(MatchesOperator.class, new SimpleOperationCalculator(new MatchesOperationCalculator()))
                        .put(ConcatOperator.class, new SimpleOperationCalculator(new ConcatOperationCalculator()))
                        .put(CompositionOperator.class, new CompositionOperationCalculator())
                        .put(SelectionOperator.class, new SelectionOperationCalculator(new SelectionErrorMessageGenerator()))
                        .put(InOperator.class, new SimpleOperationCalculator(new InOperationCalculator()))

                        .put(SumOperator.class, new SimpleOperationCalculator(new MathOperationCalculator(new SumOperationCalculator())))
                        .put(SubtractOperator.class, new SimpleOperationCalculator(new MathOperationCalculator(new SubtractOperationCalculator())))
                        .put(DivideOperator.class, new SimpleOperationCalculator(new MathOperationCalculator(new DivideOperationCalculator())))
                        .put(MultiplyOperator.class, new SimpleOperationCalculator(new MathOperationCalculator(new MultiplyOperationCalculator())))
                        .put(IntDivideOperator.class, new SimpleOperationCalculator(new MathOperationCalculator(new IntegerDivideOperationCalculator())))
                        .put(IntMultiplyOperator.class, new SimpleOperationCalculator(new MathOperationCalculator(new IntegerMultiplyOperationCalculator())))
                        .put(ModOperator.class, new SimpleOperationCalculator(new MathOperationCalculator(new ModOperationCalculator())))

                        .put(EquivalentOperator.class, new SimpleOperationCalculator(new EquivalentOperationCalculator()))
                        .put(DifferentOperator.class, new SimpleOperationCalculator(new DifferentOperationCalculator()))
                        .put(LessOperator.class, new SimpleOperationCalculator(new LessOperationCalculator()))
                        .put(LessOrEqualOperator.class, new SimpleOperationCalculator(new LessOrEqualOperationCalculator()))
                        .put(GreaterOperator.class, new SimpleOperationCalculator(new GreaterOperationCalculator()))
                        .put(GreaterOrEqualOperator.class, new SimpleOperationCalculator(new GreaterOrEqualOperationCalculator()))

                        .put(AndOperator.class, new SimpleOperationCalculator(new BooleanOperationCalculator(new AndOperationCalculator())))
                        .put(OrOperator.class, new SimpleOperationCalculator(new BooleanOperationCalculator(new OrOperationCalculator())))
                        .build(),

                ImmutableMap.<Class<? extends UnaryOperator>, UnaryOperationCalculator>builder()
                        .put(NegativeUnaryOperator.class, new NegativeOperationCalculator())
                        .put(NotUnaryOperator.class, new NotOperationCalculator())
                        .build(),

                ImmutableMap.<Class<? extends TestExpression>, TestExpressionCalculator>builder()
                        .put(NotTestExpression.class, new NotTestExpressionCalculator())
                        .put(DefinedTestExpression.class, new DefinedTestExpressionCalculator())
                        .put(IsFunctionTestExpression.class, new IsFunctionTestExpressionCalculator())
                        .put(DivisibleByTestExpression.class, new DivisibleByTestExpressionCalculator())
                        .put(NullTestExpression.class, new NullTestExpressionCalculator())
                        .put(SameAsTestExpression.class, new SameAsTestExpressionCalculator())
                        .put(FunctionTestExpression.class, new FunctionTestExpressionCalculator())
                        .build(),
                new ArrayList<StagedRenderListener>());
    }


}
