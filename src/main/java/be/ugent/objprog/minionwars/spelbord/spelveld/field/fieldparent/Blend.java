package be.ugent.objprog.minionwars.spelbord.spelveld.field.fieldparent;

import javafx.scene.paint.Color;

public class Blend extends Hexagon {

    private final Color homebaseColor;
    private final Color nothomebaseColor;
    private final Color movingColor;
    private final Color attackingColor;

    public Blend(int x, int y, double centerX, double centerY, double size, Integer homebase) {
        super(x, y, centerX, centerY, size, homebase);
        homebaseColor = Color.rgb(255, 255, 0);
        nothomebaseColor = Color.rgb(200, 20, 20);
        movingColor = Color.rgb(100, 200, 40);
        attackingColor = Color.web("bd0505");
        setMouseTransparent(true);
        setFill(Color.TRANSPARENT);
        setOpacity(0.5);
    }

    public void setTransparent() { setFill(Color.TRANSPARENT); }
    public void setHomebaseColor() { setFill(homebaseColor); }
    public void setNothomebaseColor() { setFill(nothomebaseColor); }
    public void setMovingColor() { setFill(movingColor); }
    public void setAttackingColor() { setFill(attackingColor); }
    public void setCustomColor(Color color) { setFill(color); }
}