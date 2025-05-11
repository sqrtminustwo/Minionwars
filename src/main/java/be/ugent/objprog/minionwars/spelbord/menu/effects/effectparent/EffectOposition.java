package be.ugent.objprog.minionwars.spelbord.menu.effects.effectparent;

public abstract class EffectOposition extends Effect {
    public EffectOposition(String confName, String name, Integer duration, Integer value) {
        super(confName, name, duration, value, false);
    }
}