package be.ugent.objprog.minionwars.spelbord.menu.effects;

import be.ugent.objprog.minionwars.spelbord.menu.effects.effectparent.EffectOposition;
import be.ugent.objprog.minionwars.spelbord.menu.infopanel.minions.minionparent.Minion;

public class BlindnessEffect extends EffectOposition {
    public BlindnessEffect(String confName, String name, Integer duration, Integer value) {
        super(confName, name, duration, value);
    }

    public void applyValue(Minion minion) { minion.addAttackRangeLimit(getValue()); }
    public void applyNonValue(Minion minion) {}
}