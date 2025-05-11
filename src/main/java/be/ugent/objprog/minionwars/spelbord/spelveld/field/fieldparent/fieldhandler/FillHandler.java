package be.ugent.objprog.minionwars.spelbord.spelveld.field.fieldparent.fieldhandler;

import be.ugent.objprog.minionwars.spelbord.spelveld.SpelveldModel;
import be.ugent.objprog.minionwars.spelbord.spelveld.field.fieldparent.Field;

public class FillHandler implements FieldHandler {
    public boolean canHandle(SpelveldModel model, Field field) { return false; }
    public void executeHandle(SpelveldModel model, Field field) {}
    public boolean canChangeVisual(SpelveldModel model, Field field) { return true; }
    public void changeVisual(SpelveldModel model, Field field) {
        boolean canFill = model.isStartedMainGame() || ( field.hasMinion() && field.isHomebaseOfCurrentPlayer() );
        if (canFill && field.hasMinion()) field.setFill(field.getMinion().getImage());
        else field.setFill(field.getBase());
    }
}