package org.jtwig.render.node.renderer;

import org.jtwig.model.tree.CompositeNode;
import org.jtwig.model.tree.Node;
import org.jtwig.render.RenderRequest;
import org.jtwig.render.node.RenderNodeService;
import org.jtwig.renderable.Renderable;
import org.jtwig.renderable.impl.CompositeRenderable;

import java.util.ArrayList;
import java.util.Collection;

public class CompositeNodeRender implements NodeRender<CompositeNode> {
    @Override
    public Renderable render(RenderRequest request, CompositeNode list) {
        RenderNodeService renderNodeService = request.getEnvironment().getRenderEnvironment().getRenderNodeService();
        Collection<Node> listNodes = list.getNodes();
        Collection<Renderable> renderableCollection = new ArrayList<>(listNodes.size());

        for (Node node : listNodes) {
            renderableCollection.add(renderNodeService.render(request, node));
        }

        return new CompositeRenderable(renderableCollection);
    }
}
