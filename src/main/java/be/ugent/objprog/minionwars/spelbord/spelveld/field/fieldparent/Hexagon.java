package be.ugent.objprog.minionwars.spelbord.spelveld.field.fieldparent;

import javafx.scene.shape.Polygon;

public abstract class Hexagon extends Polygon {

    protected final Integer homebase;
    public record Coords(int x, int y) {}
    private final Coords coords;


    public Hexagon(int x, int y, double centerX, double centerY, double size, Integer homebase) {
        this.homebase = homebase;
        coords = new Coords(x, y);
        getStyleClass().add("Field");
        initialize(centerX, centerY, size);
    }

    //bron: https://www.redblobgames.com/grids/hexagons/#basics
    private void initialize(double centerX, double centerY, double size) {
        for (int i = 0; i < 6; i++) {
            double angle_deg = 60 * i - 30;
            double angle_rad = Math.toRadians(angle_deg);
            getPoints().addAll(
                    (centerX + size * Math.cos(angle_rad)),
                    (centerY + size * Math.sin(angle_rad)));
        }
    }

    public Coords getCoords() { return coords; }
}