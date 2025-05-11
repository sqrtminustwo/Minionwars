package be.ugent.objprog.minionwars.spelbord.spelveld.field.fieldparent.fieldhandler;

import be.ugent.objprog.minionwars.spelbord.spelveld.SpelveldModel;
import be.ugent.objprog.minionwars.spelbord.spelveld.field.fieldparent.Field;

public class DeleteHandler implements FieldHandler {
    public boolean canHandle(SpelveldModel model, Field field) { return false; }
    public void executeHandle(SpelveldModel model, Field field) {}
    public boolean canChangeVisual(SpelveldModel model, Field field) {
        return field.getFieldState().isSelected() && model.isDeleting();
    }
    public void changeVisual(SpelveldModel model, Field field) { field.setPlacedMinion(null); }
}