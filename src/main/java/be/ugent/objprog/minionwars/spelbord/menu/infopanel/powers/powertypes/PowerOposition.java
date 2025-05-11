package be.ugent.objprog.minionwars.spelbord.menu.infopanel.powers.powertypes;

import be.ugent.objprog.minionwars.spelbord.menu.infopanel.minions.minionparent.Minion;
import be.ugent.objprog.minionwars.spelbord.menu.infopanel.powers.powerparent.Power;

public abstract class PowerOposition extends Power {
    public PowerOposition(String selectedStyle, String blendColor, String imageName, String primaryIcon, String name, Integer radius, Integer value, String effectName, Integer effectvalue) {
        super(selectedStyle, blendColor, imageName, primaryIcon, name, radius, value, effectName, effectvalue);
    }
    public boolean canApplyOn(Minion minion) {
        return !model.isCurrentPlayerMinion(minion);
    }
    public void applyDefenceChange(Minion minion) {
        if (getValue() != null && !model.isCurrentPlayerMinion(minion)) model.damageMinion(minion, getValue());
    }
    public void applyEffect(Minion minion) {
        if (getGameEffect() != null && !model.isCurrentPlayerMinion(minion)) minion.addGameEffect(getGameEffect());
    }
}