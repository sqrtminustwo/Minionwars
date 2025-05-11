package be.ugent.objprog.minionwars.spelbord.menu.infopanel.infopanelparent;

import be.ugent.objprog.minionwars.MinionWars;
import be.ugent.objprog.minionwars.spelbord.SpelbordSubModel;
import javafx.beans.InvalidationListener;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Objects;

public abstract class StatsOverview extends VBox implements InvalidationListener {

    protected SpelbordSubModel model;
    public record StatsRow(String imageName, Label value) {}

    public void createStats(List<StatsRow> iconValue) {
        try {
            getStyleClass().add("InfoPanelStatsOverview");
            setSpacing(1);
            for (StatsRow statsRow: iconValue) {
                ImageView icon = new ImageView(new Image(
                        Objects.requireNonNull(MinionWars.class.getResource("images/icons/" + statsRow.imageName
                        )).toExternalForm()));
                icon.setFitHeight(20);
                icon.setFitWidth(20);
                Label value = statsRow.value;
                HBox hbox = new HBox();
                hbox.getChildren().addAll(icon, value);
                getChildren().add(hbox);
            }
        } catch (Exception e) {
            System.out.println("Error loading info panel image: " + e.getMessage());
        }
    }

    public void setModel(SpelbordSubModel model) {
        this.model = model;
        model.addListener(this);
    }

    public abstract void startedMainGame();

}