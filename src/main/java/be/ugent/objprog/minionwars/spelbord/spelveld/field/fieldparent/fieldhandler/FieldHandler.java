package be.ugent.objprog.minionwars.spelbord.spelveld.field.fieldparent.fieldhandler;

import be.ugent.objprog.minionwars.spelbord.spelveld.SpelveldModel;
import be.ugent.objprog.minionwars.spelbord.spelveld.field.fieldparent.Field;

public interface FieldHandler {

    default void invalidate(SpelveldModel model, Field field) {
        if (canChangeVisual(model, field)) changeVisual(model, field);
    }

    default boolean handle(SpelveldModel model, Field field) {
        if (canHandle(model, field)) {
            executeHandle(model, field);
            return true;
        }
        return false;
    }

    boolean canHandle(SpelveldModel model, Field field);
    void executeHandle(SpelveldModel model, Field field);
    boolean canChangeVisual(SpelveldModel model, Field field);
    void changeVisual(SpelveldModel model, Field field);
}