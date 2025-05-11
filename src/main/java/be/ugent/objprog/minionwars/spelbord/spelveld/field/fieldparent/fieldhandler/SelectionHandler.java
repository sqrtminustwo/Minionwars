package be.ugent.objprog.minionwars.spelbord.spelveld.field.fieldparent.fieldhandler;

import be.ugent.objprog.minionwars.spelbord.menu.infopanel.minions.minionparent.Minion;
import be.ugent.objprog.minionwars.spelbord.spelveld.SpelveldModel;
import be.ugent.objprog.minionwars.spelbord.spelveld.field.fieldparent.Field;
import javafx.scene.paint.Color;

public class SelectionHandler implements FieldHandler {

    public boolean canHandle(SpelveldModel model, Field field) {
        if (model.isStartedMainGame()) return !model.isBonusing() && !model.isAttacking();
        else return field.isHomebaseOfCurrentPlayer() && field.hasMinion();
    }

    public void executeHandle(SpelveldModel model, Field field) {
        Field newField = null;
        Minion newMinion = null;
        if (!field.equals(model.getSelectedField())) {
            newField = field;
            newMinion = field.getMinion();
        }

        model.setChangingSelection();
        model.setSelectedField(newField, newMinion);
        model.setNotChangingSelection();
    }

    public boolean canChangeVisual(SpelveldModel model, Field field) {
        field.getFieldState().setSelected(field.equals(model.getSelectedField()));
        return true;
    }

    public void changeVisual(SpelveldModel model, Field field) {
        field.setStrokeWidth(3);
        field.setStroke(Color.TRANSPARENT);
        if (field.getFieldState().isSelected()) field.setStroke(field.getSelectedColor());
        if (model.isStartedMainGame() && field.getMinion() != null && !model.isCurrentPlayerMinion(field.getMinion())) {
            field.setStroke(field.getEnemyColor());
            if (field.getFieldState().isSelected()) field.setStrokeWidth(5);
        }
    }
}