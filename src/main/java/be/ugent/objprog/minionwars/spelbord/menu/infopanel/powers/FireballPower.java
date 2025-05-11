package be.ugent.objprog.minionwars.spelbord.menu.infopanel.powers;

import be.ugent.objprog.minionwars.spelbord.menu.infopanel.minions.minionparent.Minion;
import be.ugent.objprog.minionwars.spelbord.menu.infopanel.powers.powertypes.PowerOposition;

public class FireballPower extends PowerOposition {

    public FireballPower(String name, Integer radius, Integer value, String effectName, Integer effectvalue) {
        super("FireballSelected", "FB6700FF", "fireball.png", null, name, radius, value, effectName, effectvalue);
    }

    public void apply(Minion minion) {
        applyEffect(minion);
        applyDefenceChange(minion);
    }
}