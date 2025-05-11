package be.ugent.objprog.minionwars.spelbord.spelveld.field.fieldparent.fieldstate;

public class FieldState {

    private final boolean walkable;
    private final boolean canAttackFrom;
    private boolean isHomeBase;
    private boolean inBonusingRange;
    private boolean hasMinion;
    private boolean selected;

    public FieldState(boolean walkable, boolean canAttackFrom) {
        this.walkable = walkable;
        this.canAttackFrom = canAttackFrom;
        inBonusingRange = false;
    }

    public boolean isWalkable() { return walkable; }
    public boolean isCanAttackFrom() { return canAttackFrom; }
    public boolean isIsHomeBase() { return isHomeBase; }
    public boolean isInBonusingRange() { return inBonusingRange; }
    public boolean hasMinion() { return hasMinion; }
    public boolean isSelected() { return selected; }

    public void setInBonusingRange(boolean inBonusingRange) { this.inBonusingRange = inBonusingRange; }
    public void setHasMinion(boolean hasMinion) { this.hasMinion = hasMinion; }
    public void setSelected(boolean selected) { this.selected = selected; }
    public void setHomeBase(boolean homeBase) { this.isHomeBase = homeBase; }
}