package be.ugent.objprog.minionwars.spelbord.menu.menuparts.commontabs;

import be.ugent.objprog.minionwars.MinionWars;
import be.ugent.objprog.minionwars.spelbord.menu.MenuModel;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.Objects;

public class PlayerInfo extends VBox implements InvalidationListener {

    private final MenuModel model;
    private Label playerName;
    private Label aantalMunten;
    private ImageView coinImage;
    private Label minionInfo;
    private final HBox data;
    private final int width;

    public PlayerInfo(MenuModel model, int margin, int width) {
        this.model = model;
        this.width = width;
        model.addListener(this);

        data = new HBox();
        data.setPrefHeight(40);
        data.setPadding(new Insets(10, 5*margin, 10, 0));
        data.getStyleClass().add("Playerinfo");
        startedSelection();
    }

    public void startedSelection() {
        playerName = new Label();
        coinImage = new ImageView(new Image(Objects.requireNonNull(MinionWars.class.getResource("images/icons/coin-FFB900.png")).toExternalForm()));
        coinImage.setFitHeight(20);
        coinImage.setFitWidth(20);
        aantalMunten = new Label();
        aantalMunten.getStyleClass().add("CostLabel");

        Region leftSpacer = new Region();
        Region rightSpacer = new Region();
        HBox.setHgrow(leftSpacer, Priority.ALWAYS);
        HBox.setHgrow(rightSpacer, Priority.ALWAYS);
        playerName.setMaxWidth(width*0.5);
        data.getChildren().addAll(leftSpacer, playerName, rightSpacer, coinImage, aantalMunten);

        Separator separatorLine = new Separator();
        getChildren().addAll(data, separatorLine);
    }

    public void startedMainGame() {
        data.getChildren().remove(aantalMunten);
        data.getChildren().remove(coinImage);
        ImageView minionImage = new ImageView(new Image(Objects.requireNonNull(MinionWars.class.getResource("images/icons/minions-0073FF.png")).toExternalForm()));
        minionImage.setFitHeight(20);
        minionImage.setFitWidth(20);
        minionInfo = new Label();
        minionInfo.getStyleClass().add("MinionInfoLabel");
        data.getChildren().addAll(minionImage, minionInfo);
        data.setSpacing(5);
    }

    @Override
    public void invalidated(Observable ignore) {
        if (model.isStartingMainGame()) startedMainGame();
        if (model.isStartedMainGame()) {
            invalidatedSpel();
        } else {
            invalidatedStartSpel();
        }
    }

    public void invalidatedStartSpel() {
        try {
            setName();
            aantalMunten.setText(model.getCurrentSpeler().getMunten() + "");
        } catch (Exception e) {
            System.err.println("Konde speler gegeven niet inladen: " + e.getMessage());
        }
    }

    public void invalidatedSpel() {
        try {
            setName();
            minionInfo.setText(model.getCurrentSpeler().getAantalAfgehandeld() + "/" + model.getCurrentSpeler().getAantalMinions());
        } catch (Exception e) {
            System.err.println("Playerinfo: " + e.getMessage());
        }
    }

    public void setName() {
        try {
            playerName.setText(model.getCurrentSpeler().getNaam());
        } catch (Exception e) {
            System.err.println("Konde speler gegeven niet inladen: " + e.getMessage());
        }
    }
}