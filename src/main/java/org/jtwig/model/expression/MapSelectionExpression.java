package org.jtwig.model.expression;

import org.jtwig.context.RenderContext;
import org.jtwig.model.position.Position;
import org.jtwig.value.JtwigValue;

public class MapSelectionExpression extends Expression {
    private final Expression mapExpression;
    private final Expression selectValue;

    public MapSelectionExpression(Position position, Expression mapExpression, Expression selectValue) {
        super(position);
        this.mapExpression = mapExpression;
        this.selectValue = selectValue;
    }

    @Override
    public JtwigValue calculate(RenderContext context) {
        JtwigValue map = mapExpression.calculate(context);
        return map.getMapValue(selectValue.calculate(context));
    }
}
