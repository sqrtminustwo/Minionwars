package be.ugent.objprog.minionwars.spelbord.menu.infopanel.minions;

import be.ugent.objprog.minionwars.spelbord.menu.infopanel.minions.minionparent.Minion;

public class Catapult extends Minion {
    public Catapult(String type, String name, int cost, int movement, Range range, int attack, int defence, String effectName, Integer effectValue) {
        super(type, name, cost, movement, range, attack, defence, "catapult.png", effectName, effectValue);
    }
}