package be.ugent.objprog.minionwars.spelbord.menu.effects.effectparent;

public abstract class EffectTeam extends Effect {
    public EffectTeam(String confName, String name, Integer duration, Integer value) {
        super(confName, name, duration, value, true);
    }
}