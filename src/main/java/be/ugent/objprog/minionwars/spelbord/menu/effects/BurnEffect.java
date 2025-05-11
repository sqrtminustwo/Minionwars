package be.ugent.objprog.minionwars.spelbord.menu.effects;

import be.ugent.objprog.minionwars.spelbord.menu.effects.effectparent.EffectOposition;
import be.ugent.objprog.minionwars.spelbord.menu.infopanel.minions.minionparent.Minion;

public class BurnEffect extends EffectOposition {
    public BurnEffect(String confName, String name, Integer duration, Integer value) {
        super(confName, name, duration, value);
    }

    public void applyValue(Minion minion) { model.damageMinion(minion, getValue()); }
    public void applyNonValue(Minion minion) {}
}