package be.ugent.objprog.minionwars.spelbord.spelveld.field;

import be.ugent.objprog.minionwars.spelbord.spelveld.field.fieldparent.Field;

public class Mountains extends Field {
    public Mountains(Double radius, int x, int y, double centerX, double centerY, Integer homebase) {
        super(radius, "Bergen", x, y, centerX, centerY, homebase, "mountains.png", true, 1, false);
    }
}