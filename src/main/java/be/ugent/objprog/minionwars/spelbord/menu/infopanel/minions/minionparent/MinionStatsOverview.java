package be.ugent.objprog.minionwars.spelbord.menu.infopanel.minions.minionparent;

import be.ugent.objprog.minionwars.spelbord.menu.infopanel.infopanelparent.StatsOverview;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.control.Label;

import java.util.List;

public class MinionStatsOverview extends StatsOverview implements InvalidationListener {

    private final Minion maker;
    private final Label attackLabel;
    private final Label defenseLabel;

    public MinionStatsOverview(Minion maker) {
        this.maker = maker;
        Label costLabel = new Label(maker.getCost() + "");
        costLabel.getStyleClass().add("CostLabel");
        attackLabel = new Label(maker.getAttack() + "");
        attackLabel.getStyleClass().add("PrimaryLabel");
        defenseLabel = new Label(maker.getDefence() + "");
        defenseLabel.getStyleClass().add("PrimaryLabel");
        List<StatsRow> iconValue = List.of(
                new StatsRow("coin-FFB900.png", costLabel),
                new StatsRow("attack-D60000.png", attackLabel),
                new StatsRow("health-D60000.png", defenseLabel)
        );
        createStats(iconValue);
    }

    public void startedMainGame() {
        getChildren().removeFirst();
    }

    @Override
    public void invalidated(Observable ignore) {
        if (model.isStartedMainGame()) {
            attackLabel.setText(maker.getAttack() + "");
            defenseLabel.setText(maker.getCurDefence() + "/" + maker.getDefence());
        }
    }
}