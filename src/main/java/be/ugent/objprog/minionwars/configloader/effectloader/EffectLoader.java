package be.ugent.objprog.minionwars.configloader.effectloader;

import be.ugent.objprog.minionwars.configloader.ConfigLoader;
import be.ugent.objprog.minionwars.spelbord.menu.effects.*;
import be.ugent.objprog.minionwars.spelbord.menu.effects.effectparent.Effect;
import org.jdom2.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EffectLoader extends ConfigLoader<Effect> {

    private final Map<String, EffectFactory> effectFactory;

    public EffectLoader(List<Element> elements) {
        super(elements);
        effectFactory = Map.ofEntries(
                Map.entry("burn", BurnEffect::new),
                Map.entry("paralysis", ParalysisEffect::new),
                Map.entry("heal", HealEffect::new),
                Map.entry("poison", PoisonEffect::new),
                Map.entry("slow", SlowEffect::new),
                Map.entry("blindness", BlindnessEffect::new),
                Map.entry("rage", RageEffect::new)
        );
    }

    public List<Effect> getElements() {
        List<Effect> effects = new ArrayList<>();
        try {
            for (Element effectC : getConfigElements()) {
                String nameConf = effectC.getName();
                String name = effectC.getAttributeValue("name");
                Integer duration = Integer.parseInt(effectC.getAttributeValue("duration"));
                Integer value = effectC.getAttributeValue("value") == null ? null : Integer.parseInt(effectC.getAttributeValue("value"));
                Effect effect = effectFactory.get(nameConf).create(nameConf, name, duration, value);
                effects.add(effect);
            }
        } catch (Exception e) {
            System.out.println("Error loading effects: " + e.getMessage());
        }
        return effects;
    }
    public EffectFactory getEffectFactory(String name) { return effectFactory.get(name); }
}