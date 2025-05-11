package be.ugent.objprog.minionwars.spelbord.menu.infopanel.minions;

import be.ugent.objprog.minionwars.spelbord.menu.infopanel.minions.minionparent.Minion;

public class Militia extends Minion {
    public Militia(String type, String name, int cost, int movement, Range range, int attack, int defence, String effectName, Integer effectValue) {
        super(type, name, cost, movement, range, attack, defence, "militia.png", effectName, effectValue);
    }
}