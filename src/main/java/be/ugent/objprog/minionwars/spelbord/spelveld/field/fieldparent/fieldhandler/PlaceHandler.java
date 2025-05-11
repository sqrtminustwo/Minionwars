package be.ugent.objprog.minionwars.spelbord.spelveld.field.fieldparent.fieldhandler;

import be.ugent.objprog.minionwars.spelbord.spelveld.SpelveldModel;
import be.ugent.objprog.minionwars.spelbord.spelveld.field.fieldparent.Field;

public class PlaceHandler implements FieldHandler {
    public boolean canHandle(SpelveldModel model, Field field) {
        return model.isPlacing() && field.isWalkable() && field.isHomebaseOfCurrentPlayer() && !field.hasMinion();
    }
    public void executeHandle(SpelveldModel model, Field field) {
        field.setPlacedMinion(model.getSelectedMenuMinion());
        model.placedSelectedMinion();
    }
    public boolean canChangeVisual(SpelveldModel model, Field field) { return false; }
    public void changeVisual(SpelveldModel model, Field field) {}
}