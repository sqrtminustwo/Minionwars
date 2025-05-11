package be.ugent.objprog.minionwars.spelbord.menu.effects;

import be.ugent.objprog.minionwars.spelbord.menu.effects.effectparent.EffectOposition;
import be.ugent.objprog.minionwars.spelbord.menu.infopanel.minions.minionparent.Minion;

public class SlowEffect extends EffectOposition {
    public SlowEffect(String confName, String name, Integer duration, Integer value) {
        super(confName, name, duration, value);
    }

    public void applyValue(Minion minion) { minion.addMoveRangeLimit(getValue()); }
    public void applyNonValue(Minion minion) {}
}