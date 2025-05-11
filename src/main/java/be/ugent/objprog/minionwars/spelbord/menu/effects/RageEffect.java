package be.ugent.objprog.minionwars.spelbord.menu.effects;

import be.ugent.objprog.minionwars.spelbord.menu.effects.effectparent.EffectTeam;
import be.ugent.objprog.minionwars.spelbord.menu.infopanel.minions.minionparent.Minion;

public class RageEffect extends EffectTeam {
    public RageEffect(String confName, String name, Integer duration, Integer value) {
        super(confName, name, duration, value);
    }

    public void applyValue(Minion minion) { minion.addExtraAttack(getValue()); }
    public void applyNonValue(Minion minion) {}
}