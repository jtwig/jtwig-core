package org.jtwig.render.node.renderer;

import org.jtwig.model.tree.ForLoopNode;
import org.jtwig.render.RenderRequest;
import org.jtwig.render.expression.CalculateExpressionService;
import org.jtwig.render.node.RenderNodeService;
import org.jtwig.renderable.Renderable;
import org.jtwig.renderable.impl.CompositeRenderable;
import org.jtwig.util.LoopCursor;
import org.jtwig.value.WrappedCollection;
import org.jtwig.value.context.StaticVariableValueContext;
import org.jtwig.value.context.ValueContext;
import org.jtwig.value.convert.Converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class ForLoopNodeRender implements NodeRender<ForLoopNode> {
    public static final String LOOP = "loop";

    @Override
    public Renderable render(RenderRequest request, ForLoopNode node) {
        Converter<WrappedCollection> collectionConverter = request.getEnvironment().getValueEnvironment().getCollectionConverter();
        RenderNodeService renderNodeService = request.getEnvironment().getRenderEnvironment().getRenderNodeService();
        CalculateExpressionService calculateExpressionService = request.getEnvironment().getRenderEnvironment().getCalculateExpressionService();

        Object collectionJtwigValue = calculateExpressionService.calculate(request, node.getExpression());
        WrappedCollection valueCollection = collectionConverter.convert(collectionJtwigValue).or(WrappedCollection.singleton(collectionJtwigValue));
        if (valueCollection == null) valueCollection = WrappedCollection.empty();

        ValueContext parentContext = request.getRenderContext().getCurrent(ValueContext.class);
        LoopCursor loopCursor = new LoopCursor(parentContext, valueCollection);
        StaticVariableValueContext newValueContext = new StaticVariableValueContext(parentContext, LOOP, loopCursor);
        request.getRenderContext().start(ValueContext.class, newValueContext);

        Iterator<Map.Entry<String, Object>> iterator = valueCollection.iterator();
        Collection<Renderable> renderables = new ArrayList<>(valueCollection.size());

        while (iterator.hasNext()) {
            Map.Entry<String, Object> item = iterator.next();

            newValueContext.with(node.getVariableExpression().getIdentifier(), item.getValue());
            if (node.getKeyVariableExpression().isPresent()) {
                newValueContext.with(node.getKeyVariableExpression().get().getIdentifier(), item.getKey());
            }

            renderables.add(renderNodeService.render(request, node.getContent()));

            loopCursor.step();
        }

        request.getRenderContext().end(ValueContext.class);
        return new CompositeRenderable(renderables);
    }
}
