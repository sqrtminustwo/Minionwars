package be.ugent.objprog.minionwars.configloader.powerloader;

import be.ugent.objprog.minionwars.configloader.ConfigLoader;
import be.ugent.objprog.minionwars.spelbord.menu.infopanel.powers.FireballPower;
import be.ugent.objprog.minionwars.spelbord.menu.infopanel.powers.HealPower;
import be.ugent.objprog.minionwars.spelbord.menu.infopanel.powers.LightningPower;
import be.ugent.objprog.minionwars.spelbord.menu.infopanel.powers.powerparent.Power;
import org.jdom2.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PowerLoader extends ConfigLoader<Power> {

    private final Map<String, PowerFactory> powerFactory;

    public PowerLoader(List<Element> configElements) {
        super(configElements);
        powerFactory = Map.ofEntries(
                Map.entry("fireball", FireballPower::new),
                Map.entry("heal", HealPower::new),
                Map.entry("lightning", LightningPower::new)
        );
    }

    public List<Power> getElements() {
        List<Power> powers = new ArrayList<>();
        try {
            for (Element powerC: getConfigElements()) {
                String nameConf = powerC.getName();
                String name = powerC.getAttributeValue("name");
                Integer radius = Integer.parseInt(powerC.getAttributeValue("radius"));
                Integer value = powerC.getAttributeValue("value") == null ? null : Integer.parseInt(powerC.getAttributeValue("value"));
                String effectname = powerC.getAttributeValue("effect");
                Integer effectValue = powerC.getAttributeValue("effect-value") == null ? null : Integer.parseInt(powerC.getAttributeValue("effect-value"));
                Power power = powerFactory.get(nameConf).create(name, radius, value, effectname, effectValue);
                powers.add(power);
            }
        } catch (Exception e) {
            System.out.println("Error loading powers: " + e.getMessage());
        }
        return powers;
    }
}