package be.ugent.objprog.minionwars.spelbord.spelveld.field.fieldparent.fieldhandler;

import be.ugent.objprog.minionwars.spelbord.spelveld.SpelveldModel;
import be.ugent.objprog.minionwars.spelbord.spelveld.field.fieldparent.Blend;
import be.ugent.objprog.minionwars.spelbord.spelveld.field.fieldparent.Field;
import be.ugent.objprog.minionwars.spelbord.spelveld.field.fieldparent.fieldstate.FieldState;

public class HomebaseHandler implements FieldHandler {
    public boolean canHandle(SpelveldModel model, Field field) { return false; }
    public void executeHandle(SpelveldModel model, Field field) {}
    public boolean canChangeVisual(SpelveldModel model, Field field) {
        FieldState fieldState = field.getFieldState();
        fieldState.setHomeBase(fieldState.isWalkable() && field.isHomebaseOfCurrentPlayer());
        return true;
    }
    public void changeVisual(SpelveldModel model, Field field) {
        Blend blend = field.getBlend();
        if (field.hasMinion() && field.isHomebaseOfCurrentPlayer()) {
            blend.setTransparent();
        } else {
            blend.setNothomebaseColor();
            if (field.getFieldState().isIsHomeBase()) blend.setHomebaseColor();
        }
    }
}