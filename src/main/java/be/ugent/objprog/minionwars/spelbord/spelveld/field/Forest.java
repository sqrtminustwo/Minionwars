package be.ugent.objprog.minionwars.spelbord.spelveld.field;

import be.ugent.objprog.minionwars.spelbord.spelveld.field.fieldparent.Field;

public class Forest extends Field {
    public Forest(Double radius, int x, int y, double centerX, double centerY, Integer homebase) {
        super(radius, "Bos", x, y, centerX, centerY, homebase, "forest.png", true, 2, true);
    }
}