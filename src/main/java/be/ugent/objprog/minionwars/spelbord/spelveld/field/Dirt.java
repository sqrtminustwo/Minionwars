package be.ugent.objprog.minionwars.spelbord.spelveld.field;

import be.ugent.objprog.minionwars.spelbord.spelveld.field.fieldparent.Field;

public class Dirt extends Field {
    public Dirt(Double radius, int x, int y, double centerX, double centerY, Integer homebase) {
        super(radius,"Aarde", x, y, centerX, centerY, homebase, "dirt.png", true, 1, true);
    }
}