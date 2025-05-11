package be.ugent.objprog.minionwars.spelbord.menu.infopanel.minions;

import be.ugent.objprog.minionwars.spelbord.menu.infopanel.minions.minionparent.Minion;

public class Archer extends Minion {
    public Archer(String type, String name, int cost, int movement, Range range, int attack, int defence, String effectName, Integer effectValue) {
        super(type, name, cost, movement, range, attack, defence, "archer.png", effectName, effectValue);
    }
}