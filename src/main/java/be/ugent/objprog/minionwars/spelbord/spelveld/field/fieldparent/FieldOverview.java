package be.ugent.objprog.minionwars.spelbord.spelveld.field.fieldparent;

import be.ugent.objprog.minionwars.spelbord.menu.infopanel.infopanelparent.Infopanel;

public class FieldOverview extends Infopanel {


    public FieldOverview(String imagePath, String name) {
        super(imagePath, name);
        getStyleClass().clear();
        getStyleClass().add("FieldOverviewMain");
    }

    public Integer getSortingValue() { return 0; }
    public void invalidatedSpel() {}
}