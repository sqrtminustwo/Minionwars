package be.ugent.objprog.minionwars.configloader.fieldloader;

import be.ugent.objprog.minionwars.configloader.ConfigLoader;
import be.ugent.objprog.minionwars.spelbord.spelveld.field.Dirt;
import be.ugent.objprog.minionwars.spelbord.spelveld.field.Forest;
import be.ugent.objprog.minionwars.spelbord.spelveld.field.Mountains;
import be.ugent.objprog.minionwars.spelbord.spelveld.field.Water;
import be.ugent.objprog.minionwars.spelbord.spelveld.field.fieldparent.Field;
import org.jdom2.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FieldLoader extends ConfigLoader<Field> {

    private final FieldDim fielddim;
    public record FieldDim(double radius) {
        public double width() { return Math.sqrt(3)*radius; }
        public double height() { return 2*radius; }
        public double spacing() { return 3.5; }
    }
    private final Map<String, FieldFactory> fieldFactory;
    private final double padding;

    public FieldLoader(List<Element> configElements) {
        super(configElements);
        fieldFactory = Map.of(
                "dirt", Dirt::new,
                "forest", Forest::new,
                "mountains", Mountains::new,
                "water", Water::new
        );
        fielddim = new FieldDim(50);
        padding = 20;
    }

    public Double getPadding() { return padding; }
    public List<Field> getElements() {
        List<Field> fields = new ArrayList<>();
        try {
            double paddingX;
            double paddingY = padding + fielddim.width() / 2;

            for (Element fieldC : getConfigElements()) {
                int y = Integer.parseInt(fieldC.getAttributeValue("y").trim());
                int x = Integer.parseInt(fieldC.getAttributeValue("x").trim());
                String type = fieldC.getName();

                paddingX = y % 2 != 0 ? padding + fielddim.width() + 2 : padding + fielddim.width() / 2;
                double posX = paddingX + x * fielddim.width() + x * fielddim.spacing();
                double posY = paddingY + 0.75 * y * fielddim.height() + y * fielddim.spacing();

                Integer homebase = null;
                if (fieldC.getAttributeValue("homebase") != null) {
                    homebase = Integer.parseInt(fieldC.getAttributeValue("homebase"));
                    homebase--;
                }

                Field field = fieldFactory.get(type).create(fielddim.radius(), x, y, posX, posY, homebase);
                fields.add(field);
            }
        } catch (Exception e) {
            System.out.println("Error loading field: " + e.getMessage());
        }
        return fields;
    }

}