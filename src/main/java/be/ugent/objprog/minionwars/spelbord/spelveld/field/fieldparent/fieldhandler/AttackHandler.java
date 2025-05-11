package be.ugent.objprog.minionwars.spelbord.spelveld.field.fieldparent.fieldhandler;

import be.ugent.objprog.minionwars.spelbord.spelveld.SpelveldModel;
import be.ugent.objprog.minionwars.spelbord.spelveld.field.fieldparent.Field;

public class AttackHandler implements FieldHandler {
    public boolean canHandle(SpelveldModel model, Field field) {
        return model.isAttacking() && field.getFieldState().hasMinion() && !model.isCurrentPlayerMinion(field.getMinion());
    }
    public void executeHandle(SpelveldModel model, Field field) { model.attackMinion(field.getMinion()); }
    public boolean canChangeVisual(SpelveldModel model, Field field) {
        return !field.getFieldState().isSelected()
                && (model.isAttackSelecting() || model.isAttacking())
                && model.inAttackRange(field);
    }
    public void changeVisual(SpelveldModel model, Field field) {
        field.getBlend().setAttackingColor();
    }
}