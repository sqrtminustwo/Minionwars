package be.ugent.objprog.minionwars.spelbord.menu.infopanel.powers;

import be.ugent.objprog.minionwars.spelbord.menu.infopanel.minions.minionparent.Minion;
import be.ugent.objprog.minionwars.spelbord.menu.infopanel.powers.powertypes.PowerTeam;

public class HealPower extends PowerTeam {

    public HealPower(String name, Integer radius, Integer value, String effectName, Integer effectvalue) {
        super("HealSelected", "e3b835", "healing.png", "heal-D60000.png", name, radius, value, effectName, effectvalue);
    }

    public void apply(Minion minion) {
        applyEffect(minion);
        applyDefenceChange(minion);
    }
}