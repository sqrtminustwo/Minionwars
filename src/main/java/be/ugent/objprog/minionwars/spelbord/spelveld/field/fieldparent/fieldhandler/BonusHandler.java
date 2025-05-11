package be.ugent.objprog.minionwars.spelbord.spelveld.field.fieldparent.fieldhandler;

import be.ugent.objprog.minionwars.spelbord.spelveld.SpelveldModel;
import be.ugent.objprog.minionwars.spelbord.spelveld.field.fieldparent.Field;

public class BonusHandler implements FieldHandler {
    public boolean canHandle(SpelveldModel model, Field field) { return model.isBonusing() && model.canBonus(); }
    public void executeHandle(SpelveldModel model, Field field) { model.utilizeBonus(); }
    public boolean canChangeVisual(SpelveldModel model, Field field) {
        boolean canChange = model.isBonusing() && model.inBonusRange(field);
        field.getFieldState().setInBonusingRange(canChange);
        return canChange;
    }
    public void changeVisual(SpelveldModel model, Field field) {
        if (field.getFieldState().isInBonusingRange() && model.isBonusing()) {
            field.getBlend().setCustomColor(model.canBonus() ? model.getSelectedPower().getBlendColor() : field.getEnemyColor());
        }
    }
}