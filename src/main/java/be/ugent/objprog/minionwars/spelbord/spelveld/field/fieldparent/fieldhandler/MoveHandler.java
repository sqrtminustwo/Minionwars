package be.ugent.objprog.minionwars.spelbord.spelveld.field.fieldparent.fieldhandler;

import be.ugent.objprog.minionwars.spelbord.menu.infopanel.minions.minionparent.Minion;
import be.ugent.objprog.minionwars.spelbord.spelveld.SpelveldModel;
import be.ugent.objprog.minionwars.spelbord.spelveld.field.fieldparent.Field;

public class MoveHandler implements FieldHandler {
    public boolean canHandle(SpelveldModel model, Field field) {
        return model.isMoveSelecting() && model.inMovementRange(field) && model.getSelectedSpelveldMinion() != null;
    }
    public void executeHandle(SpelveldModel model, Field field) {
        Minion holder = model.getSelectedSpelveldMinion();
        model.deleteMinion(holder);
        field.setPlacedMinion(holder);
        model.setSelectedField(field, holder);
        model.setMoved();
    }
    public boolean canChangeVisual(SpelveldModel model, Field field) {
        return field.getMinion() == null && model.isMoveSelecting() && model.inMovementRange(field);
    }
    public void changeVisual(SpelveldModel model, Field field) {
        field.getBlend().setMovingColor();
    }
}