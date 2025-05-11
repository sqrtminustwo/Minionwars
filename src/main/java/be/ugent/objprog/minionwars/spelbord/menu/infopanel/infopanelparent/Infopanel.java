package be.ugent.objprog.minionwars.spelbord.menu.infopanel.infopanelparent;

import be.ugent.objprog.minionwars.MinionWars;
import be.ugent.objprog.minionwars.spelbord.menu.MenuModel;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import java.util.Objects;

public abstract class Infopanel extends HBox implements InvalidationListener {

    protected MenuModel model;
    protected final ImagePattern image;
    protected final Label showname;
    private final VBox infoBox;
    protected StatsOverview statsOverview;
    private final Region leftSpacer;

    public Infopanel(String imagePath, String name) {
        setOnMouseClicked(this::handleSelected);

        Circle avatar = new Circle(30, 30, 30);
        image = new ImagePattern(new Image(Objects.requireNonNull(MinionWars.class.getResource("images/" + imagePath)).toExternalForm()));
        avatar.setFill(image);
        getStyleClass().add("MinionPanel");

        showname = new Label(name);
        showname.setWrapText(true);
        showname.getStyleClass().add("InfoPanelLabel");

        infoBox = new VBox(showname);
        infoBox.setAlignment(Pos.CENTER);

        Region statsPlaceholder = new Region();
        leftSpacer = createSpacer();
        Region rightSpacer = createSpacer();

        getChildren().addAll(avatar, leftSpacer, infoBox, rightSpacer, statsPlaceholder);
        HBox.setHgrow(leftSpacer, Priority.ALWAYS);
        HBox.setHgrow(infoBox, Priority.NEVER);
        HBox.setHgrow(rightSpacer, Priority.ALWAYS);
        HBox.setHgrow(statsPlaceholder, Priority.NEVER);
    }

    private Region createSpacer() {
        Region spacer = new Region();
        spacer.setMinWidth(1);
        return spacer;
    }

    public void setModel(MenuModel model) {
        this.model = model;
        model.addListener(this);
    }
    public void setStatsOverview(StatsOverview stats) {
        if (statsOverview != null) getChildren().remove(statsOverview);

        statsOverview = stats;
        statsOverview.setMaxWidth(Double.MAX_VALUE);
        getChildren().add(statsOverview);

        HBox.setHgrow(statsOverview, Priority.NEVER);
    }

    public void removeAsListener() {
        model.removeListener(this);
        model.removeListener(statsOverview);
    }

    public void addNewElement(Label newElement) { infoBox.getChildren().add(newElement); }
    public void removeLeftSpacer() {
        getChildren().remove(leftSpacer);
        HBox.setHgrow(infoBox, Priority.ALWAYS);
        infoBox.setAlignment(Pos.CENTER_LEFT);
    }
    public MenuModel getModel() { return model; }
    public abstract Integer getSortingValue();

    @Override
    public void invalidated(Observable ignore) {
        if (model.isStartingMainGame()) startedMainGame();
        else if (model.isStartedMainGame()) invalidatedSpel();
        else invalidatedStartSpel();
    }
    public void invalidatedStartSpel() {}
    public abstract void invalidatedSpel();

    public void handleSelected(MouseEvent mouseEvent) {
        if (!model.isStartedMainGame()) handleStartSpel();
        else handleSpel();
    }
    public void handleStartSpel() {}
    public void handleSpel() {}
    public void updateStyleClass(String styleClass) { getStyleClass().clear(); getStyleClass().add(styleClass); }
    public void startedMainGame() {}
}