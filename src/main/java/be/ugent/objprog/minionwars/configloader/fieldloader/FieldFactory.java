package be.ugent.objprog.minionwars.configloader.fieldloader;

import be.ugent.objprog.minionwars.spelbord.spelveld.field.fieldparent.Field;

public interface FieldFactory {
    Field create(Double radius, int x, int y, double centerX, double centerY, Integer homebase);
}