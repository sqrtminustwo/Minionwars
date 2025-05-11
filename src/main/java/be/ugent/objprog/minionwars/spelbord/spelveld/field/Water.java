package be.ugent.objprog.minionwars.spelbord.spelveld.field;

import be.ugent.objprog.minionwars.spelbord.spelveld.field.fieldparent.Field;

public class Water extends Field {
    public Water(Double radius, int x, int y, double centerX, double centerY, Integer homebase) {
        super(radius, "Water", x, y, centerX, centerY, homebase, "water.png", false, null, true);
    }
}