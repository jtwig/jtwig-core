package org.jtwig.macro.render;

import org.jtwig.macro.ImportedMacros;
import org.jtwig.macro.Macro;
import org.jtwig.model.tree.MacroNode;
import org.jtwig.model.tree.Node;
import org.jtwig.model.tree.visitor.NodeVisitor;
import org.jtwig.render.RenderRequest;
import org.jtwig.resource.reference.ResourceReference;
import org.jtwig.value.context.ValueContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ImportRender {
    private static final ImportRender INSTANCE = new ImportRender();

    public static ImportRender instance () {
        return INSTANCE;
    }

    private ImportRender() {
    }

    public void render (RenderRequest request, ResourceReference resourceReference, String macroIdentifier) {
        Node content = request.getEnvironment().getParser().parse(request.getEnvironment(), resourceReference);

        ArrayList<MacroNode> macroNodes = new ArrayList<>();
        content.visit(new CollectMacroNodes(macroNodes));
        Map<String, Macro> macros = new HashMap<>();

        for (MacroNode macroNode : macroNodes) {
            macros.put(macroNode.getMacroName().getIdentifier(), new Macro(
                    resourceReference,
                    macroNode.getContent(),
                    macroNode.getMacroArgumentNames()
            ));
        }

        request.getRenderContext().getCurrent(ValueContext.class)
                .with(macroIdentifier, new ImportedMacros(macros));
    }

    public static class CollectMacroNodes implements NodeVisitor {
        private final Collection<MacroNode> macros;

        public CollectMacroNodes(Collection<MacroNode> macros) {
            this.macros = macros;
        }

        @Override
        public void consume(Node node) {
            if (node instanceof MacroNode) {
                macros.add((MacroNode) node);
            }
        }
    }
}
