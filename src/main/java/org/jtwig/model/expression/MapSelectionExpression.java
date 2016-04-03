package org.jtwig.model.expression;

import org.jtwig.model.position.Position;

public class MapSelectionExpression extends Expression {
    private final Expression mapExpression;
    private final Expression selectValue;

    public MapSelectionExpression(Position position, Expression mapExpression, Expression selectValue) {
        super(position);
        this.mapExpression = mapExpression;
        this.selectValue = selectValue;
    }

    public Expression getMapExpression() {
        return mapExpression;
    }

    public Expression getSelectValue() {
        return selectValue;
    }

    //    @Override
//    public JtwigValue calculate(CalculateRequest request) {
//        JtwigValue map = mapExpression.calculate(request);
//        return map.getMapValue(selectValue.calculate(request));
//    }
}
