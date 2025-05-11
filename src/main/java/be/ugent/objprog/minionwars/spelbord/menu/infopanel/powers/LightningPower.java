package be.ugent.objprog.minionwars.spelbord.menu.infopanel.powers;

import be.ugent.objprog.minionwars.spelbord.menu.infopanel.minions.minionparent.Minion;
import be.ugent.objprog.minionwars.spelbord.menu.infopanel.powers.powertypes.PowerOposition;

public class LightningPower extends PowerOposition {
    public LightningPower(String name, Integer radius, Integer value, String effectName, Integer effectvalue) {
        super("LightingSelected", "1769B6FF", "lightning.png", null, name, radius, value, effectName, effectvalue);
    }

    public void apply(Minion minion) {
        applyEffect(minion);
        applyDefenceChange(minion);
    }
}