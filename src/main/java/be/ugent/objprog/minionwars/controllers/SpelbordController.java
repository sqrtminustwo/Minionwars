package be.ugent.objprog.minionwars.controllers;

import be.ugent.objprog.minionwars.spelbord.Spelbord;
import be.ugent.objprog.minionwars.startscherm.input.Speler;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import org.jdom2.Element;

import java.util.List;

public class SpelbordController extends ControllerHead {

    private Element config;
    private final List<Speler> spelers;

    public SpelbordController(List<Speler> spelers) { this.spelers = spelers; }

    public Parent start() {
        SplitPane rootPane = new SplitPane();
        BorderPane menuPane = new BorderPane();
        ScrollPane spelVeld = new ScrollPane();
        spelVeld.getStyleClass().add("SpelveldModel");
        menuPane.getStyleClass().add("MenuModel");
        new Spelbord(this, config, menuPane, spelVeld);
        rootPane.setDividerPositions(0.2f);
        rootPane.getItems().addAll(menuPane, spelVeld);
        return rootPane;
    }

    public void setConfig(Element config) {
        this.config = config;
    }
    public List<Speler> getSpelers() { return spelers; }
    public void nextFase(Speler winner) { application.eindScherm(winner); }
}