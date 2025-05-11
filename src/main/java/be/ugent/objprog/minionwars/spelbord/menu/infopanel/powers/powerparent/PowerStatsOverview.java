package be.ugent.objprog.minionwars.spelbord.menu.infopanel.powers.powerparent;

import be.ugent.objprog.minionwars.spelbord.menu.infopanel.infopanelparent.StatsOverview;
import javafx.beans.Observable;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

public class PowerStatsOverview extends StatsOverview {

    public PowerStatsOverview(Power maker) {

        Label primaryLabel = null; //optioneel
        if (maker.getValue() != null && maker.getValue() > 0) {
            primaryLabel = new Label(maker.getValue() + "");
            primaryLabel.getStyleClass().add("PrimaryLabel");
        }
        Label radiusLabel = null;
        if (maker.getRadius() != null) {
            radiusLabel = new Label(maker.getRadius() + "");
            radiusLabel.getStyleClass().add("RadiusLabel");
        }
        Label durationLabel = null; //optioneel
        if (maker.getDuration() != null && maker.getDuration() > 0) {
            durationLabel = new Label(maker.getDuration() + "");
            durationLabel.getStyleClass().add("DurationLabel");
        }
        List<StatsRow> iconValue = new ArrayList<>();
        addIfNotNull(iconValue, maker.getPrimaryIcon(), primaryLabel);
        addIfNotNull(iconValue, "range-119533.png", radiusLabel);
        addIfNotNull(iconValue, "duration-0073FF.png", durationLabel);

        createStats(iconValue);
    }

    private void addIfNotNull(List<StatsRow> list, String iconName, Label label) { if (label != null) list.add(new StatsRow(iconName, label)); }

    public void startedMainGame() {}

    public void invalidated(Observable ignore) {}
}