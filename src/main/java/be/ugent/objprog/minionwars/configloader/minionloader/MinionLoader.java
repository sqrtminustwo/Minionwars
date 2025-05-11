package be.ugent.objprog.minionwars.configloader.minionloader;

import be.ugent.objprog.minionwars.configloader.ConfigLoader;
import be.ugent.objprog.minionwars.spelbord.menu.infopanel.minions.*;
import be.ugent.objprog.minionwars.spelbord.menu.infopanel.minions.minionparent.Minion;
import org.jdom2.Element;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MinionLoader extends ConfigLoader<Minion> {

    private final Map<String, MinionFactory> minionFactory;

    public MinionLoader(List<Element> configElements) {
        super(configElements);
        minionFactory = Map.ofEntries(
                Map.entry("militia", Militia::new),
                Map.entry("spear", Spear::new),
                Map.entry("sword", Sword::new),
                Map.entry("axe", Axe::new),
                Map.entry("archer", Archer::new),
                Map.entry("scout", Scout::new),
                Map.entry("cavalry", Cavalry::new),
                Map.entry("heavy-cavalry", HeavyCavalry::new),
                Map.entry("mounted-archer", MountedArcher::new),
                Map.entry("catapult", Catapult::new),
                Map.entry("trebuchet", Trebuchet::new)
        );
    }

    public List<Minion> getElements() {
        List<Minion> minions = new ArrayList<>();
        for (Element minionC : getConfigElements()) {
            try {
                String type = minionC.getName();
                String name = minionC.getAttributeValue("name");
                int cost = Integer.parseInt(minionC.getAttributeValue("cost"));
                int movement = Integer.parseInt(minionC.getAttributeValue("movement"));
                int[] rangeB = Arrays.stream(minionC.getAttributeValue("range").split(" "))
                        .mapToInt(Integer::parseInt)
                        .toArray();
                Minion.Range range = new Minion.Range(rangeB[0], rangeB[1]);
                int attack = Integer.parseInt(minionC.getAttributeValue("attack"));
                int defence = Integer.parseInt(minionC.getAttributeValue("defence"));

                String effectName = minionC.getAttributeValue("effect");
                Integer effectValue = null;
                if (effectName != null) {
                    String effectValueConf = minionC.getAttributeValue("effect-value");
                    if (effectValueConf != null) {
                        effectValue = Integer.parseInt(effectValueConf);
                    }
                }
                Minion minion = minionFactory.get(type).create(type, name, cost, movement, range, attack, defence, effectName, effectValue);
                minions.add(minion);
            } catch (Exception e) {
                System.out.println("Error loading minions: " + e.getMessage());
            }
        }
        return minions;
    }
    public MinionFactory getMinionFactory(String type) { return minionFactory.get(type); }
}